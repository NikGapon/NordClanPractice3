package com.nordclan.employees.mapper;

import com.nordclan.employees.api.DefaultMapper;
import com.nordclan.employees.dto.entity.SimpleUserDto;
import com.nordclan.employees.dto.entity.TrainingDto;
import com.nordclan.employees.dto.update.UpdateTrainingDto;
import com.nordclan.employees.entity.Training;
import com.nordclan.employees.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.UUID;

@Mapper(config = DefaultMapper.class)
public abstract class TrainingMapper implements DefaultMapper<Training, TrainingDto> {

    @Autowired
    @Lazy
    private UserService userService;

    @Mapping(target = "student", expression = "java(mapUUIDToSimpleUserDto(training.getStudentId()))")
    @Mapping(target = "trainingManager", expression = "java(mapUUIDToSimpleUserDto(training.getTrainingManagerId()))")
    @Mapping(target = "mentor", expression = "java(mapUUIDToSimpleUserDto(training.getMentorId()))")
    public abstract TrainingDto toDto(Training training);

    @Mapping(target = "studentId", expression = "java(mapSimpleUserDtoToUUID(trainingDto.getStudent()))")
    @Mapping(target = "trainingManagerId", expression = "java(mapSimpleUserDtoToUUID(trainingDto.getTrainingManager()))")
    @Mapping(target = "mentorId", expression = "java(mapSimpleUserDtoToUUID(trainingDto.getMentor()))")
    public abstract Training toEntity(TrainingDto trainingDto);

    @Mapping(target = "studentId", source = "studentId")
    @Mapping(target = "trainingManagerId", source = "trainingManagerId")
    @Mapping(target = "mentorId", source = "mentorId")
    public abstract Training toEntity(UpdateTrainingDto updateTrainingDto);

    @Mapping(target = "student", source = "studentId")
    @Mapping(target = "trainingManager", source = "trainingManagerId")
    @Mapping(target = "mentor", source = "mentorId")
    public abstract TrainingDto toDto(UpdateTrainingDto updateTrainingDto);

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
}