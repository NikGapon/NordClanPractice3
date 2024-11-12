package com.nordclan.employees.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserforRudeTopDto {

    private String FIO;
    private UUID userId;
    private Integer FinalScore;
    private String BetterThan;
}
