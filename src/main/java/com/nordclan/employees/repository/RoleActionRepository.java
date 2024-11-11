package com.nordclan.employees.repository;

import com.nordclan.employees.api.DefaultRepository;
import com.nordclan.employees.entity.RoleAction;

import java.util.List;

public interface RoleActionRepository extends DefaultRepository<RoleAction, Long> {
    List<RoleAction> findByRoleId(Long roleId);

}