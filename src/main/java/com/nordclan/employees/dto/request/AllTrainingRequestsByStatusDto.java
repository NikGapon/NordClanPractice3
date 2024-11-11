package com.nordclan.employees.dto.request;

import com.nordclan.employees.entity.TrainingRequestStatus;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllTrainingRequestsByStatusDto {

    @ApiModelProperty(value = "Список статусов", required = true)
    @NotEmpty(message = "Необходимо указать список статусов")
    List<TrainingRequestStatus> statusList;

}
