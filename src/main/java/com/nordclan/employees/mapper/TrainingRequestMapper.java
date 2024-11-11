package com.nordclan.employees.mapper;

import com.nordclan.employees.api.DefaultMapper;
import com.nordclan.employees.dto.entity.SimpleUserDto;
import com.nordclan.employees.dto.entity.TemplateDto;
import com.nordclan.employees.dto.entity.TrainingRequestDto;
import com.nordclan.employees.dto.update.UpdateTrainingRequestDto;
import com.nordclan.employees.entity.TrainingRequest;
import com.nordclan.employees.service.TemplateService;
import com.nordclan.employees.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.UUID;
@Mapper(componentModel = "spring", uses = {UserMapper.class, TemplateMapper.class})
public abstract class TrainingRequestMapper implements DefaultMapper<TrainingRequest, TrainingRequestDto> {

    @Autowired
    @Lazy
    private TemplateService templateService;

    @Autowired
    @Lazy
    private UserService userService;

    @Mapping(target = "student", expression = "java(mapUUIDToSimpleUserDto(trainingRequest.getStudentId()))")
    @Mapping(target = "author", expression = "java(mapUUIDToSimpleUserDto(trainingRequest.getAuthorId()))")
    @Mapping(target = "trainingManager", expression = "java(mapUUIDToSimpleUserDto(trainingRequest.getTrainingManagerId()))")
    @Mapping(target = "mentor", expression = "java(mapUUIDToSimpleUserDto(trainingRequest.getMentorId()))")
    @Mapping(target = "template", expression = "java(mapTemplateIdToTemplateDto(trainingRequest.getTemplateId()))")
    public abstract TrainingRequestDto toDto(TrainingRequest trainingRequest);

    @Mapping(target = "studentId", expression = "java(mapSimpleUserDtoToUUID(trainingRequestDto.getStudent()))")
    @Mapping(target = "authorId", expression = "java(mapSimpleUserDtoToUUID(trainingRequestDto.getAuthor()))")
    @Mapping(target = "trainingManagerId", expression = "java(mapSimpleUserDtoToUUID(trainingRequestDto.getTrainingManager()))")
    @Mapping(target = "mentorId", expression = "java(mapSimpleUserDtoToUUID(trainingRequestDto.getMentor()))")
    @Mapping(target = "templateId", expression = "java(mapTemplateDtoToTemplateId(trainingRequestDto.getTemplate()))")
    public abstract TrainingRequest toEntity(TrainingRequestDto trainingRequestDto);

    @Mapping(target = "studentId", source = "studentId")
    @Mapping(target = "authorId", source = "authorId")
    @Mapping(target = "trainingManagerId", source = "trainingManagerId")
    @Mapping(target = "mentorId", source = "mentorId")
    @Mapping(target = "templateId", source = "templateId")
    public abstract TrainingRequest toEntity(UpdateTrainingRequestDto updateTrainingRequestDto);

    @Mapping(target = "student", source = "studentId")
    @Mapping(target = "author", source = "authorId")
    @Mapping(target = "trainingManager", source = "trainingManagerId")
    @Mapping(target = "mentor", source = "mentorId")
    @Mapping(target = "template", source = "templateId")
    public abstract TrainingRequestDto toDto(UpdateTrainingRequestDto updateTrainingRequestDto);

    protected SimpleUserDto mapUUIDToSimpleUserDto(UUID uuid) {
        if (uuid == null) {
            return null;
        }
        return userService.getSimpleUserDtoById(uuid);
    }

    protected UUID mapSimpleUserDtoToUUID(SimpleUserDto simpleUserDto) {
        if (simpleUserDto == null) {
            return null;
        }
        return simpleUserDto.getId();
    }

    protected TemplateDto mapTemplateIdToTemplateDto(Long templateId) {
        if (templateId == null) {
            return null;
        }
        return templateService.findById(templateId);
    }

    protected Long mapTemplateDtoToTemplateId(TemplateDto templateDto) {
        if (templateDto == null) {
            return null;
        }
        return templateDto.getId();
    }
}