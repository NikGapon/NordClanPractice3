package com.nordclan.employees.dto.request;

import com.nordclan.employees.entity.TrainingStatus;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ChangeStatusDto {

    @ApiModelProperty(value = "Идентификатор обучения", required = true)
    @NotNull(message = "Необходимо указать обучение")
    private Long trainingId;

    @ApiModelProperty(value = "Новый статус", required = true)
    @NotNull(message = "Необходимо указать новый статус")
    private TrainingStatus trainingStatus;

}
