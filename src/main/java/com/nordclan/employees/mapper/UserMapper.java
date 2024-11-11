package com.nordclan.employees.mapper;

import com.nordclan.employees.api.DefaultMapper;
import com.nordclan.employees.dto.entity.RoleDto;
import com.nordclan.employees.dto.entity.UserDto;
import com.nordclan.employees.entity.Role;
import com.nordclan.employees.entity.RoleNames;
import com.nordclan.employees.entity.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = DefaultMapper.class)
public interface UserMapper extends DefaultMapper<User, UserDto> {
    @AfterMapping
    default void getRussianNameOfRole(Role role, @MappingTarget RoleDto roleDto) {
        roleDto.setRoleRu(RoleNames.getRussianNameByRole(role.getRole()));
    }
}