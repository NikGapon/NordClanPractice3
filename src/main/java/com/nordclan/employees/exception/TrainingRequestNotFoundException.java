package com.nordclan.employees.exception;

import com.nordclan.employees.entity.TrainingRequest;

public class TrainingRequestNotFoundException extends NotFoundException {
    public TrainingRequestNotFoundException(Long id){
        super(id);
    }

    public TrainingRequestNotFoundException(String attributeName, String attributeValue){
        super(attributeName, attributeValue);
    }

    @Override
    public String getEntityClassName() {
        return TrainingRequest.class.getSimpleName();
    }
}
