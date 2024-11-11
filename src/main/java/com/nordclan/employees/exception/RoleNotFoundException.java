package com.nordclan.employees.exception;

import com.nordclan.employees.entity.Role;

public class RoleNotFoundException extends NotFoundException {
    public RoleNotFoundException(Long id){
        super(id);
    }

    public RoleNotFoundException(String attributeName, String attributeValue){
        super(attributeName, attributeValue);
    }

    @Override
    public String getEntityClassName() {
        return Role.class.getSimpleName();
    }
}
