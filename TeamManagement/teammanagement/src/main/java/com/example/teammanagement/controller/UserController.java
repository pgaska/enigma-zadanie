package com.example.teammanagement.controller;

import com.example.teammanagement.model.dtos.AddUserDto;
import com.example.teammanagement.model.dtos.UserDto;
import com.example.teammanagement.service.UserService;
import com.example.teammanagement.specification.UserCriteria;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long addUser(@RequestBody @Valid AddUserDto addUserDto) {
        return userService.addUser(addUserDto);
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping
    public Page<UserDto> findUsers(UserCriteria userCriteria, Pageable pageable) {
        return userService.getUsers(userCriteria, pageable);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
