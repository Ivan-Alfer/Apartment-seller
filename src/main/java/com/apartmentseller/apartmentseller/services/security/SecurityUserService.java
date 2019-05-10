package com.apartmentseller.apartmentseller.services.security;

import com.apartmentseller.apartmentseller.dto.UserDto;
import com.apartmentseller.apartmentseller.repository.UserRepository;
import com.apartmentseller.apartmentseller.services.MapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final MapperService mapperService;

    @Autowired
    public SecurityUserService(UserRepository userRepository, MapperService mapperService) {
        this.userRepository = userRepository;
        this.mapperService = mapperService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return mapperService.mapEntityWithDto(userRepository.findByUsername(username), new UserDto());

    }
}
