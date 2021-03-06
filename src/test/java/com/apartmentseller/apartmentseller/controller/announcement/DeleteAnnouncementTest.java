package com.apartmentseller.apartmentseller.controller.announcement;

import com.apartmentseller.apartmentseller.PostgresInitializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = PostgresInitializer.class)
@Sql(value = {"/create-user-before.sql", "/create-announcement-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete-announcement-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class DeleteAnnouncementTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails(value = "q")
    public void adminDeletesAnnouncement() throws Exception {
        mockMvc.perform(delete("/announcement/2")
                .header("X-Auth-Token", "eyJhbGciOiJIUzUxMiJ9." +
                        "eyJqdGkiOiJxIiwiZXhwIjoxNTU5Mzg5NTQ3fQ." +
                        "icydP3jYahr6dEJJPMkPPiY2hfLWHZLB19AZQDC7ltgypHq90WwhghvfihTN9F5kb3nAmcsB5eP1Cj66ZHScjQ"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "a")
    public void ownerDeletesAnnouncement() throws Exception {
        mockMvc.perform(delete("/announcement/2")
                .header("X-Auth-Token", "eyJhbGciOiJIUzUxMiJ9." +
                        "eyJqdGkiOiJxIiwiZXhwIjoxNTU5Mzg5NTQ3fQ." +
                        "icydP3jYahr6dEJJPMkPPiY2hfLWHZLB19AZQDC7ltgypHq90WwhghvfihTN9F5kb3nAmcsB5eP1Cj66ZHScjQ"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "g")
    public void someoneDeletesAnnouncement() throws Exception {
        mockMvc.perform(delete("/announcement/2")
                .header("X-Auth-Token", "eyJhbGciOiJIUzUxMiJ9." +
                        "eyJqdGkiOiJnIiwiZXhwIjoxNTU5NDY4ODA4fQ." +
                        "WuFGol5vtNaQ12gv5Wl843FVOyto5ovwE3KUL8kGXJNZWWFGeLIxRQeUMviOq8WWe3Zr3n3RiGBD2ASV8E-yjQ"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
