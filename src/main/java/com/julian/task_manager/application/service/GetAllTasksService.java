package com.julian.task_manager.application.service;

import java.util.List;

import com.julian.task_manager.application.dto.TaskResponse;
import com.julian.task_manager.domain.model.Task;
import com.julian.task_manager.domain.port.in.GetAllTasksUseCase;
import com.julian.task_manager.domain.port.out.TaskRepository;

public class GetAllTasksService implements GetAllTasksUseCase {

    private final TaskRepository taskRepository;

    public GetAllTasksService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<TaskResponse> getAll() {

        return taskRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private TaskResponse toResponse(Task task) {

        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }
}