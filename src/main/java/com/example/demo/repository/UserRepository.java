package com.example.demo.repository;

import com.example.demo.domain.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.nio.channels.FileChannel;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {

    Optional<UserEntity> findByEmail(String email);

    UserEntity findByName(String name);
}
