package com.nordclan.employees.entity;

import com.nordclan.employees.api.DefaultEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Entity
@Builder(toBuilder = true)
@Table(name = "roles")
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Role extends DefaultEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String role;

    public Role(String role) {
        this.role = role;
    }

    public boolean hasRoleName(String roleName) {
        return this.role.equals(roleName);
    }
}