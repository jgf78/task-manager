package com.julian.task_manager.infrastructure.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;

import com.julian.task_manager.domain.port.out.TaskRepository;
import com.julian.task_manager.infrastructure.adapter.out.mongodb.TaskMongoIdGenerator;
import com.julian.task_manager.infrastructure.adapter.out.mongodb.TaskMongoPersistenceAdapter;
import com.julian.task_manager.infrastructure.adapter.out.mongodb.TaskMongoRepository;

@Configuration
@ConditionalOnProperty(
        name = "database.type",
        havingValue = "mongodb"
)
public class MongoPersistenceConfiguration {

    @Bean
    TaskRepository taskRepository(
            TaskMongoRepository taskMongoRepository,
            TaskMongoIdGenerator idGenerator,
            MongoOperations mongoOperations) {

        return new TaskMongoPersistenceAdapter(
                taskMongoRepository,
                idGenerator,
                mongoOperations
        );
    }
}