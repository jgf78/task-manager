package com.julian.task_manager.infrastructure.adapter.in.web;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.julian.task_manager.application.dto.ErrorResponse;
import com.julian.task_manager.domain.exception.TaskNotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String UNKNOWN_ERROR = "Unknown error";
    private static final String VALIDATION_FAILED = "Validation failed";
    private static final String INTERNAL_SERVER_ERROR = "Internal server error";
    private static final String INVALID_PARAMETER_PREFIX = "Invalid value for parameter '";

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleTaskNotFound(
            TaskNotFoundException ex,
            HttpServletRequest request) {

        return buildErrorResponse(HttpStatus.NOT_FOUND, List.of(safeMessage(ex.getMessage())), request);
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleIllegalState(
            IllegalStateException ex,
            HttpServletRequest request) {

        return buildErrorResponse(HttpStatus.CONFLICT, List.of(safeMessage(ex.getMessage())), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(errorField -> safeMessage(errorField.getDefaultMessage(), VALIDATION_FAILED))
                .toList();

        return buildErrorResponse(HttpStatus.BAD_REQUEST, errors, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request) {

        List<String> errors = ex.getConstraintViolations()
                .stream()
                .map(violation -> safeMessage(violation.getMessage(), VALIDATION_FAILED))
                .toList();

        return buildErrorResponse(HttpStatus.BAD_REQUEST, errors, request);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHandlerMethodValidation(
            HandlerMethodValidationException ex,
            HttpServletRequest request) {

        List<String> errors = ex.getParameterValidationResults()
                .stream()
                .flatMap(result -> result.getResolvableErrors().stream())
                .map(error -> safeMessage(error.getDefaultMessage(), VALIDATION_FAILED))
                .toList();

        return buildErrorResponse(HttpStatus.BAD_REQUEST, errors, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        return buildErrorResponse(HttpStatus.BAD_REQUEST, List.of(safeMessage(ex.getMessage())), request);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnexpectedException(
            Exception ex,
            HttpServletRequest request) {

        log.error("Unexpected exception at {}", request.getRequestURI(), ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, List.of(INTERNAL_SERVER_ERROR), request);
    }
    
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request) {

        String errorMessage;

        if (ex.getRequiredType() != null && ex.getRequiredType().isEnum()) {
            errorMessage = INVALID_PARAMETER_PREFIX + ex.getName()
                    + "'. Allowed values: "
                    + String.join(", ",
                            Arrays.stream(ex.getRequiredType().getEnumConstants())
                                    .map(Object::toString)
                                    .toList());
        } else {
            errorMessage = INVALID_PARAMETER_PREFIX + ex.getName() + "'";
        }

        return buildErrorResponse(HttpStatus.BAD_REQUEST, List.of(errorMessage), request);
    }

    private ErrorResponse buildErrorResponse(HttpStatus status, List<String> errors, HttpServletRequest request) {
        return new ErrorResponse(
                Instant.now(),
                status.value(),
                errors,
                request.getRequestURI()
        );
    }

    private String safeMessage(String message) {
        return safeMessage(message, UNKNOWN_ERROR);
    }

    private String safeMessage(String message, String fallback) {
        return message != null ? message : fallback;
    }
}