package com.nordclan.employees.handler;

import com.nordclan.employees.dto.entity.TrainingDto;
import com.nordclan.employees.entity.Training;
import com.nordclan.employees.mapper.TrainingMapper;
import com.nordclan.employees.service.SecurityService;
import com.nordclan.employees.service.TrainingHistoryService;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;

@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
@Transactional(propagation = Propagation.MANDATORY)
@Component
@Scope(SCOPE_REQUEST)
public class TrainingEventHandler {
    TrainingHistoryService trainingHistoryService;
    TrainingMapper trainingMapper;
    SecurityService securityService;

    Map<Long, TrainingDto> oldEntityMap = new ConcurrentHashMap<>();

    public TrainingEventHandler(
        @Lazy TrainingHistoryService trainingHistoryService,
        TrainingMapper trainingMapper,
        SecurityService securityService
    ) {
        this.trainingHistoryService = trainingHistoryService;
        this.trainingMapper = trainingMapper;
        this.securityService = securityService;
    }

    @PrePersist
    public void prePersist(Training training) {
        trainingHistoryService.createTrainingHist(
            training,
            securityService.getAuthorizedUser().getId());
    }

    @PostLoad
    public void postLoad(Training training) {
        oldEntityMap.put(training.getId(), trainingMapper.toDto(training));
    }

    @PreUpdate
    public void preUpdate(Training newEntity) {
        final UUID currentUserId = securityService.getAuthorizedUser().getId();

        final TrainingDto oldEntity = this.oldEntityMap.get(newEntity.getId());

        if (oldEntity.getTrainingStatus() != newEntity.getTrainingStatus()) {
            trainingHistoryService.updateStatus(
                oldEntity,
                currentUserId,
                oldEntity.getTrainingStatus(),
                newEntity.getTrainingStatus());
        } else {
            trainingHistoryService.updateFields(
                oldEntity,
                currentUserId,
                trainingMapper.toDto(newEntity));
        }
    }
}
