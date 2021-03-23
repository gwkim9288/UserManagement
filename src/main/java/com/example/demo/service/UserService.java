package com.example.demo.service;

import com.example.demo.domain.UserDTO;
import com.example.demo.domain.UserEntity;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {


    private ModelMapper modelMapper = new ModelMapper();
    private final UserRepository userRepo;

    public UserEntity getUserById(int id){
        if(!userRepo.existsById(id))
            return null;
        return userRepo.findById(id).get();
    }

    public boolean createUser(UserDTO userDTO){

        UserEntity mappingUser = modelMapper.map(userDTO, UserEntity.class);
        userRepo.save(mappingUser);
        return true;
    }

    public boolean deleteUserById(int id){

        if(!userRepo.existsById(id))
            return false;
        userRepo.deleteById(id);
        return true;
    }

    public UserEntity updateUser(int id, UserDTO userDTO){
        if(!userRepo.existsById(id))
            return null;
        UserEntity user = userRepo.findById(id).get();
        user.update(userDTO.getName(), userDTO.getEmail());
        userRepo.save(user);
        //UserDTO mappingUserDTO = modelMapper.map(user,UserDTO.class);
        return user;
    }

    public List<UserEntity> getUserList(){
        return userRepo.findAll();
    }
}
