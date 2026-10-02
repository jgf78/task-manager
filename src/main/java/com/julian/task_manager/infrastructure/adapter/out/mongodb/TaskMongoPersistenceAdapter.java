package com.julian.task_manager.infrastructure.adapter.out.mongodb;

import java.util.Optional;

import com.julian.task_manager.domain.model.Task;
import com.julian.task_manager.domain.model.TaskPage;
import com.julian.task_manager.domain.model.TaskPageRequest;
import com.julian.task_manager.domain.port.out.TaskRepository;

public class TaskMongoPersistenceAdapter implements TaskRepository {

    private final TaskMongoRepository taskMongoRepository;
    private final TaskMongoIdGenerator idGenerator;

    public TaskMongoPersistenceAdapter(
            TaskMongoRepository taskMongoRepository, TaskMongoIdGenerator idGenerator) {

        this.taskMongoRepository = taskMongoRepository;
        this.idGenerator = idGenerator;
    }

    @Override
    public Task save(Task task) {

        if (task.getId() == null) {
            task = new Task(
                    idGenerator.nextId(),
                    task.getTitle(),
                    task.getDescription(),
                    task.getStatus(),
                    task.getCreatedAt(),
                    task.getUpdatedAt()
            );
        }

        TaskMongoDocument document = toDocument(task);

        TaskMongoDocument savedDocument =
                taskMongoRepository.save(document);

        return toDomain(savedDocument);
    }

    @Override
    public Optional<Task> findById(Long id) {

        return taskMongoRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public TaskPage findAll(TaskPageRequest pageRequest) {
        throw new UnsupportedOperationException(
                "MongoDB pagination not implemented yet"
        );
    }

    @Override
    public void deleteById(Long id) {

        taskMongoRepository.deleteById(id);
    }

    private TaskMongoDocument toDocument(Task task) {

        return new TaskMongoDocument(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }

    private Task toDomain(TaskMongoDocument document) {

        return new Task(
                document.getId(),
                document.getTitle(),
                document.getDescription(),
                document.getStatus(),
                document.getCreatedAt(),
                document.getUpdatedAt()
        );
    }
}