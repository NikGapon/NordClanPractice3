package com.nordclan.employees.service;

import com.nordclan.employees.api.DefaultService;
import com.nordclan.employees.dto.entity.RequestHistoryDto;
import com.nordclan.employees.dto.entity.TrainingRequestDto;
import com.nordclan.employees.entity.RequestHistory;
import com.nordclan.employees.entity.TrainingRequest;
import com.nordclan.employees.entity.TrainingRequestStatus;
import com.nordclan.employees.exception.RequestHistoryFoundException;
import com.nordclan.employees.mapper.RequestHistoryMapper;
import com.nordclan.employees.repository.RequestHistoryRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
public class RequestHistoryService extends DefaultService<Long, RequestHistory, RequestHistoryDto> {

    RequestHistoryRepository requestHistoryRepository;
    RequestHistoryMapper requestHistoryMapper;

    public RequestHistoryService(
        RequestHistoryRepository repository,
        RequestHistoryMapper mapper
    ) {
        super(repository, mapper, RequestHistoryFoundException::new);
        this.requestHistoryRepository = repository;
        this.requestHistoryMapper = mapper;
    }

    public List<RequestHistoryDto> getRequestHistByRequestId(Long requestId) {
        return this.findAllByExample(RequestHistoryDto.builder().requestId(requestId).build());
    }

    public void createRequestHist(TrainingRequest trainingRequest, UUID userId) {
        Map<String, Object> fields = Map.of(
            "trainingStatus", trainingRequest.getTrainingStatus(),
            "trainingManagerId", trainingRequest.getTrainingManagerId(),
            "studentId", trainingRequest.getStudentId(),
            "authorId", trainingRequest.getAuthorId()
        );

        fields.forEach((field, value) -> {
            String type = value.getClass().getSimpleName();
            saveRequestHist(trainingRequest.getId(), userId, "CREATE", field, null, String.valueOf(value), type);
        });
    }

    public void updateFieldsForAwaitingMentor(TrainingRequestDto trainingRequest, UUID userId, Long templateId) {
        updateFields(trainingRequest.getId(), userId, "templateId", trainingRequest.getTemplate(), templateId, "Long");
        updateFields(trainingRequest.getId(), userId, "trainingStatus", trainingRequest.getTrainingStatus(), TrainingRequestStatus.AWAITING_MENTOR, "String");
    }

    public void updateFieldsForMentorAppointed(TrainingRequestDto trainingRequest, UUID userId, UUID mentorId, LocalDate trainingEndDate) {
        updateFields(trainingRequest.getId(), userId, "mentorId", trainingRequest.getMentor(), mentorId, "UUID");
        updateFields(trainingRequest.getId(), userId, "trainingEndDate", trainingRequest.getTrainingEndDate(), trainingEndDate, "LocalDate");
        updateFields(trainingRequest.getId(), userId, "trainingStatus", trainingRequest.getTrainingStatus(), TrainingRequestStatus.MENTOR_APPOINTED, "String");
    }

    public void updateFields(TrainingRequestDto trainingRequest, UUID userId, TrainingRequestDto updateRequestTrainingDto) {
        updateFields(trainingRequest.getId(), userId, "trainingStatus", trainingRequest.getTrainingStatus(), updateRequestTrainingDto.getTrainingStatus(), "String");
        updateFields(trainingRequest.getId(), userId, "studentId", trainingRequest.getStudent(), updateRequestTrainingDto.getStudent(), "UUID");
        updateFields(trainingRequest.getId(), userId, "mentorId", trainingRequest.getMentor(), updateRequestTrainingDto.getMentor(), "UUID");
        updateFields(trainingRequest.getId(), userId, "authorId", trainingRequest.getAuthor(), updateRequestTrainingDto.getAuthor(), "UUID");
        updateFields(trainingRequest.getId(), userId, "trainingManagerId", trainingRequest.getTrainingManager(), updateRequestTrainingDto.getTrainingManager(), "UUID");
        updateFields(trainingRequest.getId(), userId, "templateId", trainingRequest.getTemplate().getId(), updateRequestTrainingDto.getTemplate().getId(), "Long");
        updateFields(trainingRequest.getId(), userId, "creationDate", trainingRequest.getCreationDate(), updateRequestTrainingDto.getCreationDate(), "LocalDate");
        updateFields(trainingRequest.getId(), userId, "trainingEndDate", trainingRequest.getTrainingEndDate(), updateRequestTrainingDto.getTrainingEndDate(), "LocalDate");
        updateFields(trainingRequest.getId(), userId, "isDeleted", trainingRequest.getIsDeleted(), updateRequestTrainingDto.getIsDeleted(), "Boolean");
    }

    private void updateFields(Long requestId, UUID userId, String field, Object prevValue, Object newValue, String type) {
        if (!Objects.equals(prevValue, newValue)) {
            saveRequestHist(requestId, userId, "UPDATE", field, String.valueOf(prevValue), String.valueOf(newValue), type);
        }
    }

    private void saveRequestHist(Long requestId, UUID userId, String action, String field, String prevValue, String valueStr, String type) {
        RequestHistory requestHistory = RequestHistory.builder()
            .requestId(requestId)
            .createdAt(LocalDateTime.now())
            .userId(userId)
            .action(action)
            .field(field)
            .prevValue(prevValue != null ? prevValue : "")
            .valueStr(valueStr)
            .type(type)
            .build();
        requestHistoryRepository.save(requestHistory);
    }
}