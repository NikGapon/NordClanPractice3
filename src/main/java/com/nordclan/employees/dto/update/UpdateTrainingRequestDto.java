package com.nordclan.employees.dto.update;

import com.nordclan.employees.entity.TrainingRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTrainingRequestDto {

    private Long id;

    private UUID studentId;

    private UUID authorId;

    private UUID trainingManagerId;

    private UUID mentorId;

    private Long templateId;

    private TrainingRequestStatus trainingStatus;

    private LocalDate creationDate;

    private LocalDate updateDate;

    private LocalDate endDate;

    private Boolean isDeleted;

}