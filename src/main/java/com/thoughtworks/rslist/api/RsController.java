package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.dto.RsEvent;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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


    @GetMapping("/rsEvent/{index}")
    public RsEvent getRsEvent(@PathVariable int index) {
        return rsList.get(index - 1);
    }


    @GetMapping("/rsEvents")
    public List<RsEvent> getRsEvents(@RequestParam int start, @RequestParam int end) {
        return rsList.subList(start - 1, end);
    }

    @PostMapping("/rsEvent")
    List<RsEvent> addRsEvent(@RequestBody(required = false) RsEvent event) {
        rsList.add(event);
        return rsList;
    }

    //    @GetMapping("/rs/update")
//    List<RsEvent> updateEvent(@RequestParam String eventName, @RequestParam String keyWord, @RequestParam Integer index) {
//        RsEvent rsEvent = rsList.get(index - 1);
//        if (eventName != null) {
//            rsEvent.setEventName(eventName);
//        }
//        if (keyWord != null) {
//            rsEvent.setKeyWord(keyWord);
//        }
//        return rsList;
//    }
    @PutMapping("/rsEvent/{index}")
    List<RsEvent> updateRsEvent(@RequestBody RsEvent rsEvent, @PathVariable Integer index) {
        RsEvent event = rsList.get(index - 1);
        if (rsEvent.getEventName() != null) {
            event.setEventName(rsEvent.getEventName());
        }
        if (rsEvent.getKeyWord() != null) {
            event.setKeyWord(rsEvent.getKeyWord());
        }
        return rsList;
    }

    @DeleteMapping("/rsEvent/{index}")
    public List<RsEvent> deleteEvent(@PathVariable int index) {
        rsList.remove(index - 1);
        return rsList;
    }
}
