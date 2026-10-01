package com.julian.task_manager.domain.model;

public record TaskPageRequest(
        int page,
        int size,
        TaskSort sort,
        TaskStatus status
) {
}
