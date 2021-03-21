package com.example.demo.resource;

import com.example.demo.controller.UserController;
import com.example.demo.domain.UserEntity;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.web.util.UriComponents;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class UserResource extends EntityModel<UserEntity> {


    public static EntityModel<UserEntity> modelOf(UserEntity entity) {
        EntityModel<UserEntity> userEntityModel = EntityModel.of(entity);
        userEntityModel.add(linkTo(UserController.class).slash(entity.getId()).withSelfRel());
        return userEntityModel;
    }
}
