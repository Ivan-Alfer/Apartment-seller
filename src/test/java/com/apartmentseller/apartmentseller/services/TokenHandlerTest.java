package com.apartmentseller.apartmentseller.services;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;

public class TokenHandlerTest {


    @Test
    public void generateToken() {
        TokenHandler tokenHandler = new TokenHandler("jwtKey123456789", "AES", 864_000_000);
        String token = tokenHandler.generateToken(10L, LocalDateTime.now().plusDays(14));
        System.out.println(token);
        Optional<Long> id = tokenHandler.extractUserId(token);
        System.out.println(id.get().toString());
    }
}