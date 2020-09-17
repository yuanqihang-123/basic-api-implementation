package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.dto.RsEvent;
import com.thoughtworks.rslist.dto.User;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.exception.CommentError;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    private List<User> userList = InitUserList();

    @Autowired
    UserRepository userRepository;

    private List<User> InitUserList() {
        ArrayList<User> Users = new ArrayList<>();
        Users.add(new User("zs1", "male", 30, "zs1@tw.com", "11234567890"));
        Users.add(new User("zs2", "male", 30, "zs2@tw.com", "11234567890"));
        Users.add(new User("zs3", "male", 30, "zs3@tw.com", "11234567890"));
        return Users;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userList);
    }

//    @PostMapping("/user")
//    public ResponseEntity<List<User>> addUser(@Valid @RequestBody User user, BindingResult re) throws MethodArgumentNotValidException {
//        if (re.getAllErrors().size() != 0) {
//            throw new MethodArgumentNotValidException(null,re);
//        }
//        userList.add(user);
//        return ResponseEntity.ok(userList);
//    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<CommentError> exceptionHand(MethodArgumentNotValidException ex){
        return ResponseEntity.status(400).body(new CommentError("invalid user"));
    }

    @PostMapping("/user")
    public void addUserEntity(@Valid @RequestBody User user, BindingResult re) throws MethodArgumentNotValidException {
        UserEntity userEntity = new UserEntity(null, user.getUserName(), user.getGender(), user.getAge(), user.getEmail(), user.getPhone());
//        UserEntity userEntity = UserEntity.builder()
//                .userName(user.getUserName())
//                .gender(user.getGender())
//                .age(user.getAge())
//                .email(user.getEmail())
//                .phone(user.getPhone())
//                .build();
        if (re.getAllErrors().size() != 0) {
            throw new MethodArgumentNotValidException(null,re);
        }
        userRepository.save(userEntity);
    }

}
