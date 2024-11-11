package com.nordclan.employees.repository;

import com.nordclan.employees.api.DefaultRepository;
import com.nordclan.employees.entity.Role;
import com.nordclan.employees.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface UserRepository extends DefaultRepository<User, UUID> {


    @Query("SELECT u FROM User u JOIN u.roles r WHERE u.lastName = :lastName AND r IN :roles")
    List<User> findByLastNameAndRoles(@Param("lastName") String lastName, @Param("roles") Set<Role> roles);

    Optional<List<User>> findByLastName(String lastName);


}