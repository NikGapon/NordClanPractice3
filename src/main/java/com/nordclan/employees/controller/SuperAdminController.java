package com.nordclan.employees.controller;

import com.nordclan.employees.dto.entity.RoleDto;
import com.nordclan.employees.dto.entity.UserDto;
import com.nordclan.employees.dto.request.SetRolesRequestDto;
import com.nordclan.employees.entity.ErrorResponse;
import com.nordclan.employees.exception.UserNotFoundException;
import com.nordclan.employees.service.SuperAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/superAdmin")
@RequiredArgsConstructor
@Tag(name = "SuperAdmin Controller", description = "Методы супер-админаТест")
public class SuperAdminController {

    private final SuperAdminService superAdminService;

    @PutMapping("/assignRole/{userId}")
    @Operation(summary = "Метод позволяющий задать роли юзеру", description = "Метод позволяющий задать роли юзеру", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Успешная операция", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)))
    @ApiResponse(responseCode = "404", description = "Юзер не найден", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public UserDto assignRoleToUser(@PathVariable UUID userId, @RequestBody @Valid SetRolesRequestDto setRolesRequestDto) throws UserNotFoundException {
        return superAdminService.assignRoleToUser(userId, setRolesRequestDto);
    }

    @GetMapping("/availableRoles")
    @Operation(summary = "Метод позволяющий получить все роли в системе", description = "Получить все роли доступные пользователям", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Успешная операция", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RoleDto.class)))
    public List<RoleDto> getAvailableRoles() {
        return superAdminService.getAvailableRoles();
    }

}