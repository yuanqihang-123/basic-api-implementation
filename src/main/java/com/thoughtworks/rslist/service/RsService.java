package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.dto.RsEvent;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Configuration
public class RsService {

    //    @Autowired
    RsEventRepository rsEventRepository;
    //    @Autowired
    UserRepository userRepository;

    @Bean
    public RsService RsService(RsEventRepository rsEventRepository, UserRepository userRepository) {
        return new RsService(rsEventRepository, userRepository);
    }

    public RsService(RsEventRepository rsEventRepository, UserRepository userRepository) {
        this.rsEventRepository = rsEventRepository;
        this.userRepository = userRepository;
    }


    public RsEventEntity getRsEvent(int id) throws InvalidIndexException {
        Optional<RsEventEntity> result = rsEventRepository.findById(id);
        if (!result.isPresent()) {
            throw new InvalidIndexException();
        }
        return result.get();
    }

    public RsEventEntity patchEvent(RsEvent rsEvent, Integer rsEventId) {

        RsEventEntity rsEventById = rsEventRepository.getById(rsEventId);
        //匹配上了
        if (rsEvent.getEventName() != null) {
            rsEventById.setEventName(rsEvent.getEventName());
        }
        if (rsEvent.getKeyWord() != null) {
            rsEventById.setKeyword(rsEvent.getKeyWord());
        }
        RsEventEntity saveRs = rsEventRepository.save(rsEventById);
        return saveRs;
    }

}
