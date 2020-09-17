package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.dto.RsEvent;
import com.thoughtworks.rslist.dto.User;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.entity.VoteEntity;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RsController {
    @Autowired
    UserController userController;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RsEventRepository rsEventRepository;
    @Autowired
    VoteRepository voteRepository;

    private List<RsEvent> rsList = getRsList();

    public List<RsEvent> getRsList() {
        ArrayList<RsEvent> rsEvents = new ArrayList<>();
        rsEvents.add(new RsEvent("第一条事件", "无分类"));
        rsEvents.add(new RsEvent("第二条事件", "无分类"));
        rsEvents.add(new RsEvent("第三条事件", "无分类"));
        return rsEvents;
    }


    @GetMapping("/rsEvent/{id}")
    public ResponseEntity<RsEventEntity> getRsEvent(@PathVariable int id) throws InvalidIndexException {
        RsEventEntity rsEventRepositoryById = rsEventRepository.getById(id);
        if (rsEventRepositoryById==null){
            throw new InvalidIndexException();
        }
        return ResponseEntity.ok(rsEventRepositoryById);
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
        UserEntity userEntity = userRepository.getById(userId);
        if (userEntity==null){
            //说明userid和rsevent不匹配
            return ResponseEntity.status(400).build();
        }

        RsEventEntity rsEventById = rsEventRepository.getById(rsEventId);
        //匹配上了
        if (rsEvent.getEventName()!=null){
            rsEventById.setEventName(rsEvent.getEventName());
        }
        if (rsEvent.getKeyWord()!=null){
            rsEventById.setKeyword(rsEvent.getKeyWord());
        }
        rsEventRepository.save(rsEventById);
        return ResponseEntity.ok(rsEvent);
    }

    @PostMapping("/rsEvent/{rsEventId}/vote")
    ResponseEntity<RsEvent> addRsEvent(@RequestBody(required = false) VoteEntity vote,@PathVariable Integer rsEventId) {
        int voteNum = vote.getVoteNum();
        UserEntity userEntity = vote.getUser();
        if (userEntity.getVoteNum()<voteNum){
            return ResponseEntity.status(400).build();
        }
        //开始投票

        RsEventEntity rsEventRepositoryById = rsEventRepository.getById(rsEventId);
        vote.setRsEvent(rsEventRepositoryById);
        voteRepository.save(vote);
        rsEventRepositoryById.setVoteNum(voteNum+rsEventRepositoryById.getVoteNum());
        rsEventRepository.save(rsEventRepositoryById);
        userEntity.setVoteNum(userEntity.getVoteNum()-voteNum);
        userRepository.save(userEntity);
        return  ResponseEntity.status(201).build();
    }
}
