package com.apartmentseller.apartmentseller.services;

import com.apartmentseller.apartmentseller.dto.UserDto;
import com.apartmentseller.apartmentseller.web.UserAuthentication;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
public class TokenAuthService {

    private static final String AUTH_HEADER_NAME = "X-Auth-Token";

    private final TokenHandler tokenHandler;

    private final UserService userService;

    @Autowired
    public TokenAuthService(TokenHandler tokenHandler, UserService userService) {
        this.tokenHandler = tokenHandler;
        this.userService = userService;
    }

    public Optional<Authentication> getAuthentication(@NonNull HttpServletRequest request) {

        return Optional.ofNullable(request.getHeader(AUTH_HEADER_NAME))
                .flatMap(tokenHandler::extractUserId)
                .flatMap(userService::findById)
                .map(UserAuthentication::new);
    }

    public void addAuthentication(Authentication authResult, HttpServletResponse response) {
        UserDto user = (UserDto) authResult.getPrincipal();
        response.setHeader(AUTH_HEADER_NAME, tokenHandler.generateToken(user.getId()));
    }

}
