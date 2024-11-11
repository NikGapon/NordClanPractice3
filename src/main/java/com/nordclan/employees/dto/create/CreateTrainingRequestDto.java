package com.nordclan.employees.dto.create;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreateTrainingRequestDto {

    @ApiModelProperty(required = true, value = "Менеджер обучения")
    @NotNull(message = "Необходимо указать менеджера обучения")
    private UUID trainingManagerId;

    @ApiModelProperty(required = true, value = "Студент")
    @NotNull(message = "Необходимо указать студента")
    private UUID studentId;

}
