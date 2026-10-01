package com.julian.task_manager.application.dto;

import java.time.Instant;

import com.julian.task_manager.domain.model.TaskStatus;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Task information")
public record TaskResponse(

        @Schema(
                description = "Unique task identifier",
                example = "1"
        )
        Long id,

        @Schema(
                description = "Task title",
                example = "Learn Hexagonal Architecture"
        )
        String title,

        @Schema(
                description = "Task description",
                example = "Study ports, adapters and domain separation"
        )
        String description,

        @Schema(
                description = "Current task status",
                example = "PENDING"
        )
        TaskStatus status,

        @Schema(
                description = "Task creation timestamp",
                example = "2026-10-01T08:30:00Z"
        )
        Instant createdAt,

        @Schema(
                description = "Last task update timestamp",
                example = "2026-10-01T09:15:00Z"
        )
        Instant updatedAt
) {
}