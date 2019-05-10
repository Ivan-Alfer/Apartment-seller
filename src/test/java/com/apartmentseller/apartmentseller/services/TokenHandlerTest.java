package com.apartmentseller.apartmentseller.services;

import com.apartmentseller.apartmentseller.services.impl.TokenHandlerImpl;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Optional;

public class TokenHandlerTest {


    @Test
    public void generateToken() {
        TokenHandlerImpl tokenHandler = new TokenHandlerImpl("jwtKey123456789", "AES", 864_000_000);
        String token = tokenHandler.generateToken(10L);
        System.out.println(token);
        Optional<Long> id = tokenHandler.extractUserId(token);
        System.out.println(id.get().toString());
    }
}