package com.javatechie.jwt.api.controller;

import com.javatechie.jwt.api.entity.AuthRequest;
import com.javatechie.jwt.api.entity.User;
import com.javatechie.jwt.api.service.UserService;
import com.javatechie.jwt.api.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api")
public class UserController {
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("/users")
    public ResponseEntity<List<User>> users(){
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }
    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody @Valid User user){
        User users = userService.findByUserName(user.getUserName());
        if(users!=null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("username "+user.getUserName()+ " is already present try with another user name ");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return ResponseEntity.ok("successfully registered");
    }
    @PutMapping("/edit/{id}")
    public User update(@RequestBody User userData,@PathVariable int id){
        User existingUser = userService.findById(id);
        if(existingUser ==null ){
            throw new RuntimeException("user is not found with - "+id);
        }
        if(userData.getPassword()!=null){
            existingUser.setPassword(passwordEncoder.encode(userData.getPassword()));
        }
        userService.save(existingUser);
        return existingUser;
    }
    @DeleteMapping("/delete/{userId}")
    public String deleteEmployee(@PathVariable int userId){
        User user = userService.findById(userId);
        if(user==null){
            throw new RuntimeException("employee is not found - "+userId);
        }
        userService.deleteById(userId);
        return "deleted employee id - "+userId;
    }

    @PostMapping("/authenticate")//like login
    public ResponseEntity<String> generateToken(@RequestBody @Valid  AuthRequest authRequest) throws Exception {
        User user = userService.findByUserName(authRequest.getUserName());
        if(user == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(authRequest.getUserName()+" not found");
        }
        String hashedPasswordFromDatabase = user.getPassword();
        String userEnteredPassword = authRequest.getPassword();
        if (!BCrypt.checkpw(userEnteredPassword, hashedPasswordFromDatabase)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("password is incorrect");
        }
        return ResponseEntity.ok(jwtUtil.generateToken(authRequest.getUserName()));
    }
}
