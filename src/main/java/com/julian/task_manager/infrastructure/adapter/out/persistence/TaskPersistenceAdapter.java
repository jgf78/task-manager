package com.julian.task_manager.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import com.julian.task_manager.domain.model.Task;
import com.julian.task_manager.domain.port.out.TaskRepository;

public class TaskPersistenceAdapter implements TaskRepository {

    private final TaskJpaRepository taskJpaRepository;

    public TaskPersistenceAdapter(TaskJpaRepository taskJpaRepository) {
        this.taskJpaRepository = taskJpaRepository;
    }

    @Override
    public Task save(Task task) {

        TaskEntity entity = toEntity(task);

        TaskEntity savedEntity = taskJpaRepository.save(entity);

        return toDomain(savedEntity);
    }

    @Override
    public Optional<Task> findById(Long id) {

        return taskJpaRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public List<Task> findAll() {

        return taskJpaRepository.findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {

        taskJpaRepository.deleteById(id);
    }

    private TaskEntity toEntity(Task task) {

        return new TaskEntity(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }

    private Task toDomain(TaskEntity entity) {

        return new Task(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}