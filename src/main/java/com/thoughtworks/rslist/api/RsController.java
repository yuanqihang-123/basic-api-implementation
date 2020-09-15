package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.dto.RsEvent;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class RsController {
    private List<RsEvent> rsList = getRsList();

    public List<RsEvent> getRsList() {
        ArrayList<RsEvent> rsEvents = new ArrayList<>();
        rsEvents.add(new RsEvent("第一条事件", "无分类"));
        rsEvents.add(new RsEvent("第二条事件", "无分类"));
        rsEvents.add(new RsEvent("第三条事件", "无分类"));
        return rsEvents;
    }

    @GetMapping("/rs/event/{index}")
    public RsEvent getRsEvent(@PathVariable int index) {
        return rsList.get(index - 1);
    }


    @GetMapping("/rs/event")
    public List<RsEvent> getRsEventList(@RequestParam int start, @RequestParam int end) {
        return rsList.subList(start - 1, end);
    }

    @PostMapping("/rs/put")
    List<RsEvent> putRsEvent(@RequestBody(required = false) RsEvent event) {
        rsList.add(event);
        return rsList;
    }

    @GetMapping("/rs/update")
    List<RsEvent> updateEvent(@RequestParam String eventName, @RequestParam String keyWord, @RequestParam Integer index) {
        RsEvent rsEvent = rsList.get(index - 1);
        if (eventName != null) {
            rsEvent.setEventName(eventName);
        }
        if (keyWord != null) {
            rsEvent.setKeyWord(keyWord);
        }
        return rsList;
    }

    @GetMapping("/rs/delete/{index}")
    public List<RsEvent> deleteEvent(@PathVariable int index) {
        rsList.remove(index - 1);
        return rsList;
    }
}
