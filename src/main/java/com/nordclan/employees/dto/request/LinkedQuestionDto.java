package com.nordclan.employees.dto.request;

import com.nordclan.employees.dto.response.QuestionForLinkDto;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class LinkedQuestionDto {

    @ApiModelProperty(value = "Список вопросов для связывания", required = true)
    @NotEmpty(message = "Необходимо указать список вопросов")
    List<QuestionForLinkDto> questions;

}