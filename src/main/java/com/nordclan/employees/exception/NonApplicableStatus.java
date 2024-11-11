package com.nordclan.employees.exception;

public class NonApplicableStatus extends RuntimeException {

    public static final String message = "Данный статус не применим";

    public NonApplicableStatus(String message) {
        super(message);
    }

}
