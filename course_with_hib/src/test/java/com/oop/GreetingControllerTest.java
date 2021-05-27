package com.oop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-group-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-group-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class GreetingControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    GreetingController controller;

    @Test
    public void test() throws Exception{
        this.mockMvc.perform(get("/"))
                //при обращении к главной странице ожидаем подстроку Hit Parade
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hit Parade")));
    }

    @Test
    public void groupListTest() throws Exception{
        //добавляем 2 группы, ожидаем на выходе два элемента
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("//tbody[@id='groups-list']/tr").nodeCount(2));
    }
    @Test
    public void groupFilterTest() throws Exception{
        this.mockMvc.perform(post("/filter").param("filter", "Moon"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("//tbody[@id='groups-list']/tr[@data-id='1']").exists());
    }
    @Test
    public void addGroupTest() throws Exception{

        this.mockMvc.perform(post("/save")
                .param("name", "Really")
                .param("year", "2014")
                .param("rating", "25")
                .param("tickets", "670")
                .param("countOfConcerts", "10"))
                .andDo(print());

        this.mockMvc.perform(get("/"))
                //После добавления ожидаем 3 элемента
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("//tbody[@id='groups-list']/tr").nodeCount(3));

    }

    @Test
    public void deleteGroupTest() throws Exception{

        this.mockMvc.perform(post("/1remove"))
                .andDo(print());

        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("//tbody[@id='groups-list']/tr").nodeCount(1));
    }
}