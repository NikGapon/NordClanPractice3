package com.nordclan.employees.dto.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Pair;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UserHonestCompareDto {
    private Pair<String, UUID> Fighter1;
    private Pair<String, UUID> Fighter2;
    private Integer HowManyTemplate;
    private Integer Fighter1Score;
    private Integer Fighter2Score;
    private String Winner;

}
