package com.example.demo.controller;

import com.example.demo.domain.UserEntity;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.resource.UserResourceAssembler;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.hibernate.EntityMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.swing.text.html.parser.Entity;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
public class UserController{

    private final UserService userService;

    @GetMapping("/users/{user_id}/get")
    public ResponseEntity getUserByID(@PathVariable int user_id){
        UserEntity user = userService.getUserById(user_id);
        if(user==null){
            return null;
        }
        //Hateaos 적용하여 resource받아오기
        UserResourceAssembler userResourceAssembler = new UserResourceAssembler();
        EntityModel<UserEntity> userResource = userResourceAssembler.toModel(user);
        userResource.add(linkTo(UserController.class).withRel("getUserByID"));

        return ResponseEntity.ok(userResourceAssembler.toModel(user));
    }

    @GetMapping("/users/list")
    public List<UserEntity> getList(){
        return userService.getList();
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
