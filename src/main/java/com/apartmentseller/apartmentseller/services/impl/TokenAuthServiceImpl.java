package com.apartmentseller.apartmentseller.services.impl;

import com.apartmentseller.apartmentseller.services.TokenAuthService;
import com.apartmentseller.apartmentseller.services.TokenHandler;
import com.apartmentseller.apartmentseller.services.UserService;
import com.apartmentseller.apartmentseller.services.security.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenAuthServiceImpl implements TokenAuthService {

    private final TokenHandler tokenHandler;

    private final UserService userService;

    @Autowired
    public TokenAuthServiceImpl(TokenHandler tokenHandler, UserService userService) {
        this.tokenHandler = tokenHandler;
        this.userService = userService;
    }

    public Optional<Authentication> getAuthentication(String authToken) {
        return Optional.ofNullable(authToken)
                .flatMap(tokenHandler::extractUserId)
                .flatMap(userService::findById)
                .map(UserAuthentication::new);
    }

    public String addAuthentication(Authentication authResult) {
        UserAuthentication userAuthentication = (UserAuthentication) authResult.getPrincipal();
        return tokenHandler.generateToken(userAuthentication.getUser().getId());
    }

}
