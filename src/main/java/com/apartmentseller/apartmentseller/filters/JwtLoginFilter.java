package com.apartmentseller.apartmentseller.filters;

import com.apartmentseller.apartmentseller.dto.UserDto;
import com.apartmentseller.apartmentseller.services.TokenAuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final TokenAuthService tokenAuthService;

    private final AuthenticationManager authenticationManager;

    private final String authHeaderName;

    public JwtLoginFilter(AuthenticationManager authenticationManager, TokenAuthService tokenAuthService, String authHeaderName) {
        this.authenticationManager = authenticationManager;
        this.tokenAuthService = tokenAuthService;
        this.authHeaderName = authHeaderName;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (Objects.isNull(request)) {
            return null;
        }
        UserDto user = getUserFromRequest(request);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), Collections.emptyList());

        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    private UserDto getUserFromRequest(HttpServletRequest request) {
        UserDto user = null;
        try {
            user = new ObjectMapper().readValue(request.getInputStream(), UserDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        String token = tokenAuthService.addAuthentication(authResult);
        response.setHeader(authHeaderName, token);
    }


}
