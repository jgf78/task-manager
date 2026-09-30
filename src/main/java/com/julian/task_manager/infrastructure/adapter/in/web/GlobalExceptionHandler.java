package com.julian.task_manager.infrastructure.adapter.in.web;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.julian.task_manager.application.dto.ErrorResponse;
import com.julian.task_manager.domain.exception.TaskNotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleTaskNotFound(
            TaskNotFoundException ex,
            HttpServletRequest request) {

        return new ErrorResponse(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                List.of(ex.getMessage()),
                request.getRequestURI()
        );
    }
    
    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleIllegalState(
            IllegalStateException ex,
            HttpServletRequest request) {

        return new ErrorResponse(
                Instant.now(),
                HttpStatus.CONFLICT.value(),
                List.of(ex.getMessage()),
                request.getRequestURI()
        );
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(errorField -> errorField.getDefaultMessage())
                .orElse("Validation error");

        return new ErrorResponse(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                List.of(ex.getMessage()),
                request.getRequestURI()
        );
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request) {

        List<String> errors = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .toList();

        return new ErrorResponse(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                errors,
                request.getRequestURI()
        );
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        return new ErrorResponse(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                List.of(ex.getMessage()),
                request.getRequestURI()
        );
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnexpectedException(
            Exception ex,
            HttpServletRequest request) {

        return new ErrorResponse(
                Instant.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                List.of("Internal server error"),
                request.getRequestURI()
        );
    }
}
