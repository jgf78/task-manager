package com.julian.task_manager.infrastructure.adapter.out.mongodb;

import java.util.Objects;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        name = "database.type",
        havingValue = "mongodb"
)
public class TaskMongoIdGenerator {

    private static final String COUNTER_ID = "tasks";
    private static final String ID_FIELD = "_id";
    private static final String SEQUENCE_FIELD = "sequence";

    private final MongoOperations mongoOperations;

    public TaskMongoIdGenerator(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public Long nextId() {

        Query query = Query.query(
                Criteria.where(ID_FIELD).is(COUNTER_ID)
        );

        Update update = new Update()
                .inc(SEQUENCE_FIELD, 1);

        FindAndModifyOptions options = FindAndModifyOptions.options()
                .returnNew(true)
                .upsert(true);

        TaskMongoCounter counter = mongoOperations.findAndModify(
                query,
                update,
                options,
                TaskMongoCounter.class
        );

        return Objects.requireNonNull(
                counter,
                "Counter document was not returned by MongoDB"
        ).getSequence();
    }
}