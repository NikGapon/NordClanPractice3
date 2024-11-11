package com.nordclan.employees.exception;

import com.nordclan.employees.entity.Group;

public class GroupNotFoundException extends NotFoundException {
    public GroupNotFoundException(Long id){
        super(id);
    }

    public GroupNotFoundException(String attributeName, String attributeValue){
        super(attributeName, attributeValue);
    }

    @Override
    public String getEntityClassName() {
        return Group.class.getSimpleName();
    }
}