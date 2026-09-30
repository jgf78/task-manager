package com.julian.task_manager.domain.model;

import java.time.Instant;

public class Task {

    private Long id;

    private String title;

    private String description;

    private TaskStatus status;

    private Instant createdAt;

    private Instant updatedAt;

    public Task(String title, String description) {

        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Task title cannot be empty");
        }

        Instant now = Instant.now();

        this.title = title;
        this.description = description;
        this.status = TaskStatus.PENDING;
        this.createdAt = now;
        this.updatedAt = now;
    }

    public Task(
            Long id,
            String title,
            String description,
            TaskStatus status,
            Instant createdAt,
            Instant updatedAt) {

        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Task title cannot be empty");
        }

        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void complete() {

        if (this.status == TaskStatus.COMPLETED) {
            throw new IllegalStateException("Task is already completed");
        }

        this.status = TaskStatus.COMPLETED;
        this.updatedAt = Instant.now();
    }

    public void update(String title, String description) {

        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Task title cannot be empty");
        }

        this.title = title;
        this.description = description;
        this.updatedAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}