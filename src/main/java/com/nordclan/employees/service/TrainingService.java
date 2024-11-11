package com.nordclan.employees.service;

import com.nordclan.employees.api.DefaultService;
import com.nordclan.employees.dto.entity.TrainingDto;
import com.nordclan.employees.dto.request.ChangeStatusDto;
import com.nordclan.employees.entity.Training;
import com.nordclan.employees.entity.TrainingStatus;
import com.nordclan.employees.exception.NonApplicableStatus;
import com.nordclan.employees.exception.TrainingNotFoundException;
import com.nordclan.employees.mapper.TrainingMapper;
import com.nordclan.employees.repository.TrainingRepository;
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
public class TrainingService extends DefaultService<Long, Training, TrainingDto> {
    TrainingRepository trainingRepository;
    TrainingMapper trainingMapper;
    SecurityService securityService;

    public TrainingService(
        TrainingRepository trainingRepository,
        TrainingMapper trainingMapper,
        SecurityService securityService
    ) {
        super(trainingRepository, trainingMapper, TrainingNotFoundException::new);
        this.trainingRepository = trainingRepository;
        this.trainingMapper = trainingMapper;
        this.securityService = securityService;
    }

    @Transactional(readOnly = true)
    public List<TrainingDto> findAllByTrainingStatusIn(
            List<TrainingStatus> statusList

    ) {
        return trainingRepository.findAllByTrainingStatusIn(statusList).stream()
                .map(trainingMapper::toDto).toList();
    }

    @Override
    public TrainingDto findById(Long trainingId) throws TrainingNotFoundException {
        final Training training = this.findByIdInternal(trainingId);

//        if (securityService.getAuthorizedUser()
//                .getRoles()
//                .stream()
//                .anyMatch(role -> role.getRole().equals("MENTOR"))
//            && (training.getTrainingStatus().equals(TrainingStatus.AWAITING_WORK))
//        ) {
        //    training.setTrainingStatus(TrainingStatus.MENTOR_NOTIFIED);
        // }

        return trainingMapper.toDto(training);
    }

    public TrainingDto changeTrainingStatus(ChangeStatusDto request) throws NonApplicableStatus {
        final Training training = this.findByIdInternal(request.getTrainingId());
        final TrainingStatus newTrainingStatus = request.getTrainingStatus();

        if (newTrainingStatus == training.getTrainingStatus()) {
            throw new NonApplicableStatus(NonApplicableStatus.message);
        }

        if (newTrainingStatus == TrainingStatus.ON_PAUSE) {
            training.setTrainingPauseDate(LocalDate.now());
        }

        return trainingMapper.toDto(training.setTrainingStatus(newTrainingStatus));
    }

    @Override
    protected Training updateInternal(Training training, TrainingDto update) {
        setIfNotBlank(update.getMentor().getId(), training::setMentorId);
        setIfNotBlank(update.getTrainingManager().getId(), training::setTrainingManagerId);
        setIfNotBlank(update.getTemplateId(), training::setTemplateId);
        setIfNotBlank(update.getTrainingEndDate(), training::setTrainingEndDate);
        setIfNotBlank(update.getTrainingStartDate(), training::setTrainingStartDate);
        setIfNotBlank(update.getTrainingPauseDate(), training::setTrainingPauseDate);
        setIfNotBlank(update.getCreationDate(), training::setCreationDate);
        setIfNotBlank(update.getUpdateDate(), training::setUpdateDate);

        return training;
    }
}