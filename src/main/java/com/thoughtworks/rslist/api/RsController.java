package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.dto.RsEvent;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    List<RsEvent> addRsEvent(@Valid @RequestBody(required = false) RsEvent event) {
//        if (event.getUser().getUserName())
        rsList.add(event);
        return rsList;
    }

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
