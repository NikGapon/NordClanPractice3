package com.nordclan.employees.repository;

import com.nordclan.employees.api.DefaultRepository;
import com.nordclan.employees.entity.Group;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends DefaultRepository<Group, Long> {
}