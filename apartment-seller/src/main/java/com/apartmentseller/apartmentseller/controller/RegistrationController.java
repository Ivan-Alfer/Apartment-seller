package com.apartmentseller.apartmentseller.controller;

import com.apartmentseller.apartmentseller.dto.UserDto;
import com.apartmentseller.apartmentseller.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public UserDto signUp(@RequestBody UserDto user) {
        return userService.addUser(user);
    }

    @GetMapping("/activate/{code}")
    public UserDto activateAccount(@PathVariable String code){
            return userService.activateUser(code);
    }
}