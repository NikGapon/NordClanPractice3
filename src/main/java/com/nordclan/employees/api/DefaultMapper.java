package com.nordclan.employees.api;

import org.mapstruct.MapperConfig;

@MapperConfig(componentModel = "spring")
public interface DefaultMapper<ENT, DTO> {
    DTO toDto(ENT entity);

    ENT toEntity(DTO dto);
}
