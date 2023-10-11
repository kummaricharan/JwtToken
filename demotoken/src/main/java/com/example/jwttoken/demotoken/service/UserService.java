package com.example.jwttoken.demotoken.service;

import com.example.jwttoken.demotoken.entity.User;
import com.example.jwttoken.demotoken.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService  {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> findAll(){
        return userRepository.findAll();
    }
    public User findById(int id) {
        Optional<User> result = userRepository.findById(id);
        User user = null;
        if(result.isPresent()){
            user = result.get();
        }
        else{
            throw new RuntimeException("did not found id - "+id);
        }
        return user;
    }
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }
    @Transactional
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }
    public User findByUserName(String username) {
        return userRepository.findByUserName(username);
    }
}
