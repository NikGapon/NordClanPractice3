package com.nordclan.employees.dto.entity;

import com.nordclan.employees.entity.TrainingRequestStatus;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TrainingRequestDto {

    private Long id;
    private SimpleUserDto student;
    private SimpleUserDto author;
    private SimpleUserDto trainingManager;
    private SimpleUserDto mentor;
    private TemplateDto template;
    private TrainingRequestStatus trainingStatus;
    private LocalDate creationDate;
    private LocalDate updateDate;
    private LocalDate trainingEndDate;
    private Boolean isDeleted;

    @Version
    private Long version;

}