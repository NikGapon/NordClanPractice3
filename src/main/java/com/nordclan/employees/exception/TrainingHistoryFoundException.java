package com.nordclan.employees.exception;

import com.nordclan.employees.entity.TrainingHistory;

public class TrainingHistoryFoundException extends NotFoundException {
    public TrainingHistoryFoundException(Long id){
        super(id);
    }

    @Override
    public String getEntityClassName() {
        return TrainingHistory.class.getSimpleName();
    }
}