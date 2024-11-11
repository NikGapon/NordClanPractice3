package com.nordclan.employees.mapper;

import com.nordclan.employees.api.DefaultMapper;
import com.nordclan.employees.dto.create.CreateGroupDto;
import com.nordclan.employees.dto.entity.GroupDto;
import com.nordclan.employees.entity.Group;
import org.mapstruct.Mapper;

@Mapper(config = DefaultMapper.class)
public interface GroupMapper extends DefaultMapper<Group, GroupDto> {
    Group toNewEntity(CreateGroupDto groupDto);
}