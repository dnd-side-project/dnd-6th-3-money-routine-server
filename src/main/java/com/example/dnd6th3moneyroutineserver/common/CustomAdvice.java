package com.example.dnd6th3moneyroutineserver.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomAdvice {

    @ExceptionHandler(NullPointerException.class)
    public void nullPointerException(NullPointerException e) {
        return;
    }
}
