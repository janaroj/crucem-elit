package com.crucemelit.exception;

public class CredentialsExpiredException extends RuntimeException {

    public CredentialsExpiredException() {
        super("Credentials expired");
    }

}
