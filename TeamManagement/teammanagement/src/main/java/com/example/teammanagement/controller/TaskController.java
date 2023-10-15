package com.example.teammanagement.controller;

import com.example.teammanagement.model.Task;
import com.example.teammanagement.model.TaskStatus;
import com.example.teammanagement.model.dtos.AddTaskDto;
import com.example.teammanagement.model.dtos.TaskDto;
import com.example.teammanagement.model.dtos.UpdateTaskDto;
import com.example.teammanagement.service.TaskService;
import com.example.teammanagement.specification.TaskCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long addTask(@RequestBody AddTaskDto addTaskDto) {
        return taskService.addTask(addTaskDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Long updateTask(@PathVariable Long id, @RequestBody UpdateTaskDto updateTaskDto) {
        return taskService.updateTask(id, updateTaskDto);
    }

    @PutMapping("/{id}/status")
    @ResponseStatus(HttpStatus.CREATED)
    public Long changeStatus(@PathVariable Long id, @RequestParam TaskStatus taskStatus) {
        return taskService.changeStatus(id, taskStatus);
    }

    @PutMapping("/{id}/users")
    @ResponseStatus(HttpStatus.CREATED)
    public Long addUsersToTask(@PathVariable Long id, @RequestParam List<Long> userIds) {
        return taskService.addUsersToTask(id, userIds);
    }

    @GetMapping
    public Page<TaskDto> getTasks(TaskCriteria taskCriteria, Pageable pageable) {
        return taskService.getTasks(taskCriteria, pageable);
    }

    @DeleteMapping("/{id}")
    public void deleteTasks(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}
