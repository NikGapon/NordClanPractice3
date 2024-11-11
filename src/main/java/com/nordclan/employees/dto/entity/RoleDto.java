package com.nordclan.employees.dto.entity;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Schema(description = "DTO роли пользователя")
public class RoleDto {

    @ApiModelProperty(value = "Уникальный идентификатор роли")
    private Long id;

    @ApiModelProperty(value = "Название роли EN")
    private String role;

    @ApiModelProperty(value = "Название роли RU")
    private String roleRu;

}