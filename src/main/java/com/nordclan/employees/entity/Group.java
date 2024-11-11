package com.nordclan.employees.entity;

import com.nordclan.employees.api.DefaultEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@Entity
@Table(name = "groups")
@AllArgsConstructor
@NoArgsConstructor
public class Group extends DefaultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "groups_generator")
    @SequenceGenerator(name = "groups_generator", sequenceName = "groups_seq", allocationSize = 1)
    private Long id;

    @NotBlank
    private String name;

    @Version
    private Long version;

}