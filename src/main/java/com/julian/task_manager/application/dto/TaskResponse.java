package com.julian.task_manager.application.dto;

import java.time.Instant;

import com.julian.task_manager.domain.model.TaskStatus;

public record TaskResponse(
        Long id,
        String title,
        String description,
        TaskStatus status,
        Instant createdAt,
        Instant updatedAt
) {
}