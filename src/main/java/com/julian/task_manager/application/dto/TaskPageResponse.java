package com.julian.task_manager.application.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Paginated task response")
public record TaskPageResponse(

        @Schema(
                description = "Tasks included in the current page"
        )
        List<TaskResponse> content,

        @Schema(
                description = "Current page number (zero-based)",
                example = "0"
        )
        int page,

        @Schema(
                description = "Number of tasks requested per page",
                example = "10"
        )
        int size,

        @Schema(
                description = "Total number of tasks matching the filters",
                example = "25"
        )
        long totalElements,

        @Schema(
                description = "Total number of pages",
                example = "3"
        )
        int totalPages
) {
}