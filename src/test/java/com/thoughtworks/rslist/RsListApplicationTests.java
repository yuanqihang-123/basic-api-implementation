package com.thoughtworks.rslist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.api.RsController;
import com.thoughtworks.rslist.dto.RsEvent;
import com.thoughtworks.rslist.dto.User;
import javassist.compiler.ast.Keyword;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        mockMvc.perform(get("/rsEvent/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventName").value("第一条事件"))
                .andExpect(jsonPath("$",not(hasKey("user"))));
    }

    @Test
    void getRsEventsTest() throws Exception {
        mockMvc.perform(get("/rsEvents?start=1&end=3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].eventName", is("第一条事件")))
                .andExpect(jsonPath("$[0].keyWord", is("无分类")))
                .andExpect(jsonPath("$[1].eventName", is("第二条事件")))
                .andExpect(jsonPath("$[1].keyWord", is("无分类")))
                .andExpect(jsonPath("$[2].eventName", is("第三条事件")))
                .andExpect(jsonPath("$[2].keyWord", is("无分类")))
                .andExpect(jsonPath("$[0]", not(hasKey("user"))));
    }

    @Test
    void addRsEventTest() throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//        User user = new User("zhangsan", "male", 20, "zs@tw.com", "11234567890");
//        RsEvent rsEvent = new RsEvent("猪肉涨价了", "经济", user);
//        String userJson = objectMapper.writeValueAsString(rsEvent);
        String json = "{\"eventName\":\"猪肉涨价了\",\"keyWord\":\"经济\",\"user\":{\"user_name\":\"zhangsan\",\"user_gender\":\"male\",\"user_age\":20,\"user_email\":\"zs@tw.com\",\"user_phone\":\"11234567890\"}}";


        mockMvc.perform(post("/rsEvent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("index","3"))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[3].eventName",is("猪肉涨价了")))
                .andExpect(jsonPath("$[3].keyWord",is("经济")));
    }

    @Test
    void updateEventTest() throws Exception {
        RsEvent rsEvent = new RsEvent("猪肉降价了", "经济");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(rsEvent);
        mockMvc.perform(put("/rsEvent/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].eventName", is("猪肉降价了")))
                .andExpect(jsonPath("$[2].keyWord", is("经济")));

    }

    @Test
    void deleteEventTest() throws Exception {
        mockMvc.perform(delete("/rsEvent/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].eventName", is("第三条事件")))
                .andExpect(jsonPath("$[1].keyWord", is("无分类")));
    }


}
