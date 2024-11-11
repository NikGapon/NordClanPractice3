package com.nordclan.employees.dto.request;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class AssessTrainingRequestDto {

    @ApiModelProperty(value = "Идентификатор обучения", required = true)
    @NotNull(message = "Необходимо указать обучение")
    Long trainingId;

    @ApiModelProperty(value = "Список оценок", required = true)
    @NotEmpty(message = "Необходимо указать список оценок")
    @Valid
    List<Assessment> assessmentList;

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Assessment {
        @ApiModelProperty(value = "Идентификатор вопроса", required = true)
        @NotNull(message = "Необходимо указать вопрос в оценке вопроса")
        Long questionId;

        @ApiModelProperty(value = "Оценка вопроса", required = true)
        @Range(min = 1, max = 3, message = "Оценка вопроса должна быть равна 1, 2, 3")
        int points;
    }

}