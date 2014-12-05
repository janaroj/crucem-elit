package com.crucemelit.controller.advice;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.apache.commons.fileupload.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
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

    @ExceptionHandler({ MessagingException.class, UnsupportedEncodingException.class })
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public ServerException emailMessageException(Exception ex) {
        return new ServerException(ex.getMessage(), ex);
    }

    @ExceptionHandler({ FileUploadException.class, IOException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ServerException IOException(Exception ex) {
        return new ServerException(ex.getMessage(), ex);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ServerException unknownError(Exception e) throws ServerException {
        String error = "unknown.error";

        if (e != null) {
            if (StringUtils.hasText(e.getMessage())) {
                error = e.getMessage();
            }
            else if (e.getCause() != null && StringUtils.hasText(e.getCause().getMessage())) {
                error = e.getCause().getMessage();
            }
        }

        return new ServerException(error, e);
    }

}
