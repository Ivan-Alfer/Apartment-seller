package com.apartmentseller.apartmentseller.controller.user;

import com.apartmentseller.apartmentseller.PostgresInitializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = PostgresInitializer.class)
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class GetUserTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getUser() throws Exception {
        mockMvc.perform(get("/user/2").header("X-Auth-Token", "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJxIiwiZXhwIjoxNTU5Mzg5NTQ3fQ.icydP3jYahr6dEJJPMkPPiY2hfLWHZLB19AZQDC7ltgypHq90WwhghvfihTN9F5kb3nAmcsB5eP1Cj66ZHScjQ"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void notExist() throws Exception {
        mockMvc.perform(get("/user/7").header("X-Auth-Token", "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJxIiwiZXhwIjoxNTU5Mzg5NTQ3fQ.icydP3jYahr6dEJJPMkPPiY2hfLWHZLB19AZQDC7ltgypHq90WwhghvfihTN9F5kb3nAmcsB5eP1Cj66ZHScjQ"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}
