package com.nordclan.employees.exception;

public class UserNotActiveException extends RuntimeException {

    public static final String message = "Пользователь c id - %s не активен.";

    public UserNotActiveException(String message) {
        super(message);
    }

}