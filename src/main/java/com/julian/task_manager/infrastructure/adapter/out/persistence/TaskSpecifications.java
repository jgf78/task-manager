package com.julian.task_manager.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.domain.Specification;

import com.julian.task_manager.domain.model.TaskFilter;

public final class TaskSpecifications {

    private TaskSpecifications() {
    }

    public static Specification<TaskEntity> withFilter(
            TaskFilter filter) {

        return (root, query, criteriaBuilder) -> {

            if (filter == null) {
                return criteriaBuilder.conjunction();
            }

            var predicates = new java.util.ArrayList<
                    jakarta.persistence.criteria.Predicate>();

            if (filter.status() != null) {

                predicates.add(
                        criteriaBuilder.equal(
                                root.get("status"),
                                filter.status()
                        )
                );
            }

            if (filter.title() != null
                    && !filter.title().isBlank()) {

                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(
                                        root.get("title")
                                ),
                                "%" + filter.title().toLowerCase() + "%"
                        )
                );
            }

            return criteriaBuilder.and(
                    predicates.toArray(
                            new jakarta.persistence.criteria.Predicate[0]
                    )
            );
        };
    }
}