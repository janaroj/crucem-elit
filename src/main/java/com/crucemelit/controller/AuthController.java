package com.crucemelit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crucemelit.dto.UserDto;
import com.crucemelit.service.UserService;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/check")
    public UserDto check() {
        return userService.getCurrentUserWithAuthInfo();
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, produces = "application/json")
    public UserDto authenticate(@RequestParam("username") String username, @RequestParam("password") String password) {
        return userService.authenticate(username, password);
    }
}
