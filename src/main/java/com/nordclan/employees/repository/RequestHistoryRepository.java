package com.nordclan.employees.repository;

import com.nordclan.employees.api.DefaultRepository;
import com.nordclan.employees.entity.RequestHistory;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestHistoryRepository extends DefaultRepository<RequestHistory, Long> {
}