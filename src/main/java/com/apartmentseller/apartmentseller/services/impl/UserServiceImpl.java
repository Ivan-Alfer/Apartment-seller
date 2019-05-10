package com.apartmentseller.apartmentseller.services.impl;

import com.apartmentseller.apartmentseller.domain.Role;
import com.apartmentseller.apartmentseller.domain.User;
import com.apartmentseller.apartmentseller.dto.UserDto;
import com.apartmentseller.apartmentseller.repository.UserRepository;
import com.apartmentseller.apartmentseller.services.MapperService;
import com.apartmentseller.apartmentseller.services.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MapperService mapperService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, MapperService mapperService) {
        this.userRepository = userRepository;
        this.mapperService = mapperService;
    }

    public UserDto addUser(UserDto userDto){
        if(StringUtils.isEmpty(userDto.getUsername()) || StringUtils.isEmpty(userDto.getPassword())){
            // TODO:
            return null;
        }
        User userFromDB = userRepository.findByUsername(userDto.getUsername());
        if (Objects.nonNull(userFromDB)){
            // TODO:
            return null;
        }
        User userEntity = mapperService.mapEntityWithDto(userDto, new User());
        userEntity.setActive(true);
        userEntity.setRoles(Collections.singleton(Role.USER));
        userRepository.save(userEntity);
        return userDto;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userEntity -> mapperService.mapEntityWithDto(userEntity, new UserDto()))
                .collect(Collectors.toList());
    }

    public UserDto updateUser(UserDto user, UserDto user1){
        // TODO:
        return user;
    }

    public Optional<UserDto> findById(@NonNull Long userId) {
        return Optional.ofNullable(mapperService
                .mapEntityWithDto(userRepository.findById(userId).orElse(null), new UserDto()));
    }
}