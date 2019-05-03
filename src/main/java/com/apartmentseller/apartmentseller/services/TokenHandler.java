package com.apartmentseller.apartmentseller.services;

import com.google.common.io.BaseEncoding;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Optional;

@Service
public class TokenHandler {

    private final SecretKey secretKey;

    private final long expirationTime;

    public TokenHandler(@Value("${jwt.key}") String jwtKey, @Value("${decoded.name}") String decoderName, @Value("${jwt.expirationTimeInMS}") long expirationTime) {
        byte[] decodedKey = BaseEncoding.base64().decode(jwtKey);
        secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, decoderName);

        this.expirationTime = expirationTime;
    }

    public Optional<Long> extractUserId(@NonNull String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            Claims body = claimsJws.getBody();
            return Optional.ofNullable(body.getId())
                    .map(Long::new);
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }

    public String generateToken(@NonNull Long id) {
        return Jwts.builder()
                .setId(id.toString())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}
