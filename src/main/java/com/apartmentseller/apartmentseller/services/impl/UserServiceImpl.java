package com.apartmentseller.apartmentseller.services.impl;

import com.apartmentseller.apartmentseller.domain.Role;
import com.apartmentseller.apartmentseller.domain.User;
import com.apartmentseller.apartmentseller.dto.UserDto;
import com.apartmentseller.apartmentseller.repository.UserRepository;
import com.apartmentseller.apartmentseller.services.MailSender;
import com.apartmentseller.apartmentseller.services.MapperService;
import com.apartmentseller.apartmentseller.services.UserService;
import com.apartmentseller.apartmentseller.services.exceptions.UserNotFoundException;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Value("${url.activate}")
    private String urlForActivateAccount;

    private final UserRepository userRepository;
    private final MailSender mailSender;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, MailSender mailSender) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }

    public UserDto addUser(UserDto userDto) {
        checkForEmptyFields(userDto);

        User userFromDB = userRepository.findByUsername(userDto.getUsername());
        Optional.ofNullable(userFromDB)
                .ifPresent(user -> {
                    throw new IllegalArgumentException("User with username " + userDto.getUsername() + "exist");
                });

        userDto.setRoles(Collections.singleton(Role.USER));
        userDto.setActivationCode(UUID.randomUUID().toString());
        User userEntity = MapperService.INSTANCE.userDtoMapToUserEntity(userDto);
        sendMailToUserEmail(userDto);
        userRepository.save(userEntity);
        return userDto;
    }

    private void checkForEmptyFields(UserDto userDto) {
        Optional.ofNullable(userDto.getUsername())
                .orElseThrow(() -> new InvalidParameterException("Field username required"));
        Optional.ofNullable(userDto.getPassword())
                .orElseThrow(() -> new InvalidParameterException("Field password required"));
        Optional.ofNullable(userDto.getEmail())
                .orElseThrow(() -> new InvalidParameterException("Field email required"));
    }

    private void sendMailToUserEmail(UserDto userDto) {
        String message = String.format("Hello, %s! \n" +
                "Please, visit next link, to activate your account: " +
                urlForActivateAccount +"%s", userDto.getUsername(), userDto.getActivationCode());
        mailSender.send(userDto.getEmail(), "Activate your account", message);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(MapperService.INSTANCE::userEntityMapToUserDto)
                .collect(Collectors.toList());
    }

    public UserDto updateUser(long userId, UserDto currentUser, UserDto userDto) {
        if (ServiceUtils.hasUserPermissionToUpdate(userId, currentUser)) {
            return userRepository.findById(userId)
                    .map(userEntity -> {
                        BeanUtils.copyProperties(userDto, userEntity, "id", "active", "lastVisit", "password");
                        userRepository.save(userEntity);
                        return userDto;
                    })
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
        }
        return null;
    }

    public Optional<UserDto> findById(@NonNull Long userId) {
        return userRepository.findById(userId)
                .map(MapperService.INSTANCE::userEntityMapToUserDto);
    }

    @Override
    public UserDto activateUser(String code) {
        return Optional.ofNullable(userRepository.findByActivationCode(code))
                .map(user -> {
                    user.setActive(true);
                    user.setActivationCode(null);
                    user.setEnable(true);
                    userRepository.save(user);
                    return MapperService.INSTANCE.userEntityMapToUserDto(user);
                    })
                .orElseThrow(() -> new InvalidParameterException("Activation code not found"));
    }
}