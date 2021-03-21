package com.example.demo.service;

import com.example.demo.domain.UserDTO;
import com.example.demo.domain.UserEntity;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {


    private final ModelMapper modelMapper;
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

    public boolean updateUser(int id, UserEntity user){
        if(!userRepo.existsById(id))
            return false;
        userRepo.save(user);
        return true;
    }

    public List<UserEntity> getUserList(){
        return userRepo.findAll();
    }
}
