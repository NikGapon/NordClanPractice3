package com.nordclan.employees.dto.response;

import com.nordclan.employees.entity.QuestionLevel;
import com.nordclan.employees.entity.TypeState;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Schema(description = "DTO вопроса")
public class QuestionJointDto {

    @ApiModelProperty(required = true, value = "Уникальный идентификатор вопроса")
    private Long id;

    @ApiModelProperty(required = true, value = "Вопрос")
    private String question;

    @ApiModelProperty(value = "Ответ")
    private String answer;

    @ApiModelProperty(value = "Ссылка с полезной информацией")
    private String link;

    @ApiModelProperty(value = "Автор вопроса")
    private String author;

    @ApiModelProperty(required = true, value = "Уровень вопросов")
    private Set<QuestionLevel> questionLevels;

    @ApiModelProperty(value = "Тип вопроса")
    private TypeState type;

}