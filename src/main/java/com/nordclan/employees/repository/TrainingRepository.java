package com.nordclan.employees.repository;

import com.nordclan.employees.api.DefaultRepository;
import com.nordclan.employees.entity.Training;
import com.nordclan.employees.entity.TrainingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingRepository extends DefaultRepository<Training, Long> {

    List<Training> findAllByTrainingStatusIn(List<TrainingStatus> statusList);

}
