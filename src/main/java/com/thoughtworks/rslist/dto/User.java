package com.thoughtworks.rslist.dto;

import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @NotEmpty
    @Size(max = 8)
    private String userName;
    @NotEmpty
    private String gender;
    @NotNull
    @Min(18)
    @Max(100)
    private Integer age;
    @Email
    private String email;
    @NotEmpty
    @Pattern(regexp = "^1\\d{10}$")
    private String phone;
}

//"userName": "xiaowang",
//        "age": 19,
//        "gender": "female",
//        "email": "a@thoughtworks.com",
//        "phone": 18888888888

//    名称(不超过8位字符，不能为空)
//  性别（不能为空）
//          年龄（18到100岁之间，不能为空）
//          邮箱（符合邮箱规范）
//          手机号（1开头的11位数字，不能为空）