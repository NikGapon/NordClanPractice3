package com.nordclan.employees.exception;

import com.nordclan.employees.entity.User;

import java.util.UUID;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(UUID id){
        super(id);
    }

    public UserNotFoundException(String attributeName, String attributeValue){
        super(attributeName, attributeValue);
    }

    @Override
    public String getEntityClassName() {
        return User.class.getSimpleName();
    }
}