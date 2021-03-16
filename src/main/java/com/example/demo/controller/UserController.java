package com.example.demo.controller;

import com.example.demo.domain.UserEntity;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class UserController{

    @Autowired
    UserService userService;

    @GetMapping("/users/{user_id}/get")
    public UserEntity getUserByID(@PathVariable int user_id){
        if(userService.getUserById(user_id)==null){
            return null;
        }
        return userService.getUserById(user_id);
    }

    @PostMapping("/users/post")
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user){
        userService.createUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{user_id}/delete")
    public boolean deleteUser(@PathVariable int user_id){
        return userService.deleteUserById(user_id);
    }

    @PostMapping("/users/{user_id}/updata")
    public void updateUser(@RequestBody UserEntity user, @PathVariable int user_id){
        if(userService.updateUser(user_id,user)) {
            throw new UserNotFoundException(String.format("User Not Found : id = %d", user_id));
        }
    }
}
