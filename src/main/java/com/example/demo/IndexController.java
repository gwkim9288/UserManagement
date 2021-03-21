package com.example.demo;

import com.example.demo.controller.UserController;
import lombok.var;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import sun.misc.Contended;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
public class IndexController {
    @GetMapping("/")
    public RepresentationModel index(){
        var index = new RepresentationModel<>();
        index.add(linkTo(UserController.class).withRel("main"));
        return index;
    }
}
