package com.crucemelit.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crucemelit.model.User;
import com.crucemelit.service.UserService;

@Controller
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/authenticated/user", method = RequestMethod.GET)
    public @ResponseBody User getAuthenticatedUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            return null;
        }
        return (User) authentication.getPrincipal();
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody void authenticate(HttpServletResponse response) {
    }
}
