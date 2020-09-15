package com.thoughtworks.rslist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.api.RsController;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.awt.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
class RsListApplicationTests {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    RsController rsController;

    @Test
    void getRsTest() throws Exception {
//    {"eventName":"第一条事件","keyWord":"无分类"}
        mockMvc.perform(get("/rs/event/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.eventName").value("第一条事件"));
    }

    @Test
    void getRsEventListTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(rsController.getRsList());


        mockMvc.perform(get("/rs/event?start=1&end=3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].eventName",is("第一条事件")))
                .andExpect(jsonPath("$[0].keyWord",is("无分类")))
                .andExpect(jsonPath("$[1].eventName",is("第二条事件")))
                .andExpect(jsonPath("$[1].keyWord",is("无分类")))
                .andExpect(jsonPath("$[2].eventName",is("第三条事件")))
                .andExpect(jsonPath("$[2].keyWord",is("无分类")));
    }

    @Test
    void putRsEventTest() throws Exception {
        mockMvc.perform(post("/rs/put")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"eventName\": \"猪肉涨价了\",\"keyWord\": \"经济\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(4)));
    }


}
