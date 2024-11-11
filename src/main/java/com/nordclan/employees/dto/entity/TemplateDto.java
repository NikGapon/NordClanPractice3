package com.nordclan.employees.dto.entity;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO шаблона")
public class TemplateDto {

    @ApiModelProperty(value = "Уникальный идентификатор шаблона")
    private Long id;

    @ApiModelProperty(required = true, value = "Название шаблона")
    private String name;

    @ApiModelProperty(value = "Список вопросов")
    private List<QuestionDto> questions;

    @ApiModelProperty(value = "Описание шаблона")
    private String description;

    @ApiModelProperty(required = true, value = "Пороговое значение в процентах")
    private Integer threshold;

}