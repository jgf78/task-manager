package com.julian.task_manager.application.dto;

import java.time.Instant;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "API error response")
public record ErrorResponse(

        @Schema(
                description = "Timestamp when the error occurred",
                example = "2026-10-01T09:15:00Z"
        )
        Instant timestamp,

        @Schema(
                description = "HTTP status code",
                example = "400"
        )
        int status,

        @Schema(
                description = "List of error messages",
                example = "[\"Title is required\"]"
        )
        List<String> errors,

        @Schema(
                description = "Request path that caused the error",
                example = "/api/tasks"
        )
        String path
) {
}