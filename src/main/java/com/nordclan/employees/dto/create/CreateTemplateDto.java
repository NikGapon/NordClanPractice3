package com.nordclan.employees.dto.create;

import com.nordclan.employees.dto.response.QuestionForLinkDto;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
@Schema(description = "DTO создания шаблона")
public class CreateTemplateDto {

    @ApiModelProperty(required = true, value = "Название шаблона")
    @NotBlank(message = "Необходимо указать название шаблона")
    private String name;

    @ApiModelProperty(value = "Список вопросов")
    private List<QuestionForLinkDto> questions;

    @ApiModelProperty(value = "Описание шаблона")
    private String description;

    @PositiveOrZero(message = "Пороговое значение должно быть больше или равно нулю")
    @ApiModelProperty(required = true, value = "Пороговое значение в процентах")
    private Integer threshold;

}