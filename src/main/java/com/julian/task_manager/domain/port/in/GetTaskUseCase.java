package com.julian.task_manager.domain.port.in;

import com.julian.task_manager.application.dto.TaskResponse;

public interface GetTaskUseCase {

    TaskResponse getById(Long id);
}
