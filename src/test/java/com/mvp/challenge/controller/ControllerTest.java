package com.mvp.challenge.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mvp.challenge.MvpApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(classes = MvpApplication.class)
@AutoConfigureMockMvc
public class ControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private WebApplicationContext context;

  private final MediaType mediaType = MediaType.APPLICATION_JSON;

  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
  }

  @Test
  public void shouldCreateUser() throws Exception {

    String user =
        """
    {
        "username" : "buyer1",
        "password" : "password",
        "userRoles" : [ {"role" : {"name" : "ROLE_BUYER" , "privileges" : []}} ]
    }
    """;

    mockMvc
        .perform(post("/user").contentType(mediaType).content(user))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(mediaType))
        .andExpect(jsonPath("$.username", is("buyer1")))
        .andExpect(jsonPath("$.deposit", is(0)))
        .andReturn();
  }

  @Test
  public void shouldUpdateDeposit() throws Exception {

    String user =
        """
    {
        "username" : "buyer1",
        "password" : "password",
        "userRoles" : [ {"role" : {"name" : "ROLE_BUYER" , "privileges" : []}} ]
    }
    """;

    mockMvc
        .perform(post("/user").contentType(mediaType).content(user))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(mediaType))
        .andExpect(jsonPath("$.username", is("buyer1")))
        .andExpect(jsonPath("$.deposit", is(0)))
        .andReturn();

    mockMvc
        .perform(
            put("/user/deposit")
                .with(httpBasic("buyer1", "password"))
                .contentType(mediaType)
                .content(
                    """
                    { "value" : 10 }
                    """))
        .andExpect(status().isOk())
        .andExpect(content().contentType(mediaType))
        .andExpect(jsonPath("$.username", is("buyer1")))
        .andExpect(jsonPath("$.deposit", is(10)))
        .andReturn();
  }
}
