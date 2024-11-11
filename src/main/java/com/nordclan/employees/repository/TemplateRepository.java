package com.nordclan.employees.repository;

import com.nordclan.employees.api.DefaultRepository;
import com.nordclan.employees.entity.Template;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends DefaultRepository<Template, Long> {
}