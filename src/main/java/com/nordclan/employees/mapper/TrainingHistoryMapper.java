package com.nordclan.employees.mapper;

import com.nordclan.employees.api.DefaultMapper;
import com.nordclan.employees.dto.entity.TrainingHistoryDto;
import com.nordclan.employees.entity.TrainingHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrainingHistoryMapper extends DefaultMapper<TrainingHistory, TrainingHistoryDto> {
}