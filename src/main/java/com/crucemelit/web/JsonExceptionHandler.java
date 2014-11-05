package com.crucemelit.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.crucemelit.util.ResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Called when an exception occurs during request processing. Transforms the exception message into JSON format.
 */
@Component
public class JsonExceptionHandler implements HandlerExceptionResolver {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        try {
            System.out.println("****" + ex.getMessage() + "****"); // FKING HEROKU, AJASIN SEDA ERROR MESSAGET 2H TAGA
            mapper.writeValue(response.getWriter(), new ResponseMessage(ResponseMessage.Type.error, ex.getMessage()));
        }
        catch (IOException e) {
            // give up
            e.printStackTrace();
        }
        return new ModelAndView();
    }
}
