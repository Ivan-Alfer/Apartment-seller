package com.apartmentseller.apartmentseller.repo;

import com.apartmentseller.apartmentseller.PostgresInitializer;
import com.apartmentseller.apartmentseller.domain.User;
import com.apartmentseller.apartmentseller.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = PostgresInitializer.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void getUserByName(){
        User user1 = userRepository.save(new User("test1","test1"));
        User user2 = userRepository.save(new User("test2","test2"));

        assertNotNull(userRepository.findByUsername("test1"));
    }


}
