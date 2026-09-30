package com.julian.task_manager.domain.port.in;

import com.julian.task_manager.application.dto.TaskResponse;
import com.julian.task_manager.application.dto.UpdateTaskRequest;

public interface UpdateTaskUseCase {
    TaskResponse update(Long id, UpdateTaskRequest request);
}
