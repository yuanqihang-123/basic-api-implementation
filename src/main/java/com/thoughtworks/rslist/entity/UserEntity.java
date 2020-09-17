package com.thoughtworks.rslist.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

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
}

