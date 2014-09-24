package com.crucemelit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crucemelit.model.User;
import com.crucemelit.service.UserService;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @ResponseBody
    public List<User> index() {
        return userService.getUsers();
    }

}
