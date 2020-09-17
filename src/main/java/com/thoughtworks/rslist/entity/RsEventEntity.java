package com.thoughtworks.rslist.entity;

import com.thoughtworks.rslist.dto.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "rs_event")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
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
}
