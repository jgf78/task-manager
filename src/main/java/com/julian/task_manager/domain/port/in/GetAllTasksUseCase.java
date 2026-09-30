package com.julian.task_manager.domain.port.in;

import com.julian.task_manager.application.dto.TaskPageResponse;
import com.julian.task_manager.domain.model.TaskPageRequest;

public interface GetAllTasksUseCase {
    TaskPageResponse getAll(TaskPageRequest pageRequest);
}
