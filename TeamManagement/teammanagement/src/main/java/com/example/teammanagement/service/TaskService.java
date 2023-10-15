package com.example.teammanagement.service;

import com.example.teammanagement.model.Task;
import com.example.teammanagement.model.TaskStatus;
import com.example.teammanagement.model.dtos.AddTaskDto;
import com.example.teammanagement.model.dtos.TaskDto;
import com.example.teammanagement.model.dtos.UpdateTaskDto;
import com.example.teammanagement.repositories.TasksRepository;
import com.example.teammanagement.repositories.UsersRepository;
import com.example.teammanagement.specification.TaskCriteria;
import com.example.teammanagement.specification.TaskSpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskService {
    private final TasksRepository tasksRepository;
    private final UsersRepository usersRepository;

    public Long addTask(AddTaskDto addTaskDto) {
        var users = addTaskDto.getUserIds().stream()
                .map(usersRepository::getReferenceById)
                .collect(Collectors.toList());

        var task = Task.builder()
                .title(addTaskDto.getTitle())
                .description(addTaskDto.getDescription())
                .deadline(addTaskDto.getDeadline())
                .users(users)
                .build();
        return tasksRepository.save(task).getId();
    }

    public Long updateTask(Long id, UpdateTaskDto updateTaskDto) {
        var task = tasksRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        task.setTitle(updateTaskDto.getTitle());
        task.setDescription(updateTaskDto.getDescription());
        task.setDeadline(updateTaskDto.getDeadline());
        return tasksRepository.save(task).getId();
    }

    public Long changeStatus(Long id, TaskStatus taskStatus) {
        var task = tasksRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        task.setStatus(taskStatus);
        return tasksRepository.save(task).getId();
    }

    public Long addUsersToTask(Long taskId, List<Long> userIds) {
        var users = usersRepository.findAllById(userIds);
        var task = tasksRepository.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        task.setUsers(users);
        return tasksRepository.save(task).getId();
    }

    public Page<TaskDto> getTasks(TaskCriteria taskCriteria, Pageable pageable) {
        var specification = TaskSpecificationBuilder.build(taskCriteria);
        return tasksRepository.findAll(specification, pageable)
                .map(TaskDto::new);
    }

    public void deleteTask(Long id) {
        tasksRepository.deleteById(id);
    }
}
