package com.julian.task_manager.infrastructure.adapter.out.mongodb;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.julian.task_manager.domain.model.TaskStatus;

@Document(collection = "tasks")
public class TaskMongoDocument {

    @Id
    private Long id;

    private String title;
    private String description;
    private TaskStatus status;
    private Instant createdAt;
    private Instant updatedAt;

    public TaskMongoDocument() {
    }

    public TaskMongoDocument(
            Long id,
            String title,
            String description,
            TaskStatus status,
            Instant createdAt,
            Instant updatedAt) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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