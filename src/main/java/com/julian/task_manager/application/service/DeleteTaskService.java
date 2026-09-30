package com.julian.task_manager.application.service;

import com.julian.task_manager.domain.exception.TaskNotFoundException;
import com.julian.task_manager.domain.model.Task;
import com.julian.task_manager.domain.port.in.DeleteTaskUseCase;
import com.julian.task_manager.domain.port.out.TaskRepository;

public class DeleteTaskService implements DeleteTaskUseCase {

    private final TaskRepository taskRepository;

    public DeleteTaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void deleteById(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        taskRepository.deleteById(task.getId());
    }

}
