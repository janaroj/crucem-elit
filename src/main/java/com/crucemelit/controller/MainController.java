package com.crucemelit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.crucemelit.dto.EmailDto;
import com.crucemelit.model.User;
import com.crucemelit.service.UserService;

@RestController
public class MainController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<String> register(@RequestBody User user) {
        userService.register(user);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @RequestMapping(value = "/forgot/password", method = RequestMethod.POST)
    public ResponseEntity<String> forgotPassword(@RequestBody EmailDto emailDto) {
        userService.forgotPassword(emailDto.getEmail());
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @RequestMapping(value = "/ping")
    public ResponseEntity<String> ping() {
        userService.getUserDto(1); // Checks connection to database
        return new ResponseEntity<String>("UP", HttpStatus.OK);
    }

}
