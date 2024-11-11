package com.nordclan.employees.dto.request;

import com.nordclan.employees.entity.TrainingStatus;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllTrainingsByStatusDto {

    @NotEmpty
    List<TrainingStatus> statusList;

}