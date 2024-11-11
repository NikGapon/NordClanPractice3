package com.nordclan.employees.handler;

import com.nordclan.employees.entity.ErrorResponse;
import com.nordclan.employees.exception.InvalidJwtAuthenticationException;
import com.nordclan.employees.exception.NotFoundException;
import com.nordclan.employees.exception.QuestionNotLinkedWithTemplateException;
import com.nordclan.employees.exception.TemplateAlreadyExistsException;
import com.nordclan.employees.exception.UserIsDeletedException;
import com.nordclan.employees.exception.UserNotActiveException;
import com.nordclan.employees.exception.UserSearchByLastNameOrRoleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionsHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Некорректный запрос: "
                + exception.getFieldErrors()
                    .stream()
                    .sorted(Comparator.comparing(FieldError::getField))
                    .map(e -> e.getDefaultMessage() + " (" + e.getField() + ")")
                    .collect(Collectors.joining("; "))));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Некорректный запрос: " + exception.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException() {
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(new ErrorResponse(HttpStatus.FORBIDDEN.value(), "Отказано в доступе:("));
    }

    @ExceptionHandler(QuestionNotLinkedWithTemplateException.class)
    public ResponseEntity<ErrorResponse> handleQuestionNotLinkedWithTemplateException(QuestionNotLinkedWithTemplateException exception) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage()));
    }

    @ExceptionHandler(UserSearchByLastNameOrRoleException.class)
    public ResponseEntity<ErrorResponse> handleUserSearchByLastNameOrRoleException(UserSearchByLastNameOrRoleException exception) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage()));
    }

    @ExceptionHandler(UserIsDeletedException.class)
    public ResponseEntity<ErrorResponse> handleUserIsDeletedException(UserIsDeletedException exception) {
        return ResponseEntity
            .status(HttpStatus.GONE)
            .body(new ErrorResponse(HttpStatus.GONE.value(), exception.getMessage()));
    }

    @ExceptionHandler(UserNotActiveException.class)
    public ResponseEntity<ErrorResponse> handleUserNotActiveException(UserNotActiveException exception) {
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(new ErrorResponse(HttpStatus.FORBIDDEN.value(), exception.getMessage()));
    }

    @ExceptionHandler(InvalidJwtAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleInvalidJwtAuthenticationException(InvalidJwtAuthenticationException exception) {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(new ErrorResponse(HttpStatus.FORBIDDEN.value(), exception.getMessage()));
    }

    @ExceptionHandler(TemplateAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleTemplateAlreadyExistsException(TemplateAlreadyExistsException exception) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(HttpStatus.FORBIDDEN.value(), exception.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException exception) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage()));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException exception) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage()));
    }
}