package com.nordclan.employees.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Data
@AllArgsConstructor
public class UserDetailsDto {

    private String username;

    private List<SimpleGrantedAuthority> authorities;

}
