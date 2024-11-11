package com.nordclan.employees.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class QuestionTrainingId implements Serializable {

    @NotNull
    @Column(name = "training_id")
    private Long trainingId;

    @NotNull
    @Column(name = "question_id")
    private Long questionId;

}