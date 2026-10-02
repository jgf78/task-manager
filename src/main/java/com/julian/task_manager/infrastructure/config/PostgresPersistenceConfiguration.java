package com.julian.task_manager.infrastructure.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.julian.task_manager.domain.port.out.TaskRepository;
import com.julian.task_manager.infrastructure.adapter.out.persistence.TaskJpaRepository;
import com.julian.task_manager.infrastructure.adapter.out.persistence.TaskPersistenceAdapter;

@Configuration
@ConditionalOnProperty(
        name = "database.type",
        havingValue = "postgres",
        matchIfMissing = true
)
public class PostgresPersistenceConfiguration {

    @Bean
    TaskRepository taskRepository(
            TaskJpaRepository taskJpaRepository) {

        return new TaskPersistenceAdapter(taskJpaRepository);
    }
}