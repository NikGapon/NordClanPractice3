package com.nordclan.employees.service;

import com.nordclan.employees.api.DefaultService;
import com.nordclan.employees.dto.entity.TrainingDto;
import com.nordclan.employees.dto.entity.TrainingHistoryDto;
import com.nordclan.employees.entity.Training;
import com.nordclan.employees.entity.TrainingHistory;
import com.nordclan.employees.entity.TrainingStatus;
import com.nordclan.employees.exception.TrainingHistoryFoundException;
import com.nordclan.employees.mapper.TrainingHistoryMapper;
import com.nordclan.employees.repository.TrainingHistoryRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
public class TrainingHistoryService extends DefaultService<Long, TrainingHistory, TrainingHistoryDto> {

    TrainingHistoryRepository trainingHistoryRepository;
    TrainingHistoryMapper trainingHistoryMapper;

    public TrainingHistoryService(
        TrainingHistoryRepository repository,
        TrainingHistoryMapper mapper
    ) {
        super(repository, mapper, TrainingHistoryFoundException::new);
        this.trainingHistoryRepository = repository;
        this.trainingHistoryMapper = mapper;
    }

    public List<TrainingHistoryDto> getTrainigHistByTrainingId(Long requestId) {
        return this.findAllByExample(TrainingHistoryDto.builder().requestId(requestId).build());
    }

    public void createTrainingHist(Training training, UUID userId) {
        saveTrainingHist(training.getId(), userId, "CREATE", "trainingStatus", null, String.valueOf(training.getTrainingStatus()), "String");
        Map<String, Object> fields = Map.of(
            "trainingEndDate", training.getTrainingEndDate(),
            "mentorId", training.getMentorId(),
            "templateId", training.getTemplateId(),
            "studentId", training.getStudentId(),
            "trainingManagerId", training.getTrainingManagerId()
        );
        fields.forEach((field, value) -> {
            if (value != null) {
                String type = value.getClass().getSimpleName();
                saveTrainingHist(training.getId(), userId, "CREATE", field, null, String.valueOf(value), type);
            }
        });
    }

    public void updateStatus(TrainingDto training, UUID userId, TrainingStatus oldStatus, TrainingStatus newStatus) {
        updateFieldIfChanged(training.getId(), userId, "trainingStatus", oldStatus.name(), newStatus.name(), "String");
    }

    public void updateFields(TrainingDto training, UUID userId, TrainingDto updateTrainingDto) {
        updateFieldIfChanged(training.getId(), userId, "studentId", training.getStudent(), updateTrainingDto.getStudent(), "UUID");
        updateFieldIfChanged(training.getId(), userId, "mentorId", training.getMentor(), updateTrainingDto.getMentor(), "UUID");
        updateFieldIfChanged(training.getId(), userId, "templateId", training.getTemplateId(), updateTrainingDto.getTemplateId(), "Long");
        updateFieldIfChanged(training.getId(), userId, "trainingManagerId", training.getTrainingManager(), updateTrainingDto.getTrainingManager(), "UUID");
        updateFieldIfChanged(training.getId(), userId, "trainingEndDate", training.getTrainingEndDate(), updateTrainingDto.getTrainingEndDate(), "LocalDate");
        updateFieldIfChanged(training.getId(), userId, "creationDate", training.getCreationDate(), updateTrainingDto.getCreationDate(), "LocalDate");
    }

    private void updateFieldIfChanged(Long requestId, UUID userId, String field, Object prevValue, Object newValue, String type) {
        if (!Objects.equals(prevValue, newValue)) {
            saveTrainingHist(requestId, userId, "UPDATE", field, String.valueOf(prevValue), String.valueOf(newValue), type);
        }
    }

    private void saveTrainingHist(Long requestId, UUID userId, String action, String field, String prevValue, String valueStr, String type) {
        TrainingHistory trainingHistory = TrainingHistory.builder()
            .requestId(requestId)
            .createdAt(LocalDateTime.now())
            .userId(userId)
            .action(action)
            .field(field)
            .prevValue(prevValue != null ? prevValue : "")
            .valueStr(valueStr)
            .type(type)
            .build();
        trainingHistoryRepository.save(trainingHistory);
    }
}