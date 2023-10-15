package com.example.teammanagement.model.dtos;

import com.example.teammanagement.model.Task;
import com.example.teammanagement.model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private LocalDate deadline;
    private TaskStatus status;
    private List<UserDto> users;

    public TaskDto(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.deadline = task.getDeadline();
        this.status = task.getStatus();
        this.users = task.getUsers().stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }
}
