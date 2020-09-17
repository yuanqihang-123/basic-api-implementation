package com.thoughtworks.rslist;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.api.RsController;
import com.thoughtworks.rslist.api.UserController;
import com.thoughtworks.rslist.dto.RsEvent;
import com.thoughtworks.rslist.dto.User;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasKey;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    RsController rsController;
    @Autowired
    UserController userController;


    @Test
    void add_rs_event_and_userName_not_empty_test() throws Exception {
        User user = new User("", "male", 20, "zs@tw.com", "11234567890");
        RsEvent rsEvent = new RsEvent("猪肉涨价了", "经济", user);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rsEvent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void add_rs_event_and_userName_not_more_than_8_test() throws Exception {
        User user = new User("123456789", "male", 20, "zs@tw.com", "11234567890");
        RsEvent rsEvent = new RsEvent("猪肉涨价了", "经济", user);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rsEvent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }


    @Test
    void add_rs_event_and_userName_is_valid_test() throws Exception {
//        User user = new User("zhangsan","male",20,"zs@tw.com","11234567890");
//        RsEvent rsEvent = new RsEvent("猪肉涨价了", "经济",user);
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(rsEvent);
        String json = "{\"eventName\":\"猪肉涨价了\",\"keyWord\":\"经济\",\"user\":{\"user_name\":\"zhangsan\",\"user_gender\":\"male\",\"user_age\":20,\"user_email\":\"zs@tw.com\",\"user_phone\":\"11234567890\"}}";

        mockMvc.perform(post("/rsEvent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void add_rs_event_and_gender_not_empty_test() throws Exception {
        User user = new User("zhangsan", "", 20, "zs@tw.com", "11234567890");
        RsEvent rsEvent = new RsEvent("猪肉涨价了", "经济", user);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rsEvent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void add_rs_event_and_gender_is_valid_test() throws Exception {
//        User user = new User("zhangsan","male",20,"zs@tw.com","11234567890");
//        RsEvent rsEvent = new RsEvent("猪肉涨价了", "经济",user);
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(rsEvent);
        String json = "{\"eventName\":\"猪肉涨价了\",\"keyWord\":\"经济\",\"user\":{\"user_name\":\"zhangsan\",\"user_gender\":\"male\",\"user_age\":20,\"user_email\":\"zs@tw.com\",\"user_phone\":\"11234567890\"}}";

        mockMvc.perform(post("/rsEvent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void add_rs_event_and_age_less_than_18_test() throws Exception {
        User user = new User("zhangsan", "male", 17, "zs@tw.com", "11234567890");
        RsEvent rsEvent = new RsEvent("猪肉涨价了", "经济", user);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rsEvent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void add_rs_event_and_age_max_than_100_test() throws Exception {
        User user = new User("zhangsan", "male", 101, "zs@tw.com", "11234567890");
        RsEvent rsEvent = new RsEvent("猪肉涨价了", "经济", user);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rsEvent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void add_rs_event_and_age_not_empty_test() throws Exception {
        User user = new User("zhangsan", "male", null, "zs@tw.com", "11234567890");
        RsEvent rsEvent = new RsEvent("猪肉涨价了", "经济", user);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rsEvent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void add_rs_event_and_age_is_valide_test() throws Exception {
//        User user = new User("zhangsan","male",30,"zs@tw.com","11234567890");
//        RsEvent rsEvent = new RsEvent("猪肉涨价了", "经济",user);
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(rsEvent);
        String json = "{\"eventName\":\"猪肉涨价了\",\"keyWord\":\"经济\",\"user\":{\"user_name\":\"zhangsan\",\"user_gender\":\"male\",\"user_age\":20,\"user_email\":\"zs@tw.com\",\"user_phone\":\"11234567890\"}}";
        mockMvc.perform(post("/rsEvent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void add_rs_event_and_email_is_valide_test() throws Exception {
//        User user = new User("zhangsan","male",30,"zs@tw.com","11234567890");
//        RsEvent rsEvent = new RsEvent("猪肉涨价了", "经济",user);
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(rsEvent);
        String json = "{\"eventName\":\"猪肉涨价了\",\"keyWord\":\"经济\",\"user\":{\"user_name\":\"zhangsan\",\"user_gender\":\"male\",\"user_age\":20,\"user_email\":\"zs@tw.com\",\"user_phone\":\"11234567890\"}}";

        mockMvc.perform(post("/rsEvent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void add_rs_event_and_email_not_valid_test() throws Exception {
        User user = new User("zhangsan", "male", 30, "zstw.com", "11234567890");
        RsEvent rsEvent = new RsEvent("猪肉涨价了", "经济", user);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rsEvent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void add_rs_event_and_photo_not_start_with_1_test() throws Exception {
        User user = new User("zhangsan", "male", 30, "zs@tw.com", "21234567890");
        RsEvent rsEvent = new RsEvent("猪肉涨价了", "经济", user);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rsEvent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void add_rs_event_and_photo_not_11_numbers_test() throws Exception {
        User user = new User("zhangsan", "male", 30, "zs@tw.com", "1123456789");
        RsEvent rsEvent = new RsEvent("猪肉涨价了", "经济", user);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rsEvent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void add_rs_event_and_photo_is_valid_test() throws Exception {
//        User user = new User("zhangsan","male",30,"zs@tw.com","11234567890");
//        RsEvent rsEvent = new RsEvent("猪肉涨价了", "经济",user);
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(rsEvent);

        String json = "{\"eventName\":\"猪肉涨价了\",\"keyWord\":\"经济\",\"user\":{\"user_name\":\"zhangsan\",\"user_gender\":\"male\",\"user_age\":20,\"user_email\":\"zs@tw.com\",\"user_phone\":\"11234567890\"}}";
        mockMvc.perform(post("/rsEvent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void add_rs_event_and_user_test() throws Exception {
//        User user = new User("zhangsan","male",30,"zs@tw.com","11234567890");
//        RsEvent rsEvent = new RsEvent("猪肉涨价了", "经济",user);
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(rsEvent);
        String json = "{\"eventName\":\"猪肉涨价了\",\"keyWord\":\"经济\",\"user\":{\"user_name\":\"zhangsan\",\"user_gender\":\"male\",\"user_age\":20,\"user_email\":\"zs@tw.com\",\"user_phone\":\"11234567890\"}}";
        mockMvc.perform(post("/rsEvent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
//                .andExpect(jsonPath("$[3].user.user_name",is("zhangsan")));
    }

    @Test
    void get_all_user_test() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]", is(hasKey("user_name"))));
    }

    @Test
    void add_user_when_data_not_valid_test() throws Exception {
        User user = new User();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.error", is("invalid user")));
    }


}
