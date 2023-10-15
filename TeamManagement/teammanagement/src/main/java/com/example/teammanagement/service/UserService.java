package com.example.teammanagement.service;

import com.example.teammanagement.model.User;
import com.example.teammanagement.model.dtos.AddUserDto;
import com.example.teammanagement.model.dtos.UserDto;
import com.example.teammanagement.repositories.UsersRepository;
import com.example.teammanagement.specification.UserCriteria;
import com.example.teammanagement.specification.UserSpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private final UsersRepository usersRepository;

    public Long addUser(AddUserDto addUserDto) {
        var user = User.builder()
                .name(addUserDto.getName())
                .surname(addUserDto.getSurname())
                .email(addUserDto.getEmail())
                .build();
        return usersRepository.save(user).getId();
    }

    public UserDto getUser(Long id) {
        return usersRepository.findById(id)
                .map(UserDto::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public Page<UserDto> getUsers(UserCriteria userCriteria, Pageable pageable) {
        var spec = UserSpecificationBuilder.build(userCriteria);
        return usersRepository.findAll(spec, pageable)
                .map(UserDto::new);
    }

    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }
}
