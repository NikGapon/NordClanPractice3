package com.nordclan.employees.dto.request;

import com.nordclan.employees.entity.RoleNames;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO ролей задаваемых пользователю")
public class SetRolesRequestDto {

    @ApiModelProperty(value = "Список ролей", required = true)
    @NotEmpty(message = "Необходимо указать список ролей")
    List<RoleNames> roles;

}