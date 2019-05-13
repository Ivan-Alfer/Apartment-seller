package com.apartmentseller.apartmentseller.services.impl;

import com.apartmentseller.apartmentseller.domain.Role;
import com.apartmentseller.apartmentseller.dto.UserDto;

public class ServiceUtils {

    public static boolean hasUserPermissionToUpdate(long userId, UserDto currentUser) {
        return currentUser.getId() == userId ||
                currentUser.getRoles()
                        .stream()
                        .anyMatch(Role.ADMIN::equals);
    }

}
