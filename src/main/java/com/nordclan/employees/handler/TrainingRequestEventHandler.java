package com.nordclan.employees.handler;

import com.nordclan.employees.dto.entity.TrainingRequestDto;
import com.nordclan.employees.entity.TrainingRequest;
import com.nordclan.employees.entity.TrainingRequestStatus;
import com.nordclan.employees.mapper.TrainingRequestMapper;
import com.nordclan.employees.service.RequestHistoryService;
import com.nordclan.employees.service.SecurityService;
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
public class TrainingRequestEventHandler {
    TrainingRequestMapper trainingRequestMapper;
    RequestHistoryService requestHistoryService;
    SecurityService securityService;

    Map<Long, TrainingRequestDto> oldEntityMap = new ConcurrentHashMap<>();

    public TrainingRequestEventHandler(
        @Lazy RequestHistoryService requestHistoryService,
        TrainingRequestMapper trainingRequestMapper,
        SecurityService securityService
    ) {
        this.requestHistoryService = requestHistoryService;
        this.trainingRequestMapper = trainingRequestMapper;
        this.securityService = securityService;
    }

    @PrePersist
    public void prePersist(TrainingRequest trainingRequest) {
        requestHistoryService.createRequestHist(
            trainingRequest,
            securityService.getAuthorizedUser().getId());
    }

    @PostLoad
    public void postLoad(TrainingRequest trainingRequest) {
        oldEntityMap.put(trainingRequest.getId(), trainingRequestMapper.toDto(trainingRequest));
    }

    @PreUpdate
    public void preUpdate(TrainingRequest newEntity) {
        final UUID currentUserId = securityService.getAuthorizedUser().getId();

        final TrainingRequestDto oldEntity = this.oldEntityMap.get(newEntity.getId());

        if (oldEntity.getTrainingStatus() != newEntity.getTrainingStatus()) {
            if (newEntity.getTrainingStatus() == TrainingRequestStatus.AWAITING_MENTOR) {
                requestHistoryService.updateFieldsForAwaitingMentor(
                    oldEntity,
                    currentUserId,
                    newEntity.getTemplateId());
            } else if (newEntity.getTrainingStatus() == TrainingRequestStatus.MENTOR_APPOINTED) {
                requestHistoryService.updateFieldsForMentorAppointed(
                    oldEntity,
                    currentUserId,
                    newEntity.getMentorId(),
                    newEntity.getTrainingEndDate());
            }
        } else {
            requestHistoryService.updateFields(
                oldEntity,
                currentUserId,
                trainingRequestMapper.toDto(newEntity));
        }
    }
}
