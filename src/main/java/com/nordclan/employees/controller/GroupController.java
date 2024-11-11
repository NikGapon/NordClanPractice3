package com.nordclan.employees.controller;

import com.nordclan.employees.dto.create.CreateGroupDto;
import com.nordclan.employees.dto.entity.GroupDto;
import com.nordclan.employees.entity.ErrorResponse;
import com.nordclan.employees.exception.GroupNotFoundException;
import com.nordclan.employees.service.GroupService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
@Tag(name = "Group Controller", description = "Методы для управления группами")
public class GroupController {

    private final GroupService groupService;

    @GetMapping("/getGroup/{groupId}")
    @Operation(summary = "Получить группу", description = "Получить группу по ID", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Успешная операция", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GroupDto.class)))
    @ApiResponse(responseCode = "404", description = "Группа не найдена", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
    public GroupDto getGroup(@Parameter(description = "ID запрашиваемой группы") @PathVariable Long groupId) throws GroupNotFoundException {
        return groupService.findById(groupId);
    }

    @GetMapping("/all")
    @Operation(summary = "Получить все группы", description = "Получить список всех групп", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Успешная операция", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GroupDto.class)))
    public List<GroupDto> allGroups() {
        return groupService.findAll();
    }

    @PostMapping("/create")
    @Operation(summary = "Создать группу", description = "Создать новую группу", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Успешная операция", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GroupDto.class)))
    public GroupDto createGroup(@RequestBody @Valid @Parameter(description = "DTO для создания группы") CreateGroupDto request) {
        return groupService.create(request);
    }

    @DeleteMapping("/delete/{groupId}")
    @Operation(summary = "Удалить группу", description = "Удалить группу по ID", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Успешная операция")
    @ApiResponse(responseCode = "404", description = "Группа не найдена", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
    public void deleteGroup(@Parameter(description = "ID удаляемой группы") @PathVariable Long groupId) throws GroupNotFoundException {
        groupService.delete(groupId);
    }

    @PutMapping("/update/{groupId}")
    @Operation(summary = "Обновить группу", description = "Обновить группу по ID", security = @SecurityRequirement(name = "accessToken"))
    @ApiResponse(responseCode = "200", description = "Успешная операция", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GroupDto.class)))
    @ApiResponse(responseCode = "404", description = "Группа не найдена", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
    public GroupDto updateGroup(@Parameter(description = "ID обновляемой группы") @PathVariable Long groupId, @RequestBody @Valid @Parameter(description = "DTO для обновления группы") GroupDto groupDto) throws GroupNotFoundException {
        return groupService.update(groupId, groupDto);
    }

}