package com.julian.task_manager.application.service;

import java.time.Instant;

import com.julian.task_manager.application.dto.CreateTaskRequest;
import com.julian.task_manager.application.dto.TaskResponse;
import com.julian.task_manager.domain.model.Task;
import com.julian.task_manager.domain.model.TaskStatus;
import com.julian.task_manager.domain.port.in.CreateTaskUseCase;
import com.julian.task_manager.domain.port.out.TaskRepository;

public class CreateTaskService implements CreateTaskUseCase {

    private final TaskRepository taskRepository;

    public CreateTaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskResponse create(CreateTaskRequest request) {

        Task task = new Task(
                null,
                request.title(),
                request.description(),
                TaskStatus.PENDING,
                Instant.now(),
                Instant.now()
        );

        Task savedTask = taskRepository.save(task);

        return new TaskResponse(
                savedTask.getId(),
                savedTask.getTitle(),
                savedTask.getDescription(),
                savedTask.getStatus(),
                savedTask.getCreatedAt(),
                savedTask.getUpdatedAt()
        );
    }
}
