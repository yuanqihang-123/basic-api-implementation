package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.dto.RsEvent;
import com.thoughtworks.rslist.dto.User;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    private List<User> userList = InitUserList();

    private List<User> InitUserList() {
        ArrayList<User> Users = new ArrayList<>();
        Users.add(new User("zs1","male",30,"zs1@tw.com","11234567890"));
        Users.add(new User("zs2","male",30,"zs2@tw.com","11234567890"));
        Users.add(new User("zs3","male",30,"zs3@tw.com","11234567890"));
        return Users;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
