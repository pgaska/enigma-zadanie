package com.example.teammanagement;

import com.example.teammanagement.model.User;
import com.example.teammanagement.model.dtos.AddUserDto;
import com.example.teammanagement.repositories.UsersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserControllerTest {
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private MockMvc mvc;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@AfterEach
	public void clear() {
		usersRepository.deleteAll();
	}

	@Test
	public void shouldSaveUser() throws Exception {
		var addUserDto = new AddUserDto("name", "surname", "email@email.com");
		mvc.perform(MockMvcRequestBuilders
						.post("/api/user")
						.content(objectMapper.writeValueAsString(addUserDto))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated());
		assertEquals(1L, usersRepository.count());
	}

	@Test
	public void shouldGetUsers() throws Exception {
		var users = List.of(
				User.builder().name("Jan").surname("abcdd").email("123@gmail.com").build(),
				User.builder().name("Janina").surname("zxy").email("mail@yahoo.pl").build()
		);
		usersRepository.saveAll(users);

		mvc.perform(MockMvcRequestBuilders
						.get("/api/user?name=Jan&surname=zxy&email=mail@yahoo.pl")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.content[0].name").value("Janina"))
				.andExpect(jsonPath("$.content[0].surname").value("zxy"))
				.andExpect(jsonPath("$.content[0].email").value("mail@yahoo.pl"));
	}

	@Test
	public void shouldDeleteUser() throws Exception {
		var user = User.builder().id(1L).build();
		usersRepository.save(user);

		mvc.perform(MockMvcRequestBuilders.delete("/api/user/" + user.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk());
		assertEquals(0, usersRepository.count());
	}
}
