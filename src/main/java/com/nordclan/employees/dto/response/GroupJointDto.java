package com.nordclan.employees.dto.response;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Accessors(chain = true)
@Schema(description = "DTO группы")
public class GroupJointDto {

    @ApiModelProperty(value = "Уникальный идентификатор группы")
    private Long id;

    @ApiModelProperty(required = true, value = "Название группы")
    private String name;

    @ApiModelProperty(required = true, value = "Список вопросов")
    private List<QuestionJointDto> questions;
}