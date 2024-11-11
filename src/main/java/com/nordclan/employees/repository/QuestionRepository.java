package com.nordclan.employees.repository;

import com.nordclan.employees.api.DefaultRepository;
import com.nordclan.employees.entity.Question;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends DefaultRepository<Question, Long> {
}