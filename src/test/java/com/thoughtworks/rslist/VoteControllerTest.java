package com.thoughtworks.rslist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.dto.Vote;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.entity.VoteEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class VoteControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    VoteRepository voteRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RsEventRepository rsEventRepository;


    @Test
    void voteToRsEventWhileVoteNumLessThanUserVoteNumTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserEntity userEntity = new UserEntity(null, "zhangsan", "male", 30, "zs@tw.com", "11234567890");
        userRepository.save(userEntity);
        userEntity = new UserEntity(null, "lisi", "male", 30, "zs@tw.com", "11234567890");
        userRepository.save(userEntity);
        RsEventEntity rsEventEntity = new RsEventEntity(null, "猪肉涨价了", "经济", 0, userEntity);
        rsEventRepository.save(rsEventEntity);

//        VoteEntity voteEntity = new VoteEntity(5, new Timestamp(System.currentTimeMillis()), userEntity);
        Vote vote = new Vote(null, 5, new Timestamp(System.currentTimeMillis()), 2);
        String json = objectMapper.writeValueAsString(vote);
        mockMvc.perform(post("/rsEvent/3/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
        com.thoughtworks.rslist.entity.VoteEntity voteEntity = voteRepository.findById(4).get();
        assertEquals(5, vote.getVoteNum());
        assertEquals("猪肉涨价了", voteEntity.getRsEvent().getEventName());
        assertEquals("lisi", voteEntity.getUser().getUserName());
    }

    @Test
    void getVoteInStartTimeAndEndTimeTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserEntity userEntity = new UserEntity(null, "zhangsan", "male", 30, "zs@tw.com", "11234567890");
        userRepository.save(userEntity);
        RsEventEntity rsEventEntity1 = new RsEventEntity(null, "猪肉涨价了", "经济", 0, userEntity);
        rsEventRepository.save(rsEventEntity1);
        RsEventEntity rsEventEntity2 = new RsEventEntity(null, "比特币跌了", "比特币经济经济", 0, userEntity);
        rsEventRepository.save(rsEventEntity2);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");//24小时制
        Timestamp timestamp = new Timestamp(simpleDateFormat.parse("2020-09-18 08-00-00").getTime());

        VoteEntity vote1 = new VoteEntity(null,3, timestamp,rsEventEntity1, userEntity);
        voteRepository.save(vote1);

        timestamp = new Timestamp(simpleDateFormat.parse("2020-09-18 11-00-00").getTime());
        VoteEntity vote2 = new VoteEntity(null,4, timestamp,rsEventEntity2, userEntity);
        voteRepository.save(vote2);

        mockMvc.perform(get("/votes")
                .param("startTime","2020-09-18 10-00-00")
                .param("endTime","2020-09-18 23-59-59"))
                .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].voteNum",is(4)))
        .andExpect(jsonPath("$[0].voteTime",is("2020-09-18T03:00:00.000+0000")))
        .andExpect(jsonPath("$[0].rsEvent_id",is(3)))
        .andExpect(jsonPath("$[0].user_id",is(1)));
    }


}
