package com.julian.task_manager.infrastructure.adapter.out.mongodb;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.julian.task_manager.domain.model.Task;
import com.julian.task_manager.domain.model.TaskFilter;
import com.julian.task_manager.domain.model.TaskPage;
import com.julian.task_manager.domain.model.TaskPageRequest;
import com.julian.task_manager.domain.port.out.TaskRepository;

public class TaskMongoPersistenceAdapter implements TaskRepository {

    private final TaskMongoRepository taskMongoRepository;
    private final TaskMongoIdGenerator idGenerator;
    private final MongoOperations mongoOperations;

    public TaskMongoPersistenceAdapter(
            TaskMongoRepository taskMongoRepository,
            TaskMongoIdGenerator idGenerator,
            MongoOperations mongoOperations) {

        this.taskMongoRepository = taskMongoRepository;
        this.idGenerator = idGenerator;
        this.mongoOperations = mongoOperations;
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

        Criteria criteria = buildCriteria(
                pageRequest.filter()
        );

        Query query = new Query(criteria);

        Sort.Direction direction = Sort.Direction.valueOf(
                pageRequest.sort().direction().name()
        );

        Sort sort = Sort.by(
                direction,
                pageRequest.sort().field().getProperty()
        );

        query.with(sort);

        long skip = (long) pageRequest.page() * pageRequest.size();

        query.skip(skip);
        query.limit(pageRequest.size());

        List<TaskMongoDocument> documents =
                mongoOperations.find(
                        query,
                        TaskMongoDocument.class
                );

        long totalElements =
                mongoOperations.count(
                        new Query(criteria),
                        TaskMongoDocument.class
                );

        int totalPages = (int) Math.ceil(
                (double) totalElements / pageRequest.size()
        );

        List<Task> tasks = documents.stream()
                .map(this::toDomain)
                .toList();

        return new TaskPage(
                tasks,
                pageRequest.page(),
                pageRequest.size(),
                totalElements,
                totalPages
        );
    }

    @Override
    public void deleteById(Long id) {

        taskMongoRepository.deleteById(id);
    }

    private Criteria buildCriteria(TaskFilter filter) {

        if (filter == null) {
            return new Criteria();
        }

        List<Criteria> criteriaList = new ArrayList<>();

        if (filter.status() != null) {

            criteriaList.add(
                    Criteria.where("status")
                            .is(filter.status())
            );
        }

        if (filter.title() != null
                && !filter.title().isBlank()) {

            criteriaList.add(
                    Criteria.where("title")
                            .regex(
                                    filter.title(),
                                    "i"
                            )
            );
        }

        if (criteriaList.isEmpty()) {
            return new Criteria();
        }

        return new Criteria().andOperator(
                criteriaList.toArray(new Criteria[0])
        );
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
