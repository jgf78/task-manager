package com.julian.task_manager.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTaskRequest(

        @Schema(
                description = "Task title",
                example = "Learn Hexagonal Architecture",
                requiredMode = Schema.RequiredMode.REQUIRED,
                maxLength = 100
        )
        @NotBlank(message = "Title is required")
        @Size(
                max = 100,
                message = "Title cannot exceed 100 characters"
        )
        String title,

        @Schema(
                description = "Task description",
                example = "Study ports, adapters and domain separation",
                maxLength = 255
        )
        @Size(
                max = 255,
                message = "Description cannot exceed 255 characters"
        )
        String description
) {
}