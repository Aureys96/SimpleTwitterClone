package com.example.sweatter;

import com.example.sweatter.controller.MainController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("admin")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql", "/messages-list-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/messages-list-after.sql","/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class MainControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MainController controller;

    @Test
    public void mainPageTest() throws Exception {
        this.mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='navbarSupportedContent']/div").string("admin"));
    }

    @Test
    public void messageListTest() throws Exception {
        this.mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='message-list']/div").nodeCount(4));
    }

    @Test
    public void filterMessageTest() throws Exception {
        this.mockMvc.perform(get("/main").param("filter", "my-tag"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='message-list']/div").nodeCount(2))
                .andExpect(xpath("//*[@id='message-list']/div[@data-id=1]").exists())
                .andExpect(xpath("//*[@id='message-list']/div[@data-id=3]").exists());
    }

    @Test
    public void addMessageToListTest() throws Exception {
        MockHttpServletRequestBuilder multipart = multipart("/main")
                .file("file", "123".getBytes())
                .param("text", "fourth")
                .param("tag", "new one")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='message-list']/div").nodeCount(5))
                .andExpect(xpath("//*[@id='message-list']/div[@data-id='4']").exists())
                .andExpect(xpath("//*[@id='message-list']/div[@data-id='4']/div/span").string("fourth"))
                .andExpect(xpath("//*[@id='message-list']/div[@data-id='4']/div/i").string("#another"));
    }
}
