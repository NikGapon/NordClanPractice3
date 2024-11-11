package com.nordclan.employees.exception;

public class TrainingIncorrectStatusException extends IllegalStateException {

    public static final String message = "Training c id - %s находится в некорректном статусе %s.";

    public TrainingIncorrectStatusException(String message) {
        super(message);
    }

}