package com.julian.task_manager.infrastructure.adapter.out.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskMongoRepository
        extends MongoRepository<TaskMongoDocument, Long> {
}