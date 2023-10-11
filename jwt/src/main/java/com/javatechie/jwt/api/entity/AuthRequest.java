package com.javatechie.jwt.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @NotNull(message = "username not be empty")
    private String userName;
//    @Email(message = "enter correct mail")
//    @NotNull(message = "mail must be not null")
//    private String email;
    @NotNull(message = "enter correct password")
    private String password;
}
