package com.example.teammanagement.specification;

import com.example.teammanagement.model.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecificationBuilder {
    public static Specification<User> build(UserCriteria userCriteria) {
        return Specification.where(filterName(userCriteria.getName()))
                .and(filterSurname(userCriteria.getSurname()))
                .and(filterEmail(userCriteria.getEmail()));
    }

    private static Specification<User> filterName(String name) {
        return ((root, query, criteriaBuilder) -> name == null || name.isBlank() ? criteriaBuilder.conjunction()
                : criteriaBuilder.like(root.get("name"), "%" + name + "%"));
    }

    private static Specification<User> filterSurname(String surname) {
        return ((root, query, criteriaBuilder) -> surname == null || surname.isBlank() ? criteriaBuilder.conjunction()
                : criteriaBuilder.like(root.get("surname"), "%" + surname + "%"));
    }

    private static Specification<User> filterEmail(String email) {
        return ((root, query, criteriaBuilder) -> email == null || email.isBlank() ? criteriaBuilder.conjunction()
                : criteriaBuilder.like(root.get("email"), "%" + email + "%"));
    }
}
