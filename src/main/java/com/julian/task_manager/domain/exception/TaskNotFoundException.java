package com.julian.task_manager.domain.exception;


public class TaskNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TaskNotFoundException(Long id) {
        super("Task not found with id: " + id);
    }
}
