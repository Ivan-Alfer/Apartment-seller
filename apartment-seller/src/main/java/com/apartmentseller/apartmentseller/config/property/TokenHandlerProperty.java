package com.apartmentseller.apartmentseller.config.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "jwt")
public class TokenHandlerProperty {
    private String key;
    private long expirationTimeInMS;
    @Value("${decoded.name}")
    private String decoderName;
}
