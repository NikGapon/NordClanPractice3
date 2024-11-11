package com.nordclan.employees.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class RequestHistoryDto {
    private Long id;
    private Long requestId;
    private LocalDateTime createdAt;
    private UUID userId;
    private String action;
    private String field;
    private String prevValue;
    private String valueStr;
    private String type;
}