package com.apartmentseller.apartmentseller.dto;

import com.apartmentseller.apartmentseller.domain.Role;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserDto {

    private Long id;

    private String username;

    private String password;

    private boolean active;

    private Set<Role> roles;

    private LocalDateTime lastVisit;

}
