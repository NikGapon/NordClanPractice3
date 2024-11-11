package com.nordclan.employees.entity;

import com.nordclan.employees.api.DefaultEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "users")
@Accessors(chain = true)
@AllArgsConstructor
public class User extends DefaultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    private String surname;
    private String loginShell;
    @Column(columnDefinition="boolean default false")
    private Boolean isActive;
    private String mail;
    private String telegram;
    private LocalDate birthday;
    private LocalDate firstDay;
    private String post;
    private LocalDate lastDay;
    private String city;
    private LocalDate creationDate;
    private LocalDate updateDate;
    @Column(columnDefinition="boolean default false")
    private Boolean isDeleted;
    @Column(columnDefinition="boolean default false")
    private Boolean isRoot;
    private String photo;
    @Version
    private Long version;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )

    private Set<Role> roles = new HashSet<>();
}