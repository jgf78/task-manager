package com.julian.task_manager.infrastructure.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
        name = "database.type",
        havingValue = "mongodb"
)
public class MongoDatabaseConfiguration {
}