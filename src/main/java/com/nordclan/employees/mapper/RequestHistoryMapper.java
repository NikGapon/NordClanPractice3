package com.nordclan.employees.mapper;

import com.nordclan.employees.api.DefaultMapper;
import com.nordclan.employees.dto.entity.RequestHistoryDto;
import com.nordclan.employees.entity.RequestHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequestHistoryMapper extends DefaultMapper<RequestHistory, RequestHistoryDto> {
}