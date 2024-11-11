package com.nordclan.employees.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Swagger Controller", description = "Методы для перенаправления на Swagger UI")
public class SwaggerController {

    @GetMapping("/")
    @Operation(summary = "Перенаправление на Swagger UI", description = "Перенаправляет на страницу Swagger UI.", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "302", description = "Перенаправление на /swagger")
    public ResponseEntity<Void> home() {
        return ResponseEntity
                .status(302)
                .header("Location", "/swagger")
                .build();
    }
}