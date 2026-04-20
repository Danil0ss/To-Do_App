package com.example.To_Do_App.exception;

public class UserAccessDeniedException extends RuntimeException{
    public UserAccessDeniedException(String message){
        super(message);
    }
}
