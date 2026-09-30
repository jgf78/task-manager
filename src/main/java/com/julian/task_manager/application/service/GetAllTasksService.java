package com.julian.task_manager.application.service;

import java.util.List;

import com.julian.task_manager.application.dto.TaskPageResponse;
import com.julian.task_manager.application.dto.TaskResponse;
import com.julian.task_manager.domain.model.Task;
import com.julian.task_manager.domain.model.TaskPage;
import com.julian.task_manager.domain.model.TaskPageRequest;
import com.julian.task_manager.domain.port.in.GetAllTasksUseCase;
import com.julian.task_manager.domain.port.out.TaskRepository;

public class GetAllTasksService implements GetAllTasksUseCase {

    private final TaskRepository taskRepository;

    public GetAllTasksService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskPageResponse getAll(TaskPageRequest pageRequest) {

        TaskPage taskPage = taskRepository.findAll(pageRequest);

        List<TaskResponse> tasks = taskPage.content()
                .stream()
                .map(this::toResponse)
                .toList();

        return new TaskPageResponse(
                tasks,
                taskPage.page(),
                taskPage.size(),
                taskPage.totalElements(),
                taskPage.totalPages()
        );
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