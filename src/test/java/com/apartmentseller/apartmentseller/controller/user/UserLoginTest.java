package com.apartmentseller.apartmentseller.controller.user;

import com.apartmentseller.apartmentseller.PostgresInitializer;
import com.apartmentseller.apartmentseller.domain.User;
import com.apartmentseller.apartmentseller.dto.UserDto;
import com.apartmentseller.apartmentseller.repository.UserRepository;
import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = PostgresInitializer.class)
public class UserLoginTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private static final User user = new User("test1","test1");
    private static final User user2 = new User("test2","test2");

    @Before
    public void addDataToBD(){
        user.setEnable(true);
        userRepository.save(user);
        userRepository.save(user2);

    }

    @After
    public void removeDataFromBD(){
        userRepository.delete(user);
    }

    @Test
    public void successLogin() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("test1");
        userDto.setPassword("test1");
        Gson gson = new Gson();
        String param = gson.toJson(userDto);
        this.mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(param))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void badCredentials() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("qqq");
        userDto.setPassword("p");
        Gson gson = new Gson();
        String param = gson.toJson(userDto);
        this.mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(param))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void userIsNotEnable() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("test2");
        userDto.setPassword("test2");
        Gson gson = new Gson();
        String param = gson.toJson(userDto);
        this.mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(param))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

}
