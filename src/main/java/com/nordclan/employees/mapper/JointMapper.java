package com.nordclan.employees.mapper;

import com.nordclan.employees.dto.response.GroupJointDto;
import com.nordclan.employees.dto.response.QuestionJointDto;
import com.nordclan.employees.dto.response.TemplateJointDto;
import com.nordclan.employees.entity.Group;
import com.nordclan.employees.entity.Question;
import com.nordclan.employees.entity.Template;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JointMapper {
    QuestionJointDto toDto(Question question);
    GroupJointDto toDto(Group group);
    TemplateJointDto toDto(Template template);
}
