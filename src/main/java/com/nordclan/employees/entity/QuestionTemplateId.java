package com.nordclan.employees.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionTemplateId implements Serializable {

    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "template_id")
    private Long templateId;

}