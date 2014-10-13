package com.crucemelit.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.crucemelit.exception.CredentialsExpiredException;
import com.crucemelit.exception.EntityNotFoundException;
import com.crucemelit.exception.ServerException;
import com.crucemelit.exception.UserAlreadyExistsException;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler({ UsernameNotFoundException.class, LockedException.class, BadCredentialsException.class,
            UserAlreadyExistsException.class, CredentialsExpiredException.class })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ServerException authError(Exception ex) {
        return new ServerException(ex.getMessage(), ex);
    }

    @ExceptionHandler({ EntityNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ServerException entityNotFoundError(Exception ex) {
        return new ServerException(ex.getMessage(), ex);
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