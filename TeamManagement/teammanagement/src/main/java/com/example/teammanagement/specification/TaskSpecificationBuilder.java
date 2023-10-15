package com.example.teammanagement.specification;

import com.example.teammanagement.model.Task;
import com.example.teammanagement.model.TaskStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class TaskSpecificationBuilder {
    public static Specification<Task> build(TaskCriteria taskCriteria) {
        return Specification.where(filterTitle(taskCriteria.getTitle()))
                .and(filterDescription(taskCriteria.getDescription()))
                .and(filterDeadlineFrom(taskCriteria.getDeadlineFrom()))
                .and(filterDeadlineTo(taskCriteria.getDeadlineTo()))
                .and(filterStatus(taskCriteria.getStatus()));
    }

    private static Specification<Task> filterTitle(String title) {
        return ((root, query, criteriaBuilder) -> title == null || title.isBlank() ? criteriaBuilder.conjunction()
                : criteriaBuilder.like(root.get("title"), "%" + title + "%"));
    }

    private static Specification<Task> filterDescription(String description) {
        return ((root, query, criteriaBuilder) -> description == null || description.isBlank() ? criteriaBuilder.conjunction()
                : criteriaBuilder.like(root.get("description"), "%" + description + "%"));
    }

    private static Specification<Task> filterDeadlineFrom(LocalDate deadlineFrom) {
        return ((root, query, criteriaBuilder) -> deadlineFrom == null ? criteriaBuilder.conjunction()
                : criteriaBuilder.greaterThanOrEqualTo(root.get("deadline"), deadlineFrom));
    }

    private static Specification<Task> filterDeadlineTo(LocalDate deadlineTo) {
        return ((root, query, criteriaBuilder) -> deadlineTo == null ? criteriaBuilder.conjunction()
                : criteriaBuilder.lessThanOrEqualTo(root.get("deadline"), deadlineTo));
    }

    private static Specification<Task> filterStatus(TaskStatus status) {
        return ((root, query, criteriaBuilder) -> status == null ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get("status"), status));
    }
}
