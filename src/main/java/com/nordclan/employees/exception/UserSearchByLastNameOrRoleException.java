package com.nordclan.employees.exception;

public class UserSearchByLastNameOrRoleException extends RuntimeException {
    public static final String message = "User с lastName - %s и role - %s не найден.";

    public UserSearchByLastNameOrRoleException(String message) {
        super(message);
    }
}
