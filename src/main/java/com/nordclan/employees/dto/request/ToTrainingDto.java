package com.nordclan.employees.dto.request;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Schema(description = "DTO перевода в обучение")
@AllArgsConstructor
@Builder(toBuilder = true)
@NoArgsConstructor
public class ToTrainingDto {

    @ApiModelProperty(value = "Идентификатор запроса на обучение", required = true)
    @NotNull(message = "Необходимо указать запрос на обучение")
    private Long requestId;

    @ApiModelProperty(value = "Идентификатор ментора", required = true)
    @NotNull(message = "Необходимо указать ментора")
    private UUID mentorId;

    @ApiModelProperty(value = "Дата окончания обучения", required = true)
    @NotNull(message = "Необходимо указать дату окончания обучения")
    private LocalDate trainingEndDate;

}