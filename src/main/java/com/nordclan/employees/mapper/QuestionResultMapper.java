package com.nordclan.employees.mapper;

import com.nordclan.employees.api.DefaultMapper;
import com.nordclan.employees.dto.entity.QuestionResultDto;
import com.nordclan.employees.dto.response.QuestionResultDtoFull;
import com.nordclan.employees.entity.QuestionResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = DefaultMapper.class)
public interface QuestionResultMapper extends DefaultMapper<QuestionResult, QuestionResultDto> {
    @Mapping(source = "id.questionId", target = "questionId")
    @Mapping(source = "id.trainingId", target = "trainingId")
    QuestionResultDtoFull toDtoFull(QuestionResult questionResult);
}