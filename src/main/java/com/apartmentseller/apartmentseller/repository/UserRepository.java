package com.apartmentseller.apartmentseller.repository;

import com.apartmentseller.apartmentseller.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
