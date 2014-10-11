package com.crucemelit.exception;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@SuppressWarnings("serial")
@JsonPropertyOrder({ "message", "key", "cause" })
public class ServerException extends Exception {

    @Getter
    @Setter
    private String key;

    public ServerException(String message) {
        super(message);
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerException(String message, String key, Throwable cause) {
        super(message, cause);
        this.key = key;
    }

    public ServerException(Throwable cause) {
        super(cause);
    }

}
