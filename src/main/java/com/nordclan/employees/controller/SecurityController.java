package com.nordclan.employees.controller;

import com.nordclan.employees.dto.entity.UserDto;
import com.nordclan.employees.entity.User;
import com.nordclan.employees.service.SecurityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security")
@RequiredArgsConstructor
@Tag(name = "Security Controller", description = "Методы для получения информации о текущем пользователе")
public class SecurityController {

    private final SecurityService securityService;

    @GetMapping("/getAuthorizedUser")
    @Operation(summary = "Получить авторизованного пользователя", description = "Возвращает информацию об авторизованном пользователе из контекста безопасности.", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Успешно получен авторизованный пользователь", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = User.class)))
    public UserDto getAuthorizedUser() {
        return securityService.getAuthorizedUser();
    }

}
