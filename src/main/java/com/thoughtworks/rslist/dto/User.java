package com.thoughtworks.rslist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @NotEmpty
    @Size(max = 8)
    @JsonProperty("user_name")
    private String userName;
    @NotEmpty
    @JsonProperty("user_gender")
    private String gender;
    @NotNull
    @Min(18)
    @Max(100)
    @JsonProperty("user_age")
    private Integer age;
    @Email
    @JsonProperty("user_email")
    private String email;
    @NotEmpty
    @Pattern(regexp = "^1\\d{10}$")
    @JsonProperty("user_phone")
    private String phone;
}
