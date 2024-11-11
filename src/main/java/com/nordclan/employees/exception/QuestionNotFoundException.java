package com.nordclan.employees.exception;

import com.nordclan.employees.entity.Question;

public class QuestionNotFoundException extends NotFoundException {
    public QuestionNotFoundException(Long id){
        super(id);
    }

    public QuestionNotFoundException(String attributeName, String attributeValue){
        super(attributeName, attributeValue);
    }

    @Override
    public String getEntityClassName() {
        return Question.class.getSimpleName();
    }
}