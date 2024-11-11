package com.nordclan.employees.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RolePermissionsResponseDto {

    private List<String> roles;

    private List<String> permissions;

}