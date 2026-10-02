package com.julian.task_manager.infrastructure.adapter.out.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "counters")
public class TaskMongoCounter {

    @Id
    private String id;

    private Long sequence;

    public TaskMongoCounter(String id, Long sequence) {
        this.id = id;
        this.sequence = sequence;
    }

}