package com.nordclan.employees.entity;

import com.nordclan.employees.api.DefaultEntity;
import com.nordclan.employees.handler.TrainingRequestEventHandler;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@Accessors(chain = true)
@Table(name = "training_request")
@EntityListeners(TrainingRequestEventHandler.class)
public class TrainingRequest extends DefaultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "training_request_generator")
    @SequenceGenerator(name = "training_request_generator", sequenceName = "training_request_seq", allocationSize = 1)
    private Long id;

    private UUID studentId;

    private UUID authorId;

    private UUID trainingManagerId;

    private UUID mentorId;

    private Long templateId;

    @Enumerated(EnumType.STRING)
    private TrainingRequestStatus trainingStatus;

    private LocalDate creationDate;

    private LocalDate updateDate;

    private LocalDate trainingEndDate;

    private Boolean isDeleted;

    @Version
    private Long version;
}