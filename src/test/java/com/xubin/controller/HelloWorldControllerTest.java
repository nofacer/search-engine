package com.xubin.controller;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class HelloWorldControllerTest {

  @Autowired
  private MockMvc mvc;

  @BeforeEach
  void setUp() {
  }

  @Test
  public void alwaysPass() {
    assertTrue(true);
  }

  @Test
  void shouldGetHello() throws Exception {
    this.mvc.perform(get("/hello")).andExpect(status().isOk())
        .andExpect(content().string("Hello Spring Boot"));
  }
}
