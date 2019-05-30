package com.apartmentseller.apartmentseller.controller;

import com.apartmentseller.apartmentseller.dto.UserDto;
import com.apartmentseller.apartmentseller.services.UserService;
import com.apartmentseller.apartmentseller.services.exceptions.UserDoesNotHavePermission;
import com.apartmentseller.apartmentseller.services.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public List<UserDto> userList(){
        return userService.getAllUsers();
    }

    @PutMapping("{id}")
    public UserDto updateUser(@PathVariable("id") long userId,
                              @AuthenticationPrincipal UserDto currentUser,
                              @RequestBody UserDto userChanges) {
            return Optional.ofNullable(userService.updateUser(userId,currentUser, userChanges))
                    .orElseThrow(()-> new UserDoesNotHavePermission("You don't have permission"));
    }

    @GetMapping("{id}")
    public UserDto getUser(@PathVariable("id") long userId) {
        return userService.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User - " + userId + " not found"));
    }
}
