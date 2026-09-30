package com.julian.task_manager.domain.port.out;

import java.util.Optional;

import com.julian.task_manager.domain.model.Task;
import com.julian.task_manager.domain.model.TaskPage;
import com.julian.task_manager.domain.model.TaskPageRequest;

public interface TaskRepository {

    Task save(Task task);

    Optional<Task> findById(Long id);

    TaskPage findAll(TaskPageRequest pageRequest);

    void deleteById(Long id);

}
