package com.crucemelit.exception;


public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super("This username already exists");
    }

}
