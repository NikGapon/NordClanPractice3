package com.nordclan.employees.dto.update;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO обновления пользователя")
public class UpdateUserDto {

    @ApiModelProperty(value = "Уникальный идентификатор пользователя")
    private UUID id;

    @ApiModelProperty(value = "Имя пользователя")
    private String firstName;

    @ApiModelProperty(value = "Фамилия пользователя")
    private String lastName;

    @ApiModelProperty(value = "Отчество пользователя")
    private String surname;

}