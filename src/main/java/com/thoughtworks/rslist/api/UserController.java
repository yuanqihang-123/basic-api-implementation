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


    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<CommentError> exceptionHand(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(400).body(new CommentError("invalid user"));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserEntity> getUser(@PathVariable Integer id) {
        UserEntity userEntity = userRepository.getOne(id);
        return ResponseEntity.ok(userEntity);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<UserEntity> deleteUser(@PathVariable Integer id) {
        UserEntity user = userRepository.getOne(id);
        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user")
    public ResponseEntity<UserEntity> addUserEntity(@Valid @RequestBody User user, BindingResult re) throws MethodArgumentNotValidException {
        UserEntity userEntity = new UserEntity(null, user.getUserName(), user.getGender(), user.getAge(), user.getEmail(), user.getPhone());
        if (re.getAllErrors().size() != 0) {
            throw new MethodArgumentNotValidException(null, re);
        }
        userRepository.save(userEntity);
        return ResponseEntity.created(null).body(userEntity);
    }

}
