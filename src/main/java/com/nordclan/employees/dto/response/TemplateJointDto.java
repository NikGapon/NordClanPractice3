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
@Schema(description = "DTO объединенного шаблона")
public class TemplateJointDto {
    @ApiModelProperty(value = "Уникальный идентификатор шаблоки")
    private Long id;

    @ApiModelProperty(required = true, value = "Название шаблона")
    private String name;

    @ApiModelProperty(value = "Список групп")
    private List<GroupJointDto> groups;

    @ApiModelProperty(value = "Описание шаблона")
    private String description;

    @ApiModelProperty(required = true, value = "Пороговое значение в процентах")
    private Integer threshold;

}
