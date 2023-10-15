package com.example.teammanagement.specification;

import com.example.teammanagement.model.TaskStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskCriteria {
    private String title;
    private String description;
    private LocalDate deadlineFrom;
    private LocalDate deadlineTo;
    private TaskStatus status;
}
