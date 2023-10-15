package com.example.teammanagement;

import com.example.teammanagement.model.Task;
import com.example.teammanagement.model.TaskStatus;
import com.example.teammanagement.model.User;
import com.example.teammanagement.model.dtos.AddTaskDto;
import com.example.teammanagement.model.dtos.UpdateTaskDto;
import com.example.teammanagement.repositories.TasksRepository;
import com.example.teammanagement.repositories.UsersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TaskControllerTest {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private TasksRepository tasksRepository;
    @Autowired
    private MockMvc mvc;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private final Task task = Task.builder()
            .id(1L)
            .title("title")
            .description("description")
            .deadline(LocalDate.of(2020, 10, 9))
            .status(TaskStatus.TO_DO)
            .build();;
    private final List<User> users = List.of(
            User.builder().id(1L).build(),
            User.builder().id(2L).build()
    );

    @AfterEach
    public void clear() {
        tasksRepository.deleteAll();
        usersRepository.deleteAll();
    }

    @Test
    public void shouldAddTask() throws Exception {
        usersRepository.saveAll(users);
        var addTaskDto = new AddTaskDto(
                "title",
                "description",
                LocalDate.now(),
                List.of(1L, 2L)
        );

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/task")
                        .content(objectMapper.writeValueAsString(addTaskDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        assertEquals(1L, tasksRepository.count());
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        tasksRepository.save(task);

        var updateTaskDto = new UpdateTaskDto("title", "des", LocalDate.of(2010, 2, 4));
        mvc.perform(MockMvcRequestBuilders
                        .put("/api/task/" + task.getId())
                        .content(objectMapper.writeValueAsString(updateTaskDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        var result = tasksRepository.findById(task.getId());
        assertTrue(result.isPresent());
        assertEquals("title", result.get().getTitle());
        assertEquals("des", result.get().getDescription());
        assertEquals(LocalDate.of(2010, 2, 4), result.get().getDeadline());
    }

    @Test
    public void changeStatus() throws Exception {
        tasksRepository.save(task);

        mvc.perform(MockMvcRequestBuilders
                        .put("/api/task/" + task.getId() + "/status?taskStatus=IN_PROGRESS"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        var result = tasksRepository.findById(task.getId());
        assertTrue(result.isPresent());
        assertEquals(TaskStatus.IN_PROGRESS, result.get().getStatus());
    }

    @Test
    @Transactional
    public void shouldAddUsersToTask() throws Exception {
        usersRepository.saveAll(users);
        var ids = usersRepository.findAll().stream().map(User::getId).toList();
        tasksRepository.save(task);

        mvc.perform(MockMvcRequestBuilders
                        .put("/api/task/" + task.getId() + "/users?userIds=" + ids.get(0) + "&userIds=" + ids.get(1)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        var result = tasksRepository.findById(task.getId());
        assertTrue(result.isPresent());
        assertEquals(2, result.get().getUsers().size());
    }

    @Test
    public void shouldGetTasks() throws Exception {
        tasksRepository.save(task);

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/task?title=title&description=desc&status=TO_DO&deadlineFrom=2020-10-08&deadlineTo=2020-10-15")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("title"))
                .andExpect(jsonPath("$.content[0].description").value("description"))
                .andExpect(jsonPath("$.content[0].deadline").value("2020-10-09"))
                .andExpect(jsonPath("$.content[0].status").value("TO_DO"));
    }

    @Test
    public void shouldDeleteTask() throws Exception {
        usersRepository.saveAll(users);
        task.setUsers(users);
        tasksRepository.save(task);

        mvc.perform(MockMvcRequestBuilders.delete("/api/task/" + task.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        assertEquals(0, tasksRepository.count());
    }
}
