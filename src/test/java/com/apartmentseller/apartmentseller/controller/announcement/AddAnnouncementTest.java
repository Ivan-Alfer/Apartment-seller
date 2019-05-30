package com.apartmentseller.apartmentseller.controller.announcement;

import com.apartmentseller.apartmentseller.PostgresInitializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = PostgresInitializer.class)
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete-announcement-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AddAnnouncementTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails(value = "a")
    public void addAnnouncementWithImage() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file",
                "image file name",
                null,
                "test text".getBytes());
        MockMultipartFile announcement = new MockMultipartFile("announcement",
                "", MediaType.APPLICATION_JSON_VALUE, "{\"text\": \"some value\"}".getBytes());

        mockMvc.perform(multipart("/announcement")
                    .file(file)
                    .file(announcement)
                    .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                    .header("X-Auth-Token",
                        "eyJhbGciOiJIUzUxMiJ9." +
                                "eyJqdGkiOiJhIiwiZXhwIjoxNTU5Mzg5NDgxfQ." +
                                "uGAgxH2AumSyak5Ir0nRChRt8HRQGaNvAuFhFeSs5x7HmtwB7qy60yfT5c7J9DBrd0lXXrVMOq-tyjbw3qYPXQ"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "a")
    public void addAnnouncementWithoutImage() throws Exception {
        MockMultipartFile announcement = new MockMultipartFile("announcement",
                "", MediaType.APPLICATION_JSON_VALUE, "{\"text\": \"some value\"}".getBytes());

        mockMvc.perform(multipart("/announcement")
                    .file(announcement)
                    .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                    .header("X-Auth-Token",
                        "eyJhbGciOiJIUzUxMiJ9." +
                                "eyJqdGkiOiJhIiwiZXhwIjoxNTU5Mzg5NDgxfQ." +
                                "uGAgxH2AumSyak5Ir0nRChRt8HRQGaNvAuFhFeSs5x7HmtwB7qy60yfT5c7J9DBrd0lXXrVMOq-tyjbw3qYPXQ"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void authorizedException() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file",
                "image file name",
                null,
                "test text".getBytes());
        MockMultipartFile announcement = new MockMultipartFile("announcement",
                "", MediaType.APPLICATION_JSON_VALUE, "{\"text\": \"some value\"}".getBytes());

        mockMvc.perform(multipart("/announcement")
                    .file(file)
                    .file(announcement)
                    .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

}
