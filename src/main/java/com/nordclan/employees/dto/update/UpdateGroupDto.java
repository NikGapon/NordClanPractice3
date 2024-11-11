package com.nordclan.employees.dto.update;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO обновления группы")
public class UpdateGroupDto {

    @ApiModelProperty(value = "Уникальный идентификатор группы")
    private Long id;

    @ApiModelProperty(value = "Название группы")
    private String name;

}