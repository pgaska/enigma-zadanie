package com.example.teammanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long id;

    private String name;

    private String surname;

    private String email;

    @ManyToMany(mappedBy = "users")
    private List<Task> tasks;

    @PreRemove
    public void removeTaskAssociations() {
        if (tasks != null) {
            tasks.forEach(task -> task.getUsers().remove(this));
        }
    }
}
