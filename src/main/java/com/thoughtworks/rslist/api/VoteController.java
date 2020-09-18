package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.dto.Vote;
import com.thoughtworks.rslist.entity.VoteEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class VoteController {
    @Autowired
    UserController userController;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RsEventRepository rsEventRepository;
    @Autowired
    VoteRepository voteRepository;

    @GetMapping("/votes")
    public ResponseEntity<List<Vote>> getVotesInTimeRange(@RequestParam String startTime, @RequestParam String endTime) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");//24小时制
        Timestamp start = new Timestamp(simpleDateFormat.parse(startTime).getTime());
        Timestamp end = new Timestamp(simpleDateFormat.parse(endTime).getTime());
        List<VoteEntity> voteEntities = voteRepository.getVotesFromStartAndEndTime(start, end);
        List<Vote> votes = voteEntities.stream().map(ve -> Vote.builder()
                .id(ve.getId())
                .voteNum(ve.getVoteNum())
                .voteTime(ve.getVoteTime())
                .rsEvent_id(ve.getRsEvent().getId())
                .user_id(ve.getUser().getId())
                .build()).collect(Collectors.toList());

        return ResponseEntity.ok(votes);
    }
}
