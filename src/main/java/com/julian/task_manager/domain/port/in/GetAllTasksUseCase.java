package com.julian.task_manager.domain.port.in;

import java.util.List;

import com.julian.task_manager.application.dto.TaskResponse;

public interface GetAllTasksUseCase {
    List<TaskResponse> getAll();
}
