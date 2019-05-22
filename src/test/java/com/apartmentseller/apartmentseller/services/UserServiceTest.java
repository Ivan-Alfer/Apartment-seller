package com.apartmentseller.apartmentseller.services;

import com.apartmentseller.apartmentseller.PostgresInitializer;
import com.apartmentseller.apartmentseller.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = PostgresInitializer.class)
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void addUser() {

    }

    @Test
    public void getAllUsers() {
    }

    @Test
    public void updateUser() {
    }

    @Test
    public void findById() {
    }

    @Test
    public void activateUser() {
    }
}