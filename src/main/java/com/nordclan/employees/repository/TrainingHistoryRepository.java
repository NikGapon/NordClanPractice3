package com.nordclan.employees.repository;

import com.nordclan.employees.api.DefaultRepository;
import com.nordclan.employees.entity.TrainingHistory;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingHistoryRepository extends DefaultRepository<TrainingHistory, Long> {
}