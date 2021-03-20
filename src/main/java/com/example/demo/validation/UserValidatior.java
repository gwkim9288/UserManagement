package com.example.demo.validation;

import com.example.demo.domain.UserDTO;
import com.example.demo.domain.UserEntity;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserDTO.class.isAssignableFrom(aClass);
    }

    public void validate(Object target, Errors errors) {
        UserDTO userDTO = (UserDTO) target;
        UserEntity user1 =  userRepository.findById(userDTO.getName());
        if (user1 != null) {
            errors.rejectValue("username", "wrongValue", "someone already use this name");
        }
    }
}
