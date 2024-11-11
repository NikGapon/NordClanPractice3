package com.nordclan.employees.exception;

import com.nordclan.employees.entity.Template;

public class TemplateNotFoundException extends NotFoundException {
    public TemplateNotFoundException(Long id){
        super(id);
    }

    public TemplateNotFoundException(String attributeName, String attributeValue){
        super(attributeName, attributeValue);
    }

    @Override
    public String getEntityClassName() {
        return Template.class.getSimpleName();
    }
}