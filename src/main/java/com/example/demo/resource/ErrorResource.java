package com.example.demo.resource;

import com.example.demo.IndexController;
import org.springframework.hateoas.EntityModel;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class ErrorResource extends EntityModel<Errors> {
    public static EntityModel<Errors> modelOf(Errors errors){
        EntityModel<Errors> errorResource = EntityModel.of(errors);
        errorResource.add(linkTo(methodOn(IndexController.class).index()).withRel("index"));
        return errorResource;
    }
}
