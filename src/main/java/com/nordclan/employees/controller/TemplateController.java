package com.nordclan.employees.controller;

import com.nordclan.employees.dto.create.CreateTemplateDto;
import com.nordclan.employees.dto.entity.TemplateDto;
import com.nordclan.employees.dto.request.LinkedQuestionDto;
import com.nordclan.employees.dto.response.TemplateJointDto;
import com.nordclan.employees.entity.ErrorResponse;
import com.nordclan.employees.exception.TemplateAlreadyExistsException;
import com.nordclan.employees.exception.TemplateNotFoundException;
import com.nordclan.employees.service.TemplateService;
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
@RequestMapping("/template")
@Tag(name = "Template Controller", description = "Методы для управления шаблонами")
public class TemplateController {

    private final TemplateService templateService;

    @GetMapping("/all")
    public Page<TemplateDto> allTemplates(Pageable pageable) {
        return templateService.findAll(pageable);
    }

    @GetMapping("/getTemplate/{templateId}")
    @Operation(summary = "Получить шаблон по ID", description = "Получить шаблон по указанному ID", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Успешная операция", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TemplateDto.class)))
    @ApiResponse(responseCode = "404", description = "Шаблон не найден", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public TemplateDto getTemplate(@Parameter(description = "ID запрашиваемого шаблона") @PathVariable Long templateId) throws TemplateNotFoundException {
        return templateService.findById(templateId);
    }

    @GetMapping("/getTemplateByName")
    @Operation(summary = "Получить шаблон по ID", description = "Получить шаблон по указанному имени", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Успешная операция", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TemplateDto.class)))
    @ApiResponse(responseCode = "404", description = "Шаблон не найден", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public List<TemplateDto> getTemplateByName(@Parameter(description = "Имя запрашиваемого шаблона") @RequestParam String templateName) throws TemplateNotFoundException {
        return templateService.findByName(templateName);
    }

    @GetMapping("/getJointTemplate/{templateId}")
    @Operation(summary = "Получить объединенный шаблон по ID", description = "Получить объединенный шаблон по указанному ID", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Успешная операция", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TemplateJointDto.class)))
    @ApiResponse(responseCode = "404", description = "Объединенный шаблон не найден", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public TemplateJointDto getJointTemplate(@Parameter(description = "ID запрашиваемого шаблона") @PathVariable Long templateId) throws TemplateNotFoundException {
        return templateService.findJointTemplateById(templateId);
    }

    @PostMapping("/create")
    @Operation(summary = "Создать шаблон", description = "Создать новый шаблон", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Успешная операция", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TemplateDto.class)))
    @ApiResponse(responseCode = "409", description = "Шаблон уже существует", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public TemplateDto createTemplate(@RequestBody @Valid @Parameter(description = "DTO для создания шаблона") CreateTemplateDto templateDto) throws TemplateAlreadyExistsException {
        return templateService.createTemplate(templateDto);
    }

    //toDo подумать над put
    @PostMapping("/linkQuestion/{templateId}") //toDo подумать над put
    @Operation(summary = "Связать вопрос с шаблоном", description = "Связывает указанный вопрос с шаблоном по ID.", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Успешная операция", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TemplateDto.class)))
    public TemplateDto linkQuestion(@Parameter(description = "ID шаблона") @PathVariable Long templateId, @RequestBody @Valid @Parameter(description = "DTO для связи вопроса") LinkedQuestionDto linkedQuestionDto) {
        return templateService.linkQuestions(templateId, linkedQuestionDto.getQuestions());
    }

    @DeleteMapping("/delete/{templateId}")
    @Operation(summary = "Удалить шаблон", description = "Удалить шаблон по ID", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Успешная операция", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TemplateDto.class)))
    @ApiResponse(responseCode = "404", description = "Шаблон не найден", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public void deleteTemplate(@Parameter(description = "ID удаляемого шаблона") @PathVariable Long templateId) throws TemplateNotFoundException {
        templateService.delete(templateId);
    }

    @PutMapping("update/{templateId}")
    @Operation(summary = "Обновить шаблон", description = "Обновить шаблон по ID", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Успешная операция", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TemplateDto.class)))
    @ApiResponse(responseCode = "404", description = "Шаблон не найден", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public TemplateDto updateTemplate(@Parameter(description = "ID обновляемого шаблона") @PathVariable Long templateId, @RequestBody @Valid @Parameter(description = "DTO для обновления шаблона") TemplateDto templateDto) throws TemplateNotFoundException {
        return templateService.update(templateId, templateDto);
    }

}