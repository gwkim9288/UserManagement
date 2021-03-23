package com.example.demo;

import com.example.demo.config.SessionUser;
import com.example.demo.controller.UserController;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final HttpSession httpSession;
        @GetMapping("/")
        public RepresentationModel index(){
        var index = new RepresentationModel<>();
        index.add(linkTo(UserController.class).withRel("main"));
        return index;
    }
//    @GetMapping("/")
//    public String index(Model model) {
//
//        SessionUser user = (SessionUser) httpSession.getAttribute("user");
//
//        if (user != null) {
//            model.addAttribute("userName", user.getName());
//        }
//
//        return "index";
//    }
}
