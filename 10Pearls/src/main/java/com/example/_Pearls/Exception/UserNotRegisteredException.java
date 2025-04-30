package com.example._Pearls.Exception;

public class UserNotRegisteredException extends RuntimeException{
    public UserNotRegisteredException(String message){
        super(message);
    }
}
