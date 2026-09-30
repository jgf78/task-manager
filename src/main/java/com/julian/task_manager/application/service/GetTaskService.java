package com.julian.task_manager.application.service;

import com.julian.task_manager.application.dto.TaskResponse;
import com.julian.task_manager.domain.exception.TaskNotFoundException;
import com.julian.task_manager.domain.model.Task;
import com.julian.task_manager.domain.port.in.GetTaskUseCase;
import com.julian.task_manager.domain.port.out.TaskRepository;

public class GetTaskService implements GetTaskUseCase {

    private final TaskRepository taskRepository;

    public GetTaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskResponse getById(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

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
