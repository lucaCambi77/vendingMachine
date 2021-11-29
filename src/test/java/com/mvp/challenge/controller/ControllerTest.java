package com.mvp.challenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvp.challenge.MvpApplication;
import com.mvp.challenge.domain.Deposit;
import com.mvp.challenge.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        classes = MvpApplication.class,
        properties = {"spring.redis.embedded=true"})
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private final MediaType mediaType = MediaType.APPLICATION_JSON;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    public void shouldCreateUser() throws Exception {

        User user = new User();
        user.setUserName("user");
        user.setPassword("password");

        mockMvc.perform(post("/user/")
                        .contentType(mediaType)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(mediaType))
                .andExpect(jsonPath("$.userName", is(user.getUsername())))
                .andExpect(jsonPath("$.deposit", is(0)))
                .andReturn();
    }

    @Test
    public void shouldUpdateDeposit() throws Exception {

        String user = "{\n" +
                "    \"userName\" : \"buyer1\",\n" +
                "    \"password\" : \"password\",\n" +
                "    \"userRoles\" : [ {\"role\" : {\"name\" : \"ROLE_BUYER\" , \"privileges\" : []}} ]\n" +
                "}";

        mockMvc.perform(post("/user/")
                        .contentType(mediaType)
                        .content(user))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(mediaType))
                .andExpect(jsonPath("$.userName", is("buyer1")))
                .andExpect(jsonPath("$.deposit", is(0)))
                .andReturn();

        mockMvc.perform(put("/user/deposit").with(httpBasic("buyer1", "password"))
                        .contentType(mediaType)
                        .content(new ObjectMapper().writeValueAsString(new Deposit(10))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(mediaType))
                .andExpect(jsonPath("$.userName", is("buyer1")))
                .andExpect(jsonPath("$.deposit", is(10)))
                .andReturn();
    }
}
