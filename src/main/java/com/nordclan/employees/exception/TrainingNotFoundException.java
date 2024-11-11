package com.nordclan.employees.exception;

import com.nordclan.employees.entity.Training;

public class TrainingNotFoundException extends NotFoundException {
    public TrainingNotFoundException(Long id){
        super(id);
    }

    public TrainingNotFoundException(String attributeName, String attributeValue){
        super(attributeName, attributeValue);
    }

    @Override
    public String getEntityClassName() {
        return Training.class.getSimpleName();
    }
}