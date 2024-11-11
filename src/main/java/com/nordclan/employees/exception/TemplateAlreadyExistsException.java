package com.nordclan.employees.exception;

public class TemplateAlreadyExistsException extends RuntimeException {

    public static final String message = "Template c name - %s уже существует.";

    public TemplateAlreadyExistsException(String message) {
        super(message);
    }

}