package com.example.demo.service;

import com.example.demo.domain.UserEntity;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepo;

    public UserEntity getUserById(int id){
        if(!userRepo.existsById(id))
            return null;
        return userRepo.findById(id).get();
    }

    public boolean createUser(UserEntity user){
        userRepo.save(user);
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
}
