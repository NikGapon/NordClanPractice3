package com.nordclan.employees.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResultDtoFull {

    long questionId;
    long trainingId;
    int point;

}