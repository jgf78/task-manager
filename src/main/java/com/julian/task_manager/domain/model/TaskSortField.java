package com.julian.task_manager.domain.model;

public enum TaskSortField {

    ID("id"),
    TITLE("title"),
    STATUS("status"),
    CREATED_AT("createdAt"),
    UPDATED_AT("updatedAt");

    private final String property;

    TaskSortField(String property) {
        this.property = property;
    }

    public String getProperty() {
        return property;
    }
}