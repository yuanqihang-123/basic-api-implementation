package com.thoughtworks.rslist.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class UserEntity {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "user_name")
    private String userName;
    private String gender;
    private Integer age;
    private String email;
    private String phone;

    public UserEntity(Integer id, String userName, String gender, Integer age, String email, String phone) {
        this.id = id;
        this.userName = userName;
        this.gender = gender;
        this.age = age;
        this.email = email;
        this.phone = phone;
    }

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    private List<RsEventEntity> rsEvents;
}

