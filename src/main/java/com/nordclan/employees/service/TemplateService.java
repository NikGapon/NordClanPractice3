package com.nordclan.employees.service;

import com.nordclan.employees.api.DefaultService;
import com.nordclan.employees.dto.create.CreateTemplateDto;
import com.nordclan.employees.dto.entity.QuestionDto;
import com.nordclan.employees.dto.entity.TemplateDto;
import com.nordclan.employees.dto.response.QuestionForLinkDto;
import com.nordclan.employees.dto.response.TemplateJointDto;
import com.nordclan.employees.entity.Question;
import com.nordclan.employees.entity.QuestionTemplate;
import com.nordclan.employees.entity.QuestionTemplateId;
import com.nordclan.employees.entity.Template;
import com.nordclan.employees.exception.QuestionNotFoundException;
import com.nordclan.employees.exception.TemplateNotFoundException;
import com.nordclan.employees.mapper.JointMapper;
import com.nordclan.employees.mapper.TemplateMapper;
import com.nordclan.employees.repository.QuestionRepository;
import com.nordclan.employees.repository.QuestionTemplateRepository;
import com.nordclan.employees.repository.TemplateRepository;
import jakarta.persistence.EntityManager;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.nordclan.employees.utils.CommonUtils.setIfNotBlank;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
@Transactional
public class TemplateService extends DefaultService<Long, Template, TemplateDto> {
    TemplateRepository templateRepository;
    TemplateMapper templateMapper;
    JointMapper jointMapper;
    QuestionTemplateRepository questionTemplateRepository;
    EntityManager entityManager;
    QuestionService questionService;
    QuestionRepository questionRepository;

    public TemplateService(
        TemplateRepository templateRepository,
        JointMapper jointMapper,
        QuestionTemplateRepository questionTemplateRepository,
        TemplateMapper templateMapper,
        EntityManager entityManager,
        QuestionService questionService,
        QuestionRepository questionRepository
    ) {
        super(templateRepository, templateMapper, TemplateNotFoundException::new);
        this.templateRepository = templateRepository;
        this.jointMapper = jointMapper;
        this.questionTemplateRepository = questionTemplateRepository;
        this.templateMapper = templateMapper;
        this.entityManager = entityManager;
        this.questionService = questionService;
        this.questionRepository = questionRepository;
    }

    @Transactional(readOnly = true)
    public List<TemplateDto> findByName(String templateName) {
        return this.findAllByExample(TemplateDto.builder().name(templateName).build());
    }

    public TemplateDto createTemplate(CreateTemplateDto templateDto) {
        final Template template = templateRepository.saveAndFlush(templateMapper.toNewEntity(templateDto));

        return templateMapper.toDto(template);
    }

    public TemplateDto linkQuestions(Long templateId, List<QuestionForLinkDto> linkedQuestionDto) {
        final Template template = this.findByIdInternal(templateId);

        final List<QuestionTemplate> questionTemplates =
            questionRepository.findAllById(
                    linkedQuestionDto
                        .stream()
                        .map(QuestionForLinkDto::getId)
                        .toList())
                .stream()
                .map(question -> new QuestionTemplate(
                    question,
                    new QuestionTemplateId(
                        question.getId(),
                        template.getId()))
                ).toList();

        questionTemplateRepository.saveAllAndFlush(questionTemplates);
        entityManager.refresh(template);

        return this.findById(templateId);
    }

    @Transactional(readOnly = true)
    public TemplateJointDto findJointTemplateById(Long templateId) throws TemplateNotFoundException {
        return jointMapper.toDto(this.findByIdInternal(templateId))
            .setGroups(questionTemplateRepository.findQuestionIdsByTemplateId(templateId)
                .stream()
                .map(id -> questionRepository.findById(id)
                    .orElseThrow(() -> new QuestionNotFoundException(id)))
                .collect(Collectors.groupingBy(Question::getGroup))
                .entrySet()
                .stream()
                .map(e -> jointMapper.toDto(e.getKey())
                    .setQuestions(e.getValue().stream()
                        .map(jointMapper::toDto)
                        .toList()))
                .toList());
    }

    @Override
    public TemplateDto update(Long templateId, TemplateDto templateDto) throws TemplateNotFoundException {
        final Template template = this.updateInternal(
            this.findByIdInternal(templateId), templateDto);

        if (templateDto.getQuestions() != null) {
            List<Long> existingQuestionIds = questionTemplateRepository.findQuestionIdsByTemplateId(templateId);
            List<Long> newQuestionIds = templateDto.getQuestions().stream()
                .map(QuestionDto::getId)
                .collect(Collectors.toList());

            questionTemplateRepository.deleteTemplateQuestionsExcept(templateId, newQuestionIds);

            newQuestionIds.stream()
                .filter(questionId -> !existingQuestionIds.contains(questionId))
                .map(questionId -> new QuestionTemplate(
                    questionService.findByIdInternal(questionId),
                    new QuestionTemplateId(questionId, templateId)
                ))
                .forEach(questionTemplateRepository::saveAndFlush);
        }

        entityManager.refresh(template);
        return templateMapper.toDto(template);
    }

    @Override
    protected Template updateInternal(Template template, TemplateDto update) {
        setIfNotBlank(update.getName(), template::setName);
        setIfNotBlank(update.getDescription(), template::setDescription);
        setIfNotBlank(update.getThreshold(), template::setThreshold);

        return template;
    }
}