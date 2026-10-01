package com.julian.task_manager.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TaskJpaRepository
        extends JpaRepository<TaskEntity, Long>,
                JpaSpecificationExecutor<TaskEntity> {
}