package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public boolean findById(Integer id) {
        Optional<UserEntity> UserById = userRepository.findById(id);
        if (UserById.isPresent()){
            return true;
        }
        return false;
    }
}
