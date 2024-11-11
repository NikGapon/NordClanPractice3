package com.nordclan.employees.controller;

import com.nordclan.employees.dto.entity.TrainingDto;
import com.nordclan.employees.dto.request.AllTrainingsByStatusDto;
import com.nordclan.employees.dto.request.ChangeStatusDto;
import com.nordclan.employees.dto.update.UpdateTrainingDto;
import com.nordclan.employees.entity.ErrorResponse;
import com.nordclan.employees.entity.Training;
import com.nordclan.employees.exception.NonApplicableStatus;
import com.nordclan.employees.exception.TrainingNotFoundException;
import com.nordclan.employees.mapper.TrainingMapper;
import com.nordclan.employees.service.TrainingService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/training")
@RequiredArgsConstructor
@Tag(name = "Training Controller", description = "Методы для управления обучением")
public class TrainingController {

    private final TrainingService trainingService;
    private final TrainingMapper trainingMapper;

    @GetMapping("/getTraining/{trainingId}")
    @Operation(summary = "Получить обучение", description = "Получает обучение по ID", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Обучение успешно получено", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Training.class)))
    @ApiResponse(responseCode = "404", description = "Обучение не найдено", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public TrainingDto getTraining(@PathVariable Long trainingId) throws TrainingNotFoundException {
        return trainingService.findById(trainingId);
    }

    @GetMapping("/getAllTrainings")
    @Operation(summary = "Получить все обучения", description = "Получает все обучения", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Обучения успешно получены", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TrainingDto.class)))
    @ApiResponse(responseCode = "404", description = "Обучения не получены", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public List<TrainingDto> getAllTrainings() {
        return trainingService.findAll();
    }

    @PutMapping("/changeStatus")
    @Operation(summary = "Изменить статус обучения", description = "Изменяет статус обучения по ID", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Статус успешно сменен", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TrainingDto.class)))
    @ApiResponse(responseCode = "404", description = "Обучение не найдено", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public TrainingDto changeTrainingStatus(@RequestBody @Valid ChangeStatusDto changeStatusDto) throws NonApplicableStatus {
        return trainingService.changeTrainingStatus(changeStatusDto);
    }

    @PostMapping("/allByStatus")
    @Operation(summary = "Получить обучения по статусам", description = "Получить обучения по статусам", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Обучения успешно получены", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TrainingDto.class)))
    @ApiResponse(responseCode = "400", description = "Неверные данные", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public List<TrainingDto> findAllByTrainingStatusIn(@RequestBody AllTrainingsByStatusDto allTrainingsByStatusDto, Pageable pageable) {
        return trainingService.findAllByTrainingStatusIn(allTrainingsByStatusDto.getStatusList());
    }

    @PutMapping("/update")
    @Operation(summary = "Обновить обучение", description = "Обновить обучение", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Успешная операция", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TrainingDto.class)))
    @ApiResponse(responseCode = "404", description = "Обучение не изменено", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
    public TrainingDto updateTraining(@Parameter(description = "DTO для обновления обучения") @RequestBody @Valid UpdateTrainingDto updateTrainingDto) throws TrainingNotFoundException {
        TrainingDto trainingDto = trainingMapper.toDto(updateTrainingDto);
        return trainingService.update(updateTrainingDto.getId(), trainingDto);
    }

}