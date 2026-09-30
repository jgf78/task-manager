package com.julian.task_manager.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.julian.task_manager.domain.model.Task;

public interface TaskRepository {

    Task save(Task task);

    Optional<Task> findById(Long id);

    List<Task> findAll();

    void deleteById(Long id);

}
