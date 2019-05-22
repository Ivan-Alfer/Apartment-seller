package com.apartmentseller.apartmentseller.controller.user;

import com.apartmentseller.apartmentseller.PostgresInitializer;
import com.apartmentseller.apartmentseller.domain.Role;
import com.apartmentseller.apartmentseller.domain.User;
import com.apartmentseller.apartmentseller.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NonNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = PostgresInitializer.class)
public class ShowAllUsersTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private static final User user = new User("q", "q");
    private static final User user2 = new User("a", "a");

    @Before
    public void addDataToBD() {
        user.setEnable(true);
        user.setRoles(Collections.singleton(Role.ADMIN));
        userRepository.save(user);
        user2.setEnable(true);
        user2.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user2);
    }

    @After
    public void removeDataFromDB() {
        userRepository.deleteAll();
    }

    @Test
    public void showForAdmin() throws Exception {
        mockMvc.perform(get("/user").header("X-Auth-Token", "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJxIiwiZXhwIjoxNTU5Mzg5NTQ3fQ.icydP3jYahr6dEJJPMkPPiY2hfLWHZLB19AZQDC7ltgypHq90WwhghvfihTN9F5kb3nAmcsB5eP1Cj66ZHScjQ"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void forbiddenForOtherUsers() throws Exception {
        mockMvc.perform(get("/user").header("X-Auth-Token", "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJhIiwiZXhwIjoxNTU5Mzg5NDgxfQ.uGAgxH2AumSyak5Ir0nRChRt8HRQGaNvAuFhFeSs5x7HmtwB7qy60yfT5c7J9DBrd0lXXrVMOq-tyjbw3qYPXQ"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void authenticationForAnonUsers() throws Exception {
        mockMvc.perform(get("/user"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    private String generateToken(@NonNull Long id) {
        return Jwts.builder()
                .setId(id.toString())
                .signWith(SignatureAlgorithm.HS512, "jwtKey123456789")
                .compact();
    }
}
