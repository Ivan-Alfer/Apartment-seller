package com.apartmentseller.apartmentseller.config.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class TokenHandlerProperty {
    @Value("${jwt.key}")
    private String jwtKey;
    @Value("${decoded.name}")
    private String decoderName;
    @Value("${jwt.expirationTimeInMS}")
    private long expirationTime;
}
