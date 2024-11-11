package com.nordclan.employees.dto.create;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Schema(description = "DTO создания новой группы")
public class CreateGroupDto {

    @ApiModelProperty(required = true, value = "Название группы")
    @NotBlank(message = "Необходимо указать название группы")
    private String name;

}