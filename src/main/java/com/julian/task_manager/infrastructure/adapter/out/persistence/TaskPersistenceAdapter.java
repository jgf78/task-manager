package com.julian.task_manager.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.julian.task_manager.domain.model.Task;
import com.julian.task_manager.domain.model.TaskPage;
import com.julian.task_manager.domain.model.TaskPageRequest;
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
    public TaskPage findAll(TaskPageRequest pageRequest) {

        Sort.Direction direction = Sort.Direction.valueOf(
                pageRequest.sort().direction().name()
        );

        Sort sort = Sort.by(
                direction,
                pageRequest.sort().field().getProperty()
        );
        
        Pageable pageable = PageRequest.of(
                pageRequest.page(),
                pageRequest.size(),
                sort
        );

        Page<TaskEntity> page = taskJpaRepository.findAll(pageable);

        List<Task> tasks = page.getContent()
                .stream()
                .map(this::toDomain)
                .toList();

        return new TaskPage(
                tasks,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
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