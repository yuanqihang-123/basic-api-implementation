package com.thoughtworks.rslist.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.rslist.dto.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "rs_event")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RsEventEntity {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "name")
    private String eventName;
    private String keyword;
    private Integer voteNum = 0;


    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @OneToMany(mappedBy = "rsEvent",cascade = CascadeType.REMOVE)
    private List<VoteEntity> votes;

    public RsEventEntity(Integer id, String eventName, String keyword, Integer voteNum, @NotNull UserEntity user) {
        this.id = id;
        this.eventName = eventName;
        this.keyword = keyword;
        this.voteNum = voteNum;
        this.user = user;
    }

    @JsonIgnore
    public UserEntity getUser() {
        return user;
    }
    @JsonProperty
    public void setUser(UserEntity user) {
        this.user = user;
    }
    @JsonIgnore
    public List<VoteEntity> getVotes() {
        return votes;
    }
    @JsonProperty
    public void setVotes(List<VoteEntity> votes) {
        this.votes = votes;
    }
}
