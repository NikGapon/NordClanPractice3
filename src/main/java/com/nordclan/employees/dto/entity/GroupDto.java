package com.nordclan.employees.dto.entity;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Schema(description = "DTO группы")
public class GroupDto {

    @ApiModelProperty(value = "Уникальный идентификатор группы")
    private Long id;

    @NotBlank
    @ApiModelProperty(required = true, value = "Название группы")
    private String name;
}