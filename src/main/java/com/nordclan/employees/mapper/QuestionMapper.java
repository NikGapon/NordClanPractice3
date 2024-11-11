package com.nordclan.employees.mapper;

import com.nordclan.employees.api.DefaultMapper;
import com.nordclan.employees.dto.entity.QuestionDto;
import com.nordclan.employees.entity.Question;
import org.mapstruct.Mapper;

@Mapper(config = DefaultMapper.class)
public interface QuestionMapper extends DefaultMapper<Question, QuestionDto> {
}