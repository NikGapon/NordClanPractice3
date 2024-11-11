package com.nordclan.employees.service;

import com.nordclan.employees.api.DefaultService;
import com.nordclan.employees.dto.create.CreateTrainingRequestDto;
import com.nordclan.employees.dto.entity.TrainingDto;
import com.nordclan.employees.dto.entity.TrainingRequestDto;
import com.nordclan.employees.dto.request.ChangeTrainingRequestStatusDto;
import com.nordclan.employees.entity.Training;
import com.nordclan.employees.entity.TrainingRequest;
import com.nordclan.employees.entity.TrainingRequestStatus;
import com.nordclan.employees.entity.TrainingStatus;
import com.nordclan.employees.exception.TrainingRequestNotFoundException;
import com.nordclan.employees.exception.UserNotFoundException;
import com.nordclan.employees.mapper.TrainingMapper;
import com.nordclan.employees.mapper.TrainingRequestMapper;
import com.nordclan.employees.repository.TrainingRepository;
import com.nordclan.employees.repository.TrainingRequestRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.nordclan.employees.utils.CommonUtils.setIfNotBlank;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
@Transactional
public class TrainingRequestService extends DefaultService<Long, TrainingRequest, TrainingRequestDto> {
    TrainingRequestRepository trainingRequestRepository;
    TrainingRequestMapper trainingRequestMapper;
    TrainingRepository trainingRepository;
    SecurityService securityService;
    TrainingMapper trainingMapper;
    UserService userService;
    TemplateService templateService;

    public TrainingRequestService(
        TrainingRequestRepository trainingRequestRepository,
        TrainingRepository trainingRepository,
        SecurityService securityService,
        TrainingRequestMapper trainingRequestMapper,
        TrainingMapper trainingMapper,
        UserService userService,
        TemplateService templateService
    ) {
        super(trainingRequestRepository, trainingRequestMapper, TrainingRequestNotFoundException::new);
        this.trainingRequestRepository = trainingRequestRepository;
        this.trainingRepository = trainingRepository;
        this.securityService = securityService;
        this.trainingRequestMapper = trainingRequestMapper;
        this.trainingMapper = trainingMapper;
        this.userService = userService;
        this.templateService = templateService;
    }

    @Transactional(readOnly = true)
    public List<TrainingRequestDto> findAllByTrainingStatusIn(
        List<TrainingRequestStatus> statusList

    ) {
        return trainingRequestRepository.findAllByTrainingStatusIn(statusList).stream().map(trainingRequestMapper::toDto).toList();
    }

    public TrainingRequestDto createTrainingRequest(CreateTrainingRequestDto request) throws UserNotFoundException {
        return trainingRequestMapper.toDto(
            trainingRequestRepository.save(
                TrainingRequest.builder()
                    .trainingStatus(TrainingRequestStatus.AWAITING_PROCESSING)
                    .creationDate(LocalDate.now())
                    .authorId(securityService.getAuthorizedUser().getId())
                    .studentId(userService.findById(request.getStudentId()).getId())
                    .trainingManagerId(userService.findById(request.getTrainingManagerId()).getId())
                    .build()));
    }

    public TrainingRequestDto changeTrainingStatus(ChangeTrainingRequestStatusDto request) {
        return trainingRequestMapper.toDto(
            this.findByIdInternal(request.getRequestId())
                .setMentorId(userService.findById(request.getMentorId()).getId())
                .setTemplateId(templateService.findById(request.getTemplateId()).getId())
                .setTrainingEndDate(request.getTrainingEndDate())
                .setTrainingStatus(request.getNewStatus()));
    }

    public TrainingDto changeStatusToTraining(final ChangeTrainingRequestStatusDto request) {
        final TrainingRequestDto trainingRequest = this.changeTrainingStatus(request);

        return trainingMapper.toDto(
            trainingRepository.save(Training.builder()
                .trainingStatus(TrainingStatus.AWAITING_WORK)
                .creationDate(LocalDate.now())
                .trainingManagerId(trainingRequest.getTrainingManager().getId())
                .trainingEndDate(trainingRequest.getTrainingEndDate())
                .templateId(trainingRequest.getTemplate().getId())
                .mentorId(trainingRequest.getMentor().getId())
                .studentId(trainingRequest.getStudent().getId())
                .build()));
    }

    @Override
    protected TrainingRequest updateInternal(TrainingRequest trainingRequest, TrainingRequestDto update) {
        setIfNotBlank(update.getTrainingStatus(), trainingRequest::setTrainingStatus);
        setIfNotBlank(update.getMentor().getId(), trainingRequest::setMentorId);
        setIfNotBlank(update.getAuthor().getId(), trainingRequest::setAuthorId);
        setIfNotBlank(update.getTrainingManager().getId(), trainingRequest::setTrainingManagerId);
        setIfNotBlank(update.getTemplate().getId(), trainingRequest::setTemplateId); // Изменено с getTemplateId на getTemplate().getId()
        setIfNotBlank(update.getTrainingEndDate(), trainingRequest::setTrainingEndDate);

        return trainingRequest;
    }
}
