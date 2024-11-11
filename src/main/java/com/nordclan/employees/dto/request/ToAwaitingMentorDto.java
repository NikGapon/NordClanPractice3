package com.nordclan.employees.dto.request;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Schema(description = "DTO ожидания подтверждения ментора")
@AllArgsConstructor
public class ToAwaitingMentorDto {

    @ApiModelProperty(value = "Идентификатор запроса на обучение", required = true)
    @NotNull(message = "Необходимо указать запрос на обучение")
    private Long requestId;

    @ApiModelProperty(value = "Идентификатор шаблона", required = true)
    @NotNull(message = "Необходимо указать шаблон")
    private Long templateId;

}