package com.apartmentseller.apartmentseller.services;

import com.apartmentseller.apartmentseller.domain.Role;
import com.apartmentseller.apartmentseller.domain.User;
import com.apartmentseller.apartmentseller.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public User addUser(User user){
        if(StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())){
            // TODO:
            return null;
        }
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (Objects.nonNull(userFromDB)){
            // TODO:
            return null;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(User user, User user1){
        // TODO:
        return user;
    }
}