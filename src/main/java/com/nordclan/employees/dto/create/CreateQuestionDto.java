package com.nordclan.employees.dto.create;

import com.nordclan.employees.dto.entity.GroupDto;
import com.nordclan.employees.entity.QuestionLevel;
import com.nordclan.employees.entity.TypeState;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder(toBuilder = true)
@Schema(description = "DTO создания вопроса")
public class CreateQuestionDto {

    @ApiModelProperty(required = true, value = "Вопрос")
    @NotNull(message = "Необходимо указать вопрос")
    private String question;

    @ApiModelProperty(value = "Ответ")
    private String answer;

    @ApiModelProperty(value = "Ссылка с полезной информацией")
    private String link;

    @ApiModelProperty(required = true, value = "Группа")
    @NotNull(message = "Необходимо указать группу")
    private GroupDto group;

    @NotNull(message = "Необходимо указать уровни вопросов")
    @ApiModelProperty(required = true, value = "Уровень вопросов")
    private Set<QuestionLevel> questionLevels;

    @NotNull(message = "Необходимо указать тип вопроса")
    @ApiModelProperty(required = true, value = "Тип вопроса")
    private TypeState type;

}