package com.javatechie.jwt.api.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class ValidationApplication {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleInvalidFieldsAndArguments(MethodArgumentNotValidException exception){
        Map<String,String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
        {
            errorMap.put(error.getField(),error.getDefaultMessage());
        });
        return errorMap;
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UserNotFoundException.class)
    public Map<String,String> handleBusinessException(UserNotFoundException exception) {
        Map<String,String> errorMap=new HashMap<>();
        errorMap.put("Error Message",exception.getMessage());
        return errorMap;
    }
}
