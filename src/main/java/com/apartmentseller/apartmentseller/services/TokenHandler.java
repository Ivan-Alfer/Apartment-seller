package com.apartmentseller.apartmentseller.services;

import lombok.NonNull;

import java.util.Optional;

public interface TokenHandler {

    Optional<Long> extractUserId(@NonNull String token);
    String generateToken(@NonNull Long id);
}
