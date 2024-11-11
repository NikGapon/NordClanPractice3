package com.nordclan.employees.dto.request;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestDto {

    @ApiModelProperty(value = "Логин", required = true)
    @NotBlank(message = "Необходимо указать логин")
    private String username;

    @ApiModelProperty(value = "Пароль", required = true)
    @NotBlank(message = "Необходимо указать пароль")
    private String password;

}