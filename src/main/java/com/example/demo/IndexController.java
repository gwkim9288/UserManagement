package com.example.demo;

import com.example.demo.controller.UserController;
import com.example.demo.domain.Role;
import com.example.demo.domain.UserAccount;
import com.example.demo.domain.UserDTO;
import com.example.demo.domain.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.resource.UserResource;
import com.example.demo.service.UserService;
import com.sun.tools.javac.util.List;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;

import java.net.URI;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final HttpSession httpSession;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

        @GetMapping("/")
        public RepresentationModel index(){
        RepresentationModel index = new RepresentationModel<>();
        index.add(linkTo(UserController.class).withRel("main"));
        return index;
    }

    @GetMapping("/login")
    public void login(@RequestBody Map<String,String> loginSet) {
        UserEntity user = userRepository.findByName((String)loginSet.get("id"));
        if(user == null)
            throw new IllegalArgumentException("There is unvalid id.");

        if (!passwordEncoder.matches((String)loginSet.get("password"), user.getPassword())) {
            throw new IllegalArgumentException("There is unvalid password.");
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new UserAccount(user),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRoleKey()))
        );
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    @PostMapping("/account")
    public ResponseEntity<Object> createUser(@RequestBody UserDTO userDTO){
        try {
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userDTO.setRole(Role.USER);
            UserEntity user = userService.createUser(userDTO);
            URI location = linkTo(UserController.class).slash(user.getId()).toUri();
            EntityModel<UserEntity> userResource = UserResource.modelOf(user);
            userResource.add(linkTo(UserController.class).withRel("get-list"));
            return ResponseEntity.created(location).body(userResource);
        }catch(Exception ex) {
            return ResponseEntity.badRequest().build();
        }
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
