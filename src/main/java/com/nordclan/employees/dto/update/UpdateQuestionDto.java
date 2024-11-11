package com.nordclan.employees.dto.update;

import com.nordclan.employees.dto.entity.GroupDto;
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
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO обновления вопроса")
public class UpdateQuestionDto {

    @ApiModelProperty(value = "Уникальный идентификатор вопроса")
    private Long id;

    @ApiModelProperty(value = "Название вопроса")
    private String question;

    @ApiModelProperty(value = "Автор вопроса")
    private String author;

    @ApiModelProperty(value = "Ответ")
    private String answer;

    @ApiModelProperty(value = "Ссылка с полезной информацией")
    private String link;

    @ApiModelProperty(required = true, value = "Уникальный идентификатор группы")
    private GroupDto group;

    @ApiModelProperty(required = true, value = "Уровень вопросов")
    private Set<QuestionLevel> questionLevels;

    @ApiModelProperty(value = "Тип вопроса")
    private TypeState type;

}