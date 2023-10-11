package com.javatechie.jwt.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull(message = "username not empty")
    private String userName;
//    @Min(2)
//    @Max(6)
    @NotNull(message = "password is not empty")
    private String password;
    @Email(message = "invalid email")
    @NotNull(message = "email is not null")
    private String email;
    @NotNull(message = "please enter role")
    private String role;
}
