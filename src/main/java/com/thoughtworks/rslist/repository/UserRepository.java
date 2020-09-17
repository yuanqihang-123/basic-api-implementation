package com.thoughtworks.rslist.repository;

import com.sun.xml.bind.v2.model.core.ID;
import com.thoughtworks.rslist.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity getById(Integer userId);
}
