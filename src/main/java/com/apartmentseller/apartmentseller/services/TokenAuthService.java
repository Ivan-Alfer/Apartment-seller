package com.apartmentseller.apartmentseller.services;

import lombok.NonNull;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public interface TokenAuthService {

    Optional<Authentication> getAuthentication(@NonNull HttpServletRequest request);
    void addAuthentication(Authentication authResult, HttpServletResponse response);
}
