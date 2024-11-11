package com.nordclan.employees.repository;

import com.nordclan.employees.api.DefaultRepository;
import com.nordclan.employees.entity.QuestionResult;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessmentRepository extends DefaultRepository<QuestionResult, Long> {

    @Query("SELECT q FROM QuestionResult q WHERE q.id.trainingId = :trainingId")
    List<QuestionResult> findAllByTraining(@Param("trainingId") Long trainingId);

}