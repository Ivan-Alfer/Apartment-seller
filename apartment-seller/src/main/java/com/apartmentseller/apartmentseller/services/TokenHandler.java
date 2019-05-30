package com.apartmentseller.apartmentseller.services;

import lombok.NonNull;

import java.util.Optional;

public interface TokenHandler {

    Optional<String> extractUser(@NonNull String token);
    String generateToken(@NonNull String username);
}
