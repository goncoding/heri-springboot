package com.doosan.heritage.controller.rest;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest
@WebMvcTest
class HelloRestControllerTest {


    @Autowired
    private MockMvc mvc;

    @Test
    public void _테스트() throws Exception{

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("hello"));
    }

    @Test
    public void dto_return_테스트() throws Exception{

        mvc.perform(get("/hello/dto")
                        .param("name","hello")
                        .param("amount",String.valueOf(1000))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("hello")))
                .andExpect(jsonPath("$.amount", is(1000)));
    }





}