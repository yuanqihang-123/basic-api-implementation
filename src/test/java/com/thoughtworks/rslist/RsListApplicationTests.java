package com.thoughtworks.rslist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.api.RsController;
import com.thoughtworks.rslist.dto.User;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.entity.VoteEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
class RsListApplicationTests {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    RsController rsController;
    @Autowired
    RsEventRepository rsEventRepository;
    @Autowired
    UserRepository userRepository;


    /*@Test
    void getRsTest() throws Exception {
//    {"eventName":"第一条事件","keyWord":"无分类"}
        mockMvc.perform(get("/rsEvent/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventName").value("第一条事件"))
                .andExpect(jsonPath("$", not(hasKey("user"))));
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
                .andExpect(header().string("index", "3"))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[3].eventName", is("猪肉涨价了")))
                .andExpect(jsonPath("$[3].keyWord", is("经济")));
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

    @Test
    void getEventsStartAndEndTest() throws Exception {
        mockMvc.perform(get("/rsEvents?start=0&end=4"))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.error", is("invalid request param")));
    }

    @Test
    void getEventIndexTest() throws Exception {
        mockMvc.perform(get("/rsEvent/4"))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.error", is("invalid index")));
    }

    @Test
    void addEventValidTest() throws Exception {
        RsEvent rsEvent = new RsEvent("", "经济");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(rsEvent);
        mockMvc.perform(post("/rsEvent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.error", is("invalid param")));


    }
*/
    @Test
    void addEventToRepositoryWhenUserNotRegisterTest() throws Exception {
        String json = "{\"eventName\":\"猪肉涨价了\",\"keyWord\":\"经济\",\"userId\":1}";
        mockMvc.perform(post("/rsEvent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(400));
    }

    @Test
    void addEventToRepositoryWhenUserRegisterTest() throws Exception {
        UserEntity userEntity = new UserEntity(null, "zhangsan", "male", 30, "zs@tw.com", "11234567890");
        userRepository.save(userEntity);

        String json = "{\"eventName\":\"猪肉涨价了\",\"keyWord\":\"经济\",\"userId\":1}";
        mockMvc.perform(post("/rsEvent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());

        RsEventEntity rsEventEntity = rsEventRepository.getByEventName("猪肉涨价了");
        assertEquals(2, rsEventEntity.getId());
        assertEquals("猪肉涨价了", rsEventEntity.getEventName());
        assertEquals("经济", rsEventEntity.getKeyword());

    }

    @Test
    void updateEventWhenUserIdIsNotValidTest() throws Exception {
        String json = "{\"eventName\":\"猪肉涨价了\",\"keyWord\":\"经济\"}";
        mockMvc.perform(patch("/rsEvent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateEventWhenUserIdIsNotMatchWithRsEventTest() throws Exception {
        String json = "{\"eventName\":\"猪肉涨价了\",\"keyWord\":\"经济\",\"userId\":1}";
        mockMvc.perform(patch("/rsEvent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(400));
    }

    @Test
    void updateEventWhenUserIdIsMatchWithRsEventTest() throws Exception {
        User user = new User("zhangsan", "male", 30, "zs@tw.com", "11234567890");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(201));
        List<UserEntity> userEntitys = userRepository.findAll();
        assertEquals("zhangsan", userEntitys.get(0).getUserName());
        assertEquals(1, userEntitys.size());

        String rsjson = "{\"eventName\":\"猪肉涨价了\",\"keyWord\":\"经济\",\"userId\":1}";
        mockMvc.perform(post("/rsEvent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(rsjson))
                .andExpect(status().isCreated());
        RsEventEntity rsEventEntity = rsEventRepository.getByEventName("猪肉涨价了");
        assertEquals(2, rsEventEntity.getId());
        assertEquals("猪肉涨价了", rsEventEntity.getEventName());
        assertEquals("经济", rsEventEntity.getKeyword());

        String json1 = "{\"eventName\":\"鸡肉涨价了\",\"keyWord\":\"经济1\",\"userId\":1}";
        mockMvc.perform(patch("/rsEvent/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json1))
                .andExpect(status().isOk());
        RsEventEntity rsEventById = rsEventRepository.getById(2);
        assertEquals("鸡肉涨价了",rsEventById.getEventName());
        assertEquals("经济1",rsEventById.getKeyword());
    }

    @Test
    void voteToRsEventWhileVoteNumMoreThanUserVoteNumTest() throws Exception {
        User user = new User("zhangsan", "male", 30, "zs@tw.com", "11234567890");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(201));
        User user1 = new User("lisi", "male", 30, "zs@tw.com", "11234567890");
        json = objectMapper.writeValueAsString(user1);
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(201));

        String rsjson = "{\"eventName\":\"猪肉涨价了\",\"keyWord\":\"经济\",\"userId\":1}";
        mockMvc.perform(post("/rsEvent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(rsjson))
                .andExpect(status().isCreated());

        UserEntity userEntity = UserEntity.builder()
                .id(2)
                .userName(user1.getUserName())
                .gender(user1.getGender())
                .age(user1.getAge())
                .email(user1.getEmail())
                .phone(user.getPhone())
                .voteNum(10)
                .build();
        VoteEntity voteEntity = new VoteEntity(11, new Timestamp(System.currentTimeMillis()), userEntity);
        String voteJson = objectMapper.writeValueAsString(voteEntity);
        mockMvc.perform(post("/rsEvent/3/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(voteJson))
                .andExpect(status().is(400));
    }


}
