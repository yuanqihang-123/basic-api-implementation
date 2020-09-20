package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.dto.RsEvent;
import com.thoughtworks.rslist.dto.Vote;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import com.thoughtworks.rslist.service.RsService;
import com.thoughtworks.rslist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class RsController {
//    @Autowired
    private final UserController userController;
//    @Autowired
    private final UserRepository userRepository;
//    @Autowired
    private final RsEventRepository rsEventRepository;
//    @Autowired
    private final VoteRepository voteRepository;
//    @Autowired
    private final RsService rsService;
//    @Autowired
    private final UserService userService;

    public RsController(UserController userController, UserRepository userRepository, RsEventRepository rsEventRepository, VoteRepository voteRepository, RsService rsService, UserService userService) {
        this.userController = userController;
        this.userRepository = userRepository;
        this.rsEventRepository = rsEventRepository;
        this.voteRepository = voteRepository;
        this.rsService = rsService;
        this.userService = userService;
    }

    private List<RsEvent> rsList = getRsList();

    public List<RsEvent> getRsList() {
        ArrayList<RsEvent> rsEvents = new ArrayList<>();
        rsEvents.add(new RsEvent("第一条事件", "无分类"));
        rsEvents.add(new RsEvent("第二条事件", "无分类"));
        rsEvents.add(new RsEvent("第三条事件", "无分类"));
        return rsEvents;
    }


    @GetMapping("/rsEvent/{id}")
    public ResponseEntity<RsEvent> getRsEvent(@PathVariable int id) throws InvalidIndexException {
        RsEventEntity re = rsService.getRsEvent(id);
        RsEvent rsEvent = RsEvent.builder()
                .eventName(re.getEventName())
                .keyWord(re.getKeyword())
                .userId(re.getUser().getId())
                .build();
        return ResponseEntity.ok(rsEvent);
    }


    @GetMapping("/rsEvents")
    public ResponseEntity<List<RsEvent>> getRsEvents(@RequestParam int start, @RequestParam int end) throws InvalidIndexException {
        return ResponseEntity.ok(rsList.subList(start - 1, end));
    }

    @PostMapping("/rsEvent")
    ResponseEntity<RsEvent> addRsEvent(@Valid @RequestBody(required = false) RsEvent event) {
        Integer userId = event.getUserId();
        UserEntity userEntity = userRepository.getById(userId);
        if (userEntity==null){
            return ResponseEntity.status(400).build();
        }
        RsEventEntity rsEventEntity = RsEventEntity.builder()
                .eventName(event.getEventName())
                .keyword(event.getKeyWord())
                .user(userEntity)
                .voteNum(0)
                .build();
        rsEventRepository.save(rsEventEntity);
        return  ResponseEntity.status(201).header("index",""+rsList.indexOf(event)).body(event);
    }

    @PutMapping("/rsEvent/{index}")
    ResponseEntity<List<RsEvent>> updateRsEvent(@RequestBody RsEvent rsEvent, @PathVariable Integer index) {
        RsEvent event = rsList.get(index - 1);
        if (rsEvent.getEventName() != null) {
            event.setEventName(rsEvent.getEventName());
        }
        if (rsEvent.getKeyWord() != null) {
            event.setKeyWord(rsEvent.getKeyWord());
        }
        return ResponseEntity.ok(rsList);
    }

    @DeleteMapping("/rsEvent/{index}")
    public ResponseEntity<List<RsEvent>> deleteEvent(@PathVariable int index) {
        rsList.remove(index - 1);
        return ResponseEntity.ok(rsList);
    }

    @PatchMapping("/rsEvent/{rsEventId}")
    public ResponseEntity<RsEvent> patchEvent(@Valid @RequestBody RsEvent rsEvent, @PathVariable Integer rsEventId) {
        Integer userId = rsEvent.getUserId();
        boolean isPresentUser = userService.findById(userId);
        if (!isPresentUser){
            return ResponseEntity.status(400).build();
        }

        RsEventEntity re = rsService.patchEvent(rsEvent, rsEventId);
        RsEvent rsEvent1 = rsEvent.builder()
                .userId(re.getId())
                .keyWord(re.getKeyword())
                .eventName(re.getEventName())
                .build();
        return ResponseEntity.ok(rsEvent1);
    }

    @Transactional
    @PostMapping("/rsEvent/{rsEventId}/vote")
    ResponseEntity<RsEvent> addRsEvent(@RequestBody(required = false) Vote vote, @PathVariable Integer rsEventId) {
        int voteNum = vote.getVoteNum();
        UserEntity userEntity = userRepository.findById(vote.getUser_id()).get();
        if (userEntity.getVoteNum()<voteNum){
            return ResponseEntity.status(400).build();
        }
        //开始投票

        RsEventEntity rsEventRepositoryById = rsEventRepository.getById(rsEventId);
        vote.setRsEvent_id(rsEventId);
        com.thoughtworks.rslist.entity.VoteEntity voteEntity = com.thoughtworks.rslist.entity.VoteEntity.builder()
                .voteNum(vote.getVoteNum())
                .voteTime(vote.getVoteTime())
                .rsEvent(rsEventRepositoryById)
                .user(userEntity)
                .build();
        voteRepository.save(voteEntity);
        rsEventRepositoryById.setVoteNum(voteNum+rsEventRepositoryById.getVoteNum());
        rsEventRepository.save(rsEventRepositoryById);
        userEntity.setVoteNum(userEntity.getVoteNum()-voteNum);
        userRepository.save(userEntity);
        return  ResponseEntity.status(201).build();
    }
}
