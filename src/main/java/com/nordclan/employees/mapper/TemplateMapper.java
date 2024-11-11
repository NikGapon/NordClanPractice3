package com.nordclan.employees.mapper;

import com.nordclan.employees.api.DefaultMapper;
import com.nordclan.employees.dto.create.CreateTemplateDto;
import com.nordclan.employees.dto.entity.QuestionDto;
import com.nordclan.employees.dto.entity.TemplateDto;
import com.nordclan.employees.entity.Question;
import com.nordclan.employees.entity.QuestionTemplate;
import com.nordclan.employees.entity.QuestionTemplateId;
import com.nordclan.employees.entity.Template;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = QuestionMapper.class)
public interface TemplateMapper extends DefaultMapper<Template, TemplateDto> {
    @Mapping(source = "questions", target = "questions", ignore = true)
    Template toNewEntity(CreateTemplateDto createTemplateDto);

    default Question mapQuestionTemplateToQuestionDto(String question) {
        return Question.builder().question(question).build();
    }

    default QuestionTemplateId map(Long value) {
        return QuestionTemplateId.builder().templateId(value).build();
    }

    default QuestionDto mapQuestionTemplateToQuestionDto(QuestionTemplate questionTemplate) {
        return Mappers.getMapper(QuestionMapper.class).toDto(questionTemplate.getQuestion());
    }
}