package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.entity.VoteEntity;
import javafx.util.converter.TimeStringConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<VoteEntity,Integer> {
    @Query("select v from VoteEntity v where v.voteTime >= ?1 and v.voteTime <= ?2")
    public List<VoteEntity> getVotesFromStartAndEndTime(Timestamp start, Timestamp end);
}
