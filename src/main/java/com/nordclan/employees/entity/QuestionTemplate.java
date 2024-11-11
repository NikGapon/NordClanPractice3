package com.nordclan.employees.entity;

import com.nordclan.employees.api.DefaultEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Builder(toBuilder = true)
@Table(name = "question_template")
@AllArgsConstructor
public class QuestionTemplate extends DefaultEntity {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    @MapsId("questionId")
    Question question;

    @EmbeddedId
    QuestionTemplateId id;
}