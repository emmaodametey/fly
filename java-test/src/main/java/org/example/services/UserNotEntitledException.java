package org.example.services;

public class UserNotEntitledException extends Exception{

    public UserNotEntitledException(String message) {
        super(message);
    }
}
