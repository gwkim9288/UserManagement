package com.example.demo.controller;

import com.example.demo.domain.UserEntity;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.resource.UserResourceAssembler;
import com.example.demo.service.UserService;
import com.example.demo.validation.UserValidator;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.hibernate.EntityMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.validation.*;

import javax.swing.text.html.parser.Entity;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/users")
public class UserController{

    private final UserService userService;
    private final UserValidator userValidator;

    @GetMapping("{user_id}")
    public ResponseEntity getUserByID(@PathVariable int user_id){
        UserEntity user = userService.getUserById(user_id);
        if(user==null){
            return null;
        }
        //Hateaos 적용하여 resource받아오기
        UserResourceAssembler userResourceAssembler = new UserResourceAssembler();
        EntityModel<UserEntity> userResource = userResourceAssembler.toModel(user);

        //add list uri link
        userResource.add(linkTo(methodOn(UserController.class).getList()).withRel("getList"));

        return ResponseEntity.ok(userResource);
    }

    @GetMapping("/list")
    public List<UserEntity> getList(){
        return userService.getList();
    }

    @PostMapping("/")
    public ResponseEntity<UserEntity> createUser(@RequestBody @Valid UserEntity user){
        userService.createUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{user_id}")
    public boolean deleteUser(@PathVariable int user_id){
        return userService.deleteUserById(user_id);
    }

    @PostMapping("/{user_id}")
    public void updateUser(@RequestBody UserEntity user, @PathVariable int user_id){
        if(userService.updateUser(user_id,user)) {
            throw new UserNotFoundException(String.format("User Not Found : id = %d", user_id));
        }
    }
}
