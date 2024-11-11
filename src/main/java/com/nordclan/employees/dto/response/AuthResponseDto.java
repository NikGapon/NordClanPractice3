package com.nordclan.employees.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AuthResponseDto {

    private String access_token;

    private String token_type;

    private String refresh_token;

    private long expires_in;

    private long refresh_expires_in;

    private String scope;

    private String jti;

}