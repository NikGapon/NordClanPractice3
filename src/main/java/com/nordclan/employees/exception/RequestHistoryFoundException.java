package com.nordclan.employees.exception;

import com.nordclan.employees.entity.RequestHistory;

public class RequestHistoryFoundException extends NotFoundException {
    public RequestHistoryFoundException(Long id){
        super(id);
    }

    @Override
    public String getEntityClassName() {
        return RequestHistory.class.getSimpleName();
    }
}