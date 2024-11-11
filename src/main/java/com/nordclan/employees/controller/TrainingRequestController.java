package com.nordclan.employees.controller;

import com.nordclan.employees.dto.create.CreateTrainingRequestDto;
import com.nordclan.employees.dto.entity.TrainingRequestDto;
import com.nordclan.employees.dto.request.AllTrainingRequestsByStatusDto;
import com.nordclan.employees.dto.request.ChangeTrainingRequestStatusDto;
import com.nordclan.employees.dto.update.UpdateTrainingRequestDto;
import com.nordclan.employees.entity.ErrorResponse;
import com.nordclan.employees.entity.TrainingRequest;
import com.nordclan.employees.exception.NonApplicableStatus;
import com.nordclan.employees.exception.TrainingNotFoundException;
import com.nordclan.employees.exception.TrainingRequestNotFoundException;
import com.nordclan.employees.exception.UserNotFoundException;
import com.nordclan.employees.mapper.TrainingRequestMapper;
import com.nordclan.employees.service.TrainingRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/trainingReq")
@RequiredArgsConstructor
public class TrainingRequestController {

    private final TrainingRequestService trainingRequestService;
    private final TrainingRequestMapper trainingRequestMapper;

    @GetMapping("/getTrainingRequest/{trainingRequestId}")
    @Operation(summary = "Получить запрос на обучение", description = "Получает запрос на обучение по ID", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Запрос на обучение успешно отменен", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TrainingRequest.class)))
    @ApiResponse(responseCode = "404", description = "Запрос на обучение не найден", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public TrainingRequestDto getTrainingRequest(@PathVariable Long trainingRequestId) throws TrainingRequestNotFoundException {
        return trainingRequestService.findById(trainingRequestId);
    }

    @GetMapping("/getAllTrainingRequests")
    @Operation(summary = "Получить все запросы на обучение", description = "Получает все запросы на обучение", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Запросы на обучение успешно получены", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TrainingRequest.class)))
    @ApiResponse(responseCode = "404", description = "Запросы на обучение не получены", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public Page<TrainingRequestDto> getAllTrainingRequests(Pageable pageable) {
        return trainingRequestService.findAll(pageable);
    }

    @PutMapping("/changeStatus")
    @Operation(summary = "Изменить статус заявки на обучение", description = "Изменяет статус заявки на обучение", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Запрос на обучение успешно отменен", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TrainingRequest.class)))
    @ApiResponse(responseCode = "404", description = "Запрос на обучение не найден", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public Object changeTrainingRequestStatus(@RequestBody @Valid ChangeTrainingRequestStatusDto dto) throws TrainingNotFoundException, TrainingRequestNotFoundException, NonApplicableStatus {
        return switch (dto.getNewStatus()) {
            case AWAITING_MENTOR, CANCELED -> trainingRequestService.changeTrainingStatus(dto);
            case MENTOR_APPOINTED -> trainingRequestService.changeStatusToTraining(dto);
            default -> throw new IllegalArgumentException("Invalid status");
        };
    }

    @PostMapping("/create")
    @Operation(summary = "Создать запрос на обучение", description = "Создает новый запрос на обучение", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Запрос на обучение успешно создан", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TrainingRequestDto.class)))
    @ApiResponse(responseCode = "400", description = "Неверные данные обучения", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public TrainingRequestDto createRequest(@RequestBody @Valid CreateTrainingRequestDto createTrainingRequestDto) throws UserNotFoundException {
        return trainingRequestService.createTrainingRequest(createTrainingRequestDto);
    }

    @PostMapping("/allByStatus")
    @Operation(summary = "Получить запросы на обучение по статусам", description = "Получить запросы на обучение по статусам", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Запрос на обучение успешно создан", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TrainingRequestDto.class)))
    @ApiResponse(responseCode = "400", description = "Неверные данные", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public List<TrainingRequestDto> findAllByTrainingStatusIn(@RequestBody AllTrainingRequestsByStatusDto allTrainingRequestsByStatusDto) {
        return trainingRequestService.findAllByTrainingStatusIn(allTrainingRequestsByStatusDto.getStatusList());
    }

    @PutMapping("update/{requestId}")
    @Operation(summary = "Обновить запрос на обучение ", description = "Обновить запрос на обучение", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Успешная операция", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TrainingRequestDto.class)))
    @ApiResponse(responseCode = "404", description = "Запрос на обучение не изменен", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
    public TrainingRequestDto updateTrainingRequest(@Parameter(description = "DTO для обновления заявки на обучение") @RequestBody @Valid UpdateTrainingRequestDto updateTrainingRequestDto) throws TrainingRequestNotFoundException {
        TrainingRequestDto trainingRequestDto = trainingRequestMapper.toDto(updateTrainingRequestDto);
        return trainingRequestService.update(updateTrainingRequestDto.getId(), trainingRequestDto);
    }
}