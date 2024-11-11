package com.nordclan.employees.dto.update;

import com.nordclan.employees.entity.TrainingStatus;
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
public class UpdateTrainingDto {

    private Long id;

    private UUID studentId;

    private UUID trainingManagerId;

    private UUID mentorId;

    private Long templateId;

    private TrainingStatus trainingStatus;

    private LocalDate creationDate;

    private LocalDate trainingEndDate;
}