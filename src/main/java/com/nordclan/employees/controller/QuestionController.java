package com.nordclan.employees.controller;

import com.nordclan.employees.dto.create.CreateQuestionDto;
import com.nordclan.employees.dto.entity.QuestionDto;
import com.nordclan.employees.entity.ErrorResponse;
import com.nordclan.employees.exception.GroupNotFoundException;
import com.nordclan.employees.exception.QuestionNotFoundException;
import com.nordclan.employees.exception.TemplateNotFoundException;
import com.nordclan.employees.service.QuestionService;
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


@RestController
@RequiredArgsConstructor
@RequestMapping("/question")
@Tag(name = "Question Controller", description = "Методы для управления вопросами")
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/getQuestion/{questionId}")
    @Operation(summary = "Получить вопрос", description = "Получить вопрос по ID", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Успешная операция", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = QuestionDto.class)))
    @ApiResponse(responseCode = "404", description = "Вопрос не найден", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
    public QuestionDto getQuestion(@Parameter(description = "ID запрашиваемого вопроса") @PathVariable Long questionId) throws QuestionNotFoundException {
        return questionService.findById(questionId);
    }

    @GetMapping("/all")
    @Operation(summary = "Получить все вопросы", description = "Получить список всех вопросов по фильтру, если передать groupId - будут вопросы в разрезе группы, без groupId - вообще все вопросы", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Успешная операция", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = QuestionDto.class)))
    public List<QuestionDto> allQuestion(@Parameter(description = "ID группы") @RequestParam(required = false) Long groupId, Pageable pageable) throws GroupNotFoundException {
        if (groupId == null) {
            return questionService.findAll();
        } else {
            return questionService.findAllByGroup(groupId);
        }
    }

    @PostMapping("/create")
    @Operation(summary = "Создать вопрос", description = "Создать новый вопрос", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Успешная операция", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = QuestionDto.class)))
    public QuestionDto createQuestion(@RequestBody @Valid @Parameter(description = "DTO для создания вопроса") CreateQuestionDto questionDto) throws GroupNotFoundException, TemplateNotFoundException {
        return questionService.create(questionDto);
    }

    @DeleteMapping("/delete/{questionId}")
    @Operation(summary = "Удалить вопрос", description = "Удалить вопрос по ID", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Успешная операция")
    @ApiResponse(responseCode = "404", description = "Вопрос не найден", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
    public void deleteQuestion(@Parameter(description = "ID удаляемого вопроса") @PathVariable Long questionId) throws QuestionNotFoundException {
        questionService.delete(questionId);
    }

    @PutMapping("update/{questionId}")
    @Operation(summary = "Обновить вопрос", description = "Обновить вопрос по ID", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Успешная операция", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = QuestionDto.class)))
    @ApiResponse(responseCode = "404", description = "Вопрос не найден", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
    public QuestionDto updateQuestion(@Parameter(description = "ID обновляемого вопроса") @PathVariable Long questionId, @RequestBody @Valid @Parameter(description = "DTO для обновления вопроса") QuestionDto questionDto) throws QuestionNotFoundException {
        return questionService.update(questionId, questionDto);
    }

}