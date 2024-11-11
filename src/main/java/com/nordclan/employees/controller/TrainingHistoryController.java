package com.nordclan.employees.controller;

import com.nordclan.employees.dto.entity.TrainingHistoryDto;
import com.nordclan.employees.entity.ErrorResponse;
import com.nordclan.employees.service.TrainingHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/training_hist")
@RequiredArgsConstructor
public class TrainingHistoryController {

    private final TrainingHistoryService trainingHistoryService;

    @GetMapping("/{trainingId}")
    @Operation(summary = "Получить историю обучения", description = "Получает историю обучения по ID тренинга", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "История обучения успешно получена", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TrainingHistoryDto.class)))
    @ApiResponse(responseCode = "404", description = "История обучения не найдена", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public List<TrainingHistoryDto> getTrainingHistByTrainingId(@PathVariable Long trainingId) {
        return trainingHistoryService.getTrainigHistByTrainingId(trainingId);
    }

    @GetMapping("/getAllTrainingHist")
    @Operation(summary = "Получить всю историю обучения", description = "Получает всю историю обучения", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "История обучения успешно получена", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TrainingHistoryDto.class)))
    @ApiResponse(responseCode = "404", description = "История обучения не получена", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public List<TrainingHistoryDto> getAllTrainingHist() {
        return trainingHistoryService.findAll();
    }
}