package com.thoughtworks.rslist.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RsEvent {
    public RsEvent(String eventName, String keyWord) {
        this.eventName = eventName;
        this.keyWord = keyWord;
    }

    @NotEmpty
    private String eventName;
    @NotEmpty
    private String keyWord;
    @Valid
    @NotNull
    private Integer userId;

//    @JsonIgnore
    public Integer getUserId() {
        return userId;
    }

    @JsonProperty
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
