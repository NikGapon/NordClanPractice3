package com.nordclan.employees.controller;

import com.nordclan.employees.dto.entity.UserDto;
import com.nordclan.employees.entity.ErrorResponse;
import com.nordclan.employees.exception.UserNotFoundException;
import com.nordclan.employees.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "Методы для управления пользователями")
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    @Operation(summary = "Создать пользователя", description = "Создать нового пользователя", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Успешная операция", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserDto.class)))
    public UserDto createUser(@RequestBody @Valid @Parameter(description = "DTO для создания пользователя") UserDto userDto) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/all")
    @Operation(summary = "Получить всех пользователей", description = "Получить список всех пользователей", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Успешная операция", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserDto.class)))
    public List<UserDto> allUser(Pageable pageable) {
        return userService.findAll();
    }

    @DeleteMapping("/delete/{userId}")
    @Operation(summary = "Удалить пользователя", description = "Удалить пользователя по ID", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Успешная операция", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserDto.class)))
    @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
    public UserDto deleteUser(@Parameter(description = "ID удаляемого пользователя") @PathVariable UUID userId) throws UserNotFoundException {
        return userService.delete(userId);
    }

    @PutMapping("update/{userId}")
    @Operation(summary = "Обновить пользователя", description = "Обновить пользователя по ID", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Успешная операция", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserDto.class)))
    @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
    public UserDto updateUser(@Parameter(description = "ID обновляемого пользователя") @PathVariable UUID userId, @RequestBody @Valid @Parameter(description = "DTO для обновления пользователя") UserDto userDto) throws UserNotFoundException {
        return userService.update(userId, userDto);
    }

    @GetMapping("/getUser/{userId}")
    @Operation(summary = "Получить пользователя", description = "Получить пользователя по ID", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Успешная операция", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserDto.class)))
    @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
    public UserDto getUser(@Parameter(description = "ID запрашиваемого пользователя") @PathVariable UUID userId) throws UserNotFoundException {
        return userService.findById(userId);
    }

    @GetMapping("/findUser")
    @Operation(summary = "Получить пользователя по атрибутам", description = "Получить пользователя по фамилии и ролям", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Успешная операция", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserDto.class)))
    public List<UserDto> findUser(@RequestParam @Parameter(description = "Фамилия запрашиваемого пользователя") String userLastName,
                                                  @RequestParam(required = false) @Parameter(description = "Роль запрашиваемого пользователя") String userRole) {
        return userService.findUser(userLastName, userRole);
    }
}