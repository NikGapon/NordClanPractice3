package com.nordclan.employees.dto.entity;

import com.nordclan.employees.entity.TrainingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TrainingDto {

    private Long id;
    private SimpleUserDto student;
    private SimpleUserDto trainingManager;
    private SimpleUserDto mentor;
    private Long templateId;
    private TrainingStatus trainingStatus;
    private LocalDate creationDate;
    private LocalDate updateDate;
    private LocalDate trainingEndDate;
    private LocalDate trainingStartDate;
    private LocalDate trainingPauseDate;
    private Integer examResult;

}