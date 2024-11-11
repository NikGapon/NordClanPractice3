package com.nordclan.employees.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResultDto {

    @Range(min = 1, max = 3, message = "Оценка вопроса должна быть равна 1, 2, 3")
    private Integer point;

}