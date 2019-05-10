package com.apartmentseller.apartmentseller.services;

import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface TokenAuthService {

    Optional<Authentication> getAuthentication(String authToken);
    String addAuthentication(Authentication authResult);
}
