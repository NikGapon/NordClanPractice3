package com.nordclan.employees.repository;

import com.nordclan.employees.api.DefaultRepository;
import com.nordclan.employees.entity.TrainingRequest;
import com.nordclan.employees.entity.TrainingRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingRequestRepository extends DefaultRepository<TrainingRequest, Long> {

    List<TrainingRequest> findAllByTrainingStatusIn(List<TrainingRequestStatus> statusList);

}
