package com.apartmentseller.apartmentseller.services.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDoesNotHavePermission extends RuntimeException {
    private String message;
}
