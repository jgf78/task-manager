package com.julian.task_manager.infrastructure.adapter.out.persistence;

import java.util.ArrayList;
import java.util.Locale;

import org.springframework.data.jpa.domain.Specification;

import com.julian.task_manager.domain.model.TaskFilter;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public final class TaskSpecifications {

    private TaskSpecifications() {
    }

    public static Specification<TaskEntity> withFilter(
            TaskFilter filter) {

        return (Root<TaskEntity> root,
                @SuppressWarnings("unused") CriteriaQuery<?> query,
                CriteriaBuilder criteriaBuilder) -> {

            if (filter == null) {
                return criteriaBuilder.conjunction();
            }

            var predicates = new ArrayList<Predicate>();

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

                var titleFilter = "%" + filter.title().toLowerCase(Locale.ROOT) + "%";

                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(
                                        root.get("title")
                                ),
                                titleFilter
                        )
                );
            }

            return criteriaBuilder.and(
                    predicates.toArray(
                            new Predicate[0]
                    )
            );
        };
    }
}