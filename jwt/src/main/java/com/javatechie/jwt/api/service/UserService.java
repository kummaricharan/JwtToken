package com.javatechie.jwt.api.service;

import com.javatechie.jwt.api.entity.User;
import com.javatechie.jwt.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class UserService {
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
        return userRepository.findByName(username);
    }

}
