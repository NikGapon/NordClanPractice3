package com.nordclan.employees.service;

import com.nordclan.employees.dto.entity.RoleDto;
import com.nordclan.employees.dto.entity.UserDto;
import com.nordclan.employees.dto.request.SetRolesRequestDto;
import com.nordclan.employees.entity.RoleNames;
import com.nordclan.employees.entity.User;
import com.nordclan.employees.exception.UserNotFoundException;
import com.nordclan.employees.mapper.RoleMapper;
import com.nordclan.employees.mapper.UserMapper;
import com.nordclan.employees.repository.RoleRepository;
import com.nordclan.employees.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
@Transactional
public class SuperAdminService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    UserMapper userMapper;

    public UserDto assignRoleToUser(UUID userId, SetRolesRequestDto setRolesRequestDto) throws UserNotFoundException {
        final User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));

        return userMapper.toDto(
            user.setRoles(
                setRolesRequestDto.getRoles()
                    .stream()
                    .map(RoleNames::toString)
                    .map(role -> roleRepository.findByRole(role)
                        .orElseThrow(() -> new NoSuchElementException("Не найдена роль " + role)))
                    .collect(Collectors.toSet())));
    }

    public List<RoleDto> getAvailableRoles() {
        return roleRepository.findAll().stream().map(roleMapper::toDto).toList();
    }
}