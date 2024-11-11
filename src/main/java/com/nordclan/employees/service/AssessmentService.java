package com.nordclan.employees.service;

import com.nordclan.employees.dto.entity.QuestionResultDto;
import com.nordclan.employees.dto.request.AssessTrainingRequestDto;
import com.nordclan.employees.dto.response.QuestionResultDtoFull;
import com.nordclan.employees.entity.Question;
import com.nordclan.employees.entity.QuestionResult;
import com.nordclan.employees.entity.QuestionTrainingId;
import com.nordclan.employees.entity.Training;
import com.nordclan.employees.entity.TrainingStatus;
import com.nordclan.employees.exception.QuestionNotFoundException;
import com.nordclan.employees.exception.QuestionNotLinkedWithTemplateException;
import com.nordclan.employees.exception.TrainingIncorrectStatusException;
import com.nordclan.employees.exception.TrainingNotFoundException;
import com.nordclan.employees.mapper.QuestionResultMapper;
import com.nordclan.employees.repository.AssessmentRepository;
import com.nordclan.employees.repository.QuestionTemplateRepository;
import com.nordclan.employees.repository.TrainingRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@Transactional
public class AssessmentService {
    QuestionService questionService;
    AssessmentRepository assessmentRepository;
    QuestionTemplateRepository questionTemplateRepository;
    QuestionResultMapper questionResultMapper;
    TrainingService trainingService;
    TemplateService templateService;
    TrainingRepository trainingRepository;

    private int getTotalQuestionCount(Long templateId) {
        return templateService.findById(templateId).getQuestions().size();
    }

    private int calculateExamResult(Long trainingId) {
        Training training = trainingService.findByIdInternal(trainingId);
        int totalQuestionCount = getTotalQuestionCount(training.getTemplateId());

        List<QuestionResult> assessments = assessmentRepository.findAllByTraining(trainingId);
        int totalAnsweredPoints = assessments.stream().mapToInt(QuestionResult::getPoint).sum();

        int maxPoints = totalQuestionCount * 3;
        return (int) Math.round((totalAnsweredPoints / (double) maxPoints) * 100);
    }

    public QuestionResultDto assessTraining(Long trainingId, Long questionId, QuestionResultDto questionResultDto) throws QuestionNotFoundException, TrainingNotFoundException, QuestionNotLinkedWithTemplateException {
        final Training training = trainingService.findByIdInternal(trainingId);
        final Question question = questionService.findByIdInternal(questionId);

        if (!training.getTrainingStatus().equals(TrainingStatus.IN_PROGRESS)) {
            training.setTrainingStatus(TrainingStatus.IN_PROGRESS); //ToDo Не нравиться логика
        }

        if (questionTemplateRepository.findQuestionTemplateRelation(question.getId(), training.getTemplateId())
            .isEmpty()) {
            throw new QuestionNotLinkedWithTemplateException(question.getId(), training.getTemplateId());
        }

        final QuestionResult questionResult = questionResultMapper.toEntity(questionResultDto);
        final QuestionTrainingId questionTrainingIdyId = new QuestionTrainingId(trainingId, question.getId());
        questionResult.setId(questionTrainingIdyId);

        QuestionResult savedResult = assessmentRepository.saveAndFlush(questionResult);
        training.setExamResult(calculateExamResult(trainingId));
        trainingRepository.save(training);
        return questionResultMapper.toDto(savedResult);
    }

    public List<QuestionResultDtoFull> getAllResultByTraining(Long trainingId) throws TrainingNotFoundException {
        return assessmentRepository.findAllByTraining(trainingService.findById(trainingId).getId())
            .stream()
            .map(questionResultMapper::toDtoFull)
            .toList();
    }

    public void assessQuestions(AssessTrainingRequestDto request) {
        final Long trainingId = request.getTrainingId();

        final Training training = trainingService.findByIdInternal(trainingId);

        if (!TrainingStatus.IN_PROGRESS.equals(training.getTrainingStatus())) {
            throw new TrainingIncorrectStatusException(String.format(
                TrainingIncorrectStatusException.message,
                trainingId,
                training.getTrainingStatus()));
        }

        final Long templateId = training.getTemplateId();
        assessmentRepository.saveAll(
            request.getAssessmentList().stream()
                .map(assessment -> {
                    final Long questionId = assessment.getQuestionId();

                    questionTemplateRepository.findQuestionTemplateRelation(
                        questionId,
                        templateId
                    ).orElseThrow(() ->
                        new QuestionNotLinkedWithTemplateException(questionId, templateId));

                    return new QuestionResult()
                        .setId(new QuestionTrainingId(trainingId, questionId))
                        .setPoint(assessment.getPoints());
                }).toList());
        int examResult = calculateExamResult(trainingId);
        training.setExamResult(examResult);
        trainingRepository.save(training);
    }
}