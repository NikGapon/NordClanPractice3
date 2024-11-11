package com.nordclan.employees.controller;

import com.nordclan.employees.dto.entity.*;
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
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.Triple;
import org.springframework.data.util.Pair;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/userInfo")
@RequiredArgsConstructor
@Tag(name = "User Info Controller", description = "Получение информации о пользователе ")
public class UserInfoController {


    private final UserService userService;

    @GetMapping("/check/{userId}")
    @Operation(summary = "получить информацию", description = "получить информацию о пользователе", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Успешная операция", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Pair.class)))
    @ApiResponse(responseCode = "404", description = "Вопрос не найден", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
    public Triple<SimpleUserDto,  Map<Long, TemplateDto>, Integer> getUserInfo(@Parameter(description = "ID запрашиваемого юзера") @PathVariable UUID userId) throws UserNotFoundException {
        return userService.getUserInfo(userId);
    }
    @GetMapping("/honestCompare/{user1Id}/{user2Id}")
    @Operation(summary = "Сравнить юзеров", description = "сравнить двоих юзеров по одинаковым критериям", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Успешная операция", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Pair.class)))
    @ApiResponse(responseCode = "404", description = "Вопрос не найден", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
    public Pair<UserHonestCompareDto, Map<String, String>> userHonestCompare(@Parameter(description = "ID запрашиваемых юзеров") @PathVariable UUID user1Id, @Parameter(description = "ID запрашиваемых юзеров") @PathVariable UUID user2Id) throws UserNotFoundException {
        return userService.honestCompare2UsersInfo(user1Id, user2Id);
    }
    @GetMapping("/topofthetemplate/{templateId}")
    @Operation(summary = "Получить топ по шаблону", description = "Получить топ людей прошедших этот шаблон", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Успешная операция", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Pair.class)))
    @ApiResponse(responseCode = "404", description = "Вопрос не найден", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
    public Pair<String, Map<Triple<String, UUID, Integer>, String>> topUsersoftheTemplate(@Parameter(description = "ID шаблона") @PathVariable Long templateId) throws UserNotFoundException {
        return userService.topUsersoftheTemplate(templateId);
    }


}
