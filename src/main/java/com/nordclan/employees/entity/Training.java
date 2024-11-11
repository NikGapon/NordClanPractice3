package com.nordclan.employees.entity;

import com.nordclan.employees.api.DefaultEntity;
import com.nordclan.employees.handler.TrainingEventHandler;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Builder(toBuilder = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "training")
@EntityListeners(TrainingEventHandler.class)
public class Training extends DefaultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "training_generator")
    @SequenceGenerator(name = "training_generator", sequenceName = "training_seq", allocationSize = 1)
    private Long id;

    @NotNull
    private UUID studentId;

    @NotNull
    private UUID trainingManagerId;

    @NotNull
    private UUID mentorId;

    @NotNull
    private Long templateId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TrainingStatus trainingStatus;

    private LocalDate creationDate;

    private LocalDate updateDate;

    private LocalDate trainingEndDate;

    private LocalDate trainingStartDate;

    private LocalDate trainingPauseDate;

    private Integer examResult;

    @Version
    private Long version;

}