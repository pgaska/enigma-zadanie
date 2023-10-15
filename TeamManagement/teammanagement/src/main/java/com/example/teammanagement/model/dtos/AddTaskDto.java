package com.example.teammanagement.model.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddTaskDto {
    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    @NotNull
    private LocalDate deadline;

    private List<Long> userIds = new ArrayList<>();
}
