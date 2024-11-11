package com.nordclan.employees.service;

import com.nordclan.employees.dto.entity.RoleDto;
import com.nordclan.employees.dto.response.RolePermissionsResponseDto;
import com.nordclan.employees.entity.RoleAction;
import com.nordclan.employees.repository.RoleActionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
@Transactional
public class UserRolePermissionService {
    SecurityService securityService;
    RoleActionRepository roleActionRepository;

    public RolePermissionsResponseDto getRolePermissionsByUserLogin() {
        final Set<RoleDto> roles = new HashSet<>(securityService.getAuthorizedUser().getRoles());

        return new RolePermissionsResponseDto(
            roles.stream().map(RoleDto::getRole).toList(),
            roles.stream()
                .flatMap(role -> roleActionRepository
                    .findByRoleId(role.getId())
                    .stream())
                .map(RoleAction::getAction)
                .toList());
    }

}