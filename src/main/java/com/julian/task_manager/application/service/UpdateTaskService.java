package com.julian.task_manager.application.service;

import com.julian.task_manager.application.dto.TaskResponse;
import com.julian.task_manager.application.dto.UpdateTaskRequest;
import com.julian.task_manager.domain.exception.TaskNotFoundException;
import com.julian.task_manager.domain.model.Task;
import com.julian.task_manager.domain.port.in.UpdateTaskUseCase;
import com.julian.task_manager.domain.port.out.TaskRepository;

public class UpdateTaskService implements UpdateTaskUseCase {

    private final TaskRepository taskRepository;

    public UpdateTaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskResponse update(Long id, UpdateTaskRequest request) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        task.update(
                request.title(),
                request.description()
        );

        Task updatedTask = taskRepository.save(task);

        return new TaskResponse(
                updatedTask.getId(),
                updatedTask.getTitle(),
                updatedTask.getDescription(),
                updatedTask.getStatus(),
                updatedTask.getCreatedAt(),
                updatedTask.getUpdatedAt()
        );
    }

}
