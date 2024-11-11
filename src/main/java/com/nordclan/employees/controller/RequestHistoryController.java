package com.nordclan.employees.controller;

import com.nordclan.employees.dto.entity.RequestHistoryDto;
import com.nordclan.employees.entity.ErrorResponse;
import com.nordclan.employees.service.RequestHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/request_hist")
@RequiredArgsConstructor
@Tag(name = "RequestHist Controller", description = "Методы для управления историей заявок")
public class RequestHistoryController {

    private final RequestHistoryService requestHistoryService;

    @GetMapping("/getAllRequestHist")
    @Operation(summary = "Получить всю историю заявок", description = "Получает всю историю заявок с поддержкой пагинации и сортировки", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "История заявок успешно получена", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RequestHistoryDto.class)))
    @ApiResponse(responseCode = "404", description = "История заявок не найдена", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public List<RequestHistoryDto> getAllRequestHist() {
        return requestHistoryService.findAll();
    }

    @GetMapping("/getRequestHistByRequestId/{requestId}")
    @Operation(summary = "Получить историю заявки по ID", description = "Получает историю заявки по ID", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "История заявки успешно получена", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RequestHistoryDto.class)))
    @ApiResponse(responseCode = "404", description = "История заявки не найдена", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public List<RequestHistoryDto> getRequestHistByRequestId(@PathVariable Long requestId) {
        return requestHistoryService.getRequestHistByRequestId(requestId);
    }
}