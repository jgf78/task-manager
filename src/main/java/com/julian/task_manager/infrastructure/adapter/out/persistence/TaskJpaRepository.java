package com.julian.task_manager.infrastructure.adapter.out.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.julian.task_manager.domain.model.TaskStatus;

public interface TaskJpaRepository extends JpaRepository<TaskEntity, Long> {
    Page<TaskEntity> findAllByStatus(
            TaskStatus status,
            Pageable pageable
    );
}
