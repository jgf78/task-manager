package com.julian.task_manager.domain.model;

public record TaskSort(
        TaskSortField field,
        SortDirection direction
) {
}