package com.nordclan.employees.controller;

import com.nordclan.employees.dto.response.RolePermissionsResponseDto;
import com.nordclan.employees.service.UserRolePermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/configuration")
@AllArgsConstructor
@Tag(name = "Action Role Controller", description = "Методы для управления ролями и разрешениями")
public class UserRolePermissionController {

    private final UserRolePermissionService userRolePermissionService;

    @GetMapping("/role")
    @Operation(summary = "Получить разрешения роли пользователя", description = "Возвращает разрешения роли, назначенной текущему пользователю.", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Разрешения роли пользователя.", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RolePermissionsResponseDto.class)))
    public RolePermissionsResponseDto getRolePermissionsByUserLogin() {
        return userRolePermissionService.getRolePermissionsByUserLogin();
    }

}