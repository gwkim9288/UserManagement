package com.example.demo.controller;

import com.example.demo.domain.UserDTO;
import com.example.demo.domain.UserEntity;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.resource.ErrorResource;
import com.example.demo.resource.UserResource;
import com.example.demo.service.UserService;
import com.example.demo.validation.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.validation.*;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;
    private final UserValidator userValidator;
    private final UserRepository userRepository;

    @GetMapping("{user_id}")
    public ResponseEntity getUserByID(@PathVariable int user_id) {
        UserEntity user = userService.getUserById(user_id);
        if (user == null) {
            return null;
        }
        //Hateaos 적용하여 resource받아오기
        EntityModel<UserEntity> userResource = UserResource.modelOf(user);

        //add list uri link
        userResource.add(linkTo(methodOn(UserController.class)).withRel("getList"));

        return ResponseEntity.ok(userResource);
    }

    @GetMapping
    public ResponseEntity getUserList(Pageable pageable, PagedResourcesAssembler<UserEntity> assembler) {

        Page<UserEntity> paged = userRepository.findAll(pageable);
        PagedModel<EntityModel<UserEntity>> userResource = assembler.toModel(paged, i -> UserResource.modelOf(i));
        userResource.add(linkTo(UserController.class).withRel("create-user"));
        return ResponseEntity.ok(userResource);
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody @Valid UserDTO userDTO, Errors errors) {
        if (errors.hasErrors()) {
            EntityModel<Errors> error1 = ErrorResource.modelOf(errors);
            return ResponseEntity.badRequest().body(error1);
        }
        userValidator.validate(userDTO, errors);
        if (errors.hasErrors()) {
            EntityModel<Errors> error2 = ErrorResource.modelOf(errors);
            return ResponseEntity.badRequest().body(error2);
        }
        userService.createUser(userDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(userDTO.getName()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{user_id}")
    public boolean deleteUser(@PathVariable int user_id) {
        return userService.deleteUserById(user_id);
    }

    @PostMapping("/{user_id}")
    public void updateUser(@RequestBody UserEntity user, @PathVariable int user_id) {
        if (userService.updateUser(user_id, user)) {
            throw new UserNotFoundException(String.format("User Not Found : id = %d", user_id));
        }
    }
}
