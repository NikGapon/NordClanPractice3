package com.nordclan.employees.dto.entity;

import com.nordclan.employees.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class SimpleUserDto {

    private UUID id;
    private String firstName;
    private String lastName;
    private String surName;
    @Builder.Default
    private Set<Role> roles = new HashSet<>();
    private String photo;
}