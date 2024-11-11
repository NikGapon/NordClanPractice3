package com.nordclan.employees.service;

import com.nordclan.employees.dto.entity.UserDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
@Transactional
public class SecurityService {

    public UserDto getAuthorizedUser() {
        return new UserDto();
    }
}
