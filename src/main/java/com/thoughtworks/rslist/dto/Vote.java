package com.thoughtworks.rslist.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vote {
    private Integer id;
    private int voteNum;
    private Timestamp voteTime;
    private Integer rsEvent_id;
    private Integer user_id;

    public Vote(Integer id, int voteNum, Timestamp voteTime, Integer user_id) {
        this.id = id;
        this.voteNum = voteNum;
        this.voteTime = voteTime;
        this.user_id = user_id;
    }
}
