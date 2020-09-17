package com.thoughtworks.rslist.entity;

import com.thoughtworks.rslist.dto.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
