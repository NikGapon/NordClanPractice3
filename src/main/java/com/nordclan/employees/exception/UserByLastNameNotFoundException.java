package com.nordclan.employees.exception;

public class UserByLastNameNotFoundException extends RuntimeException {

    public static final String message = "User c name - %s не найден.";

    public UserByLastNameNotFoundException(String message) {
        super(message);
    }

}
