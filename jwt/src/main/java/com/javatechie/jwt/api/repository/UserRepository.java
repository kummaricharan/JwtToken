package com.javatechie.jwt.api.repository;

import com.javatechie.jwt.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUserName(String username);
    @Query("SELECT u FROM User u WHERE u.userName = :username")
    User findByName(@Param("username") String username);
}
