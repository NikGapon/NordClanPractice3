package com.nordclan.employees.dto.request;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestRefreshTokenDto {

    @ApiModelProperty(value = "Токен обновления", required = true)
    @NotBlank(message = "Необходимо указать токен обновления")
    String refreshToken;

}
