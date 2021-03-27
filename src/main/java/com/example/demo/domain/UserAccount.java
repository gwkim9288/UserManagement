package com.example.demo.domain;

import com.sun.tools.javac.util.List;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
public class UserAccount extends User {
    private UserEntity user;
    public UserAccount(UserEntity user) {
        super(user.getName(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.user = user;
    }
}
