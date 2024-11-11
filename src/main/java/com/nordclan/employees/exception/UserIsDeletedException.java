package com.nordclan.employees.exception;

public class UserIsDeletedException extends RuntimeException {

    public static final String message = "Пользователь c id - %s удален.";

    public UserIsDeletedException(String message) {
        super(message);
    }

}