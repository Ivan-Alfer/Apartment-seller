package com.apartmentseller.apartmentseller.controller.announcement;

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
@Sql(value = {"/create-announcement-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete-announcement-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(value = {"/delete-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class GetAllAnnouncementTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void showForAdmin() throws Exception {
        mockMvc.perform(get("/announcement").header("X-Auth-Token", "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJxIiwiZXhwIjoxNTU5Mzg5NTQ3fQ.icydP3jYahr6dEJJPMkPPiY2hfLWHZLB19AZQDC7ltgypHq90WwhghvfihTN9F5kb3nAmcsB5eP1Cj66ZHScjQ"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void showForUser() throws Exception {
        mockMvc.perform(get("/announcement").header("X-Auth-Token", "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJhIiwiZXhwIjoxNTU5Mzg5NDgxfQ.uGAgxH2AumSyak5Ir0nRChRt8HRQGaNvAuFhFeSs5x7HmtwB7qy60yfT5c7J9DBrd0lXXrVMOq-tyjbw3qYPXQ"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void showForAnon() throws Exception {
        mockMvc.perform(get("/announcement"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
