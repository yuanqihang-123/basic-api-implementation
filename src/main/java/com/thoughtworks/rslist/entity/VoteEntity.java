package com.thoughtworks.rslist.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table(name = "vote")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VoteEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private int voteNum;
    private Timestamp voteTime;

    public VoteEntity(int voteNum, Timestamp voteTime, UserEntity user) {
        this.voteNum = voteNum;
        this.voteTime = voteTime;
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "rsEvent_id")
    private RsEventEntity rsEvent;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
