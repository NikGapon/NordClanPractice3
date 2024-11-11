package com.nordclan.employees.entity;

import com.nordclan.employees.api.DefaultEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder(toBuilder = true)
@Entity
@AllArgsConstructor
@Table(name = "template")
public class Template extends DefaultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "template_generator")
    @SequenceGenerator(name = "template_generator", sequenceName = "template_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(unique=true)
    private String name;

    @Nullable
    private String description;

    @PositiveOrZero
    private Integer threshold;

    @OneToMany(mappedBy = "id.templateId", cascade = CascadeType.DETACH, orphanRemoval = false)
    @ToString.Exclude
    private List<QuestionTemplate> questions = new ArrayList<>();

    @Version
    private Long version;
}