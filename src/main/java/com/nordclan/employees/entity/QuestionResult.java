package com.nordclan.employees.entity;

import com.nordclan.employees.api.DefaultEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@Entity
@Table(name = "question_result")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class QuestionResult extends DefaultEntity {

    @EmbeddedId
    private QuestionTrainingId id;

    @Range(min = 1, max = 3, message = "Оценка вопроса должна быть равна 1, 2, 3")
    private Integer point;

}