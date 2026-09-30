package com.julian.task_manager.domain.port.in;

import com.julian.task_manager.application.dto.CreateTaskRequest;
import com.julian.task_manager.application.dto.TaskResponse;

public interface CreateTaskUseCase {

    TaskResponse create(CreateTaskRequest request);

}
