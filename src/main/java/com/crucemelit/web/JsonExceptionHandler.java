package com.crucemelit.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * Called when an exception occurs during request processing. Transforms the exception message into JSON format.
 */
@Component
public class JsonExceptionHandler implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        System.out.println("****" + ex.getMessage() + "****"); // FKING HEROKU, AJASIN SEDA ERROR MESSAGET 2H TAGA
        return new ModelAndView();
    }
}
