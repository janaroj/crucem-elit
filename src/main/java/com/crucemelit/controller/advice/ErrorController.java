package com.crucemelit.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.crucemelit.exception.ServerException;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler({ UsernameNotFoundException.class, BadCredentialsException.class })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ServerException usernameError(Exception ex) {
        return new ServerException(ex);
    }

    /*
     * @ResponseBody
     * 
     * @ExceptionHandler(Exception.class)
     * 
     * @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) public String unknownError(Exception e) throws ServerException
     * {
     * 
     * String error = "Unknown error occurred.";
     * 
     * if (e != null) { if (StringUtils.hasText(e.getMessage())) { error = e.getMessage(); } else if (e.getCause() !=
     * null && StringUtils.hasText(e.getCause().getMessage())) { error = e.getCause().getMessage(); } }
     * 
     * return error; }
     */

}
