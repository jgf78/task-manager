package com.julian.task_manager.application.dto;

public record CreateTaskRequest(
        String title,
        String description
) {
}
