package com.julian.task_manager.application.service;

import com.julian.task_manager.application.dto.TaskResponse;
import com.julian.task_manager.domain.exception.TaskNotFoundException;
import com.julian.task_manager.domain.model.Task;
import com.julian.task_manager.domain.port.in.CompleteTaskUseCase;
import com.julian.task_manager.domain.port.out.TaskRepository;

public class CompleteTaskService implements CompleteTaskUseCase {

    private final TaskRepository taskRepository;

    public CompleteTaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskResponse complete(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        task.complete();

        Task completedTask = taskRepository.save(task);

        return new TaskResponse(
                completedTask.getId(),
                completedTask.getTitle(),
                completedTask.getDescription(),
                completedTask.getStatus(),
                completedTask.getCreatedAt(),
                completedTask.getUpdatedAt()
        );
    }

}
