package com.apartmentseller.apartmentseller.services.impl;

import com.apartmentseller.apartmentseller.config.property.TokenHandlerProperty;
import com.apartmentseller.apartmentseller.services.TokenHandler;
import com.google.common.io.BaseEncoding;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Optional;

@Service
public class TokenHandlerImpl implements TokenHandler {

    private final SecretKey secretKey;

    private final long expirationTime;

    public TokenHandlerImpl(TokenHandlerProperty tokenHandlerProperty) {
        byte[] decodedKey = BaseEncoding.base64().decode(tokenHandlerProperty.getKey());
        secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, tokenHandlerProperty.getDecoderName());

        this.expirationTime = tokenHandlerProperty.getExpirationTimeInMS();
    }

    public Optional<String> extractUser(@NonNull String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            Claims body = claimsJws.getBody();
            return Optional.ofNullable(body.getId())
                    .map(String::new);
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }

    public String generateToken(@NonNull String username) {
        return Jwts.builder()
                .setId(username)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}
