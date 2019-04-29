package com.apartmentseller.apartmentseller.controller;

import com.apartmentseller.apartmentseller.domain.User;
import com.apartmentseller.apartmentseller.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> userList(@AuthenticationPrincipal User user){
        return userService.getAllUsers();
    }

    @PutMapping("{id}")
    public User updateUser(@PathVariable("id") User userFromDB, @RequestBody User user){
        return userService.updateUser(userFromDB, user);
    }

    @GetMapping("{id}")
    public User getAnnouncement(@PathVariable("id") User user) {
        return user;
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

}
