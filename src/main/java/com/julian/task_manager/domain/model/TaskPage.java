package com.julian.task_manager.domain.model;

import java.util.List;

public record TaskPage(
        List<Task> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) {
}