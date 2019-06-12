package com.apartmentseller.apartmentseller.services;

import com.apartmentseller.apartmentseller.dto.UserDto;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDto addUser(UserDto userDto);
    List<UserDto> getAllUsers();
    UserDto updateUser(long userId, UserDto currentUser, UserDto user1);
    Optional<UserDto> findById(@NonNull Long userId);
    UserDto activateUser(String code);
}
