package com.nordclan.employees.dto.request;

import com.nordclan.employees.entity.TrainingRequestStatus;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ChangeTrainingRequestStatusDto {

    @ApiModelProperty(value = "Идентификатор запроса", required = true)
    @NotNull(message = "Необходимо указать запрос")
    private Long requestId;

    @ApiModelProperty(value = "Идентификатор ментора", required = true)
    @NotNull(message = "Необходимо указать ментора")
    private UUID mentorId;

    @ApiModelProperty(value = "Идентификатор шаблона", required = true)
    @NotNull(message = "Необходимо указать шаблон")
    private Long templateId;

    @ApiModelProperty(value = "Дата окончания обучения", required = true)
    @NotNull(message = "Необходимо указать дату окончания обучения")
    private LocalDate trainingEndDate;

    @ApiModelProperty(value = "Новый статус", required = true)
    @NotNull(message = "Необходимо указать новый статус")
    private TrainingRequestStatus newStatus;

}

