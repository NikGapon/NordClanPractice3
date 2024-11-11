package com.nordclan.employees.controller;

import com.nordclan.employees.dto.entity.QuestionResultDto;
import com.nordclan.employees.dto.request.AssessTrainingRequestDto;
import com.nordclan.employees.dto.response.QuestionResultDtoFull;
import com.nordclan.employees.entity.ErrorResponse;
import com.nordclan.employees.exception.QuestionNotFoundException;
import com.nordclan.employees.exception.QuestionNotLinkedWithTemplateException;
import com.nordclan.employees.exception.TrainingNotFoundException;
import com.nordclan.employees.service.AssessmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/assessment")
@RequiredArgsConstructor
@Tag(name = "Assessment Controller", description = "Методы для управления оценками")
public class AssessmentController {

    private final AssessmentService assessmentService;

    @PostMapping("/assess/{trainingId}/{questionId}")
    @Operation(summary = "Оценить вопрос в обучении", description = "Оценивает вопрос в обучении, сохраняет результат.", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Результат оценки сохранен.", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = QuestionResultDto.class)))
    @ApiResponse(responseCode = "404", description = "Вопрос, обучение или связь вопроса с шаблоном не найдены.", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
    public QuestionResultDto assess(@Parameter(description = "ID обучения") @PathVariable Long trainingId, @Parameter(description = "ID вопроса") @PathVariable Long questionId, @RequestBody @Valid @Parameter(description = "Результат оценки") QuestionResultDto questionResultDto) throws QuestionNotFoundException, TrainingNotFoundException, QuestionNotLinkedWithTemplateException {
        return assessmentService.assessTraining(trainingId, questionId, questionResultDto);
    }

    @GetMapping("/getAssessments/{trainingId}")
    @Operation(summary = "Получить все результаты оценки по обучению", description = "Возвращает список результатов оценки для указанного обучения.", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Список результатов оценки.", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = QuestionResultDto.class)))
    @ApiResponse(responseCode = "404", description = "Обучение не найдено.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public List<QuestionResultDtoFull> getAllResultByTraining(@Parameter(description = "ID обучения") @PathVariable Long trainingId) throws TrainingNotFoundException {
        return assessmentService.getAllResultByTraining(trainingId);
    }

    @PostMapping("/assess")
    @Operation(summary = "Сохранение оценки вопросов", security = @SecurityRequirement(name = "accessToken"))
    public void assessQuestions(@RequestBody @Valid AssessTrainingRequestDto request) {
        assessmentService.assessQuestions(request);
    }

}