package com.crucemelit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crucemelit.dto.EmailDto;
import com.crucemelit.model.User;
import com.crucemelit.service.UserService;

@Controller
public class MainController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private AuthController authController;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public User register(@RequestBody User user) {
    	String password = user.getPassword();
        userService.register(user);
        return authController.authenticate(user.getUsername(), password);
    }

    @RequestMapping(value = "/forgot/password", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> forgotPassword(@RequestBody EmailDto emailDto) {
        userService.forgotPassword(emailDto.getEmail());
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

}
