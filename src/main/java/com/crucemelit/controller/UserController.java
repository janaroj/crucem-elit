package com.crucemelit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crucemelit.model.Gym;
import com.crucemelit.model.User;
import com.crucemelit.service.GymService;
import com.crucemelit.service.UserService;

@Controller
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private GymService gymService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/gyms")
    @ResponseBody
    public List<Gym> getGyms() {
        return gymService.getGyms();
    }

    @RequestMapping(value = "/gyms/{id}")
    @ResponseBody
    public Gym getGym(@RequestBody long id) {
        return gymService.getGym(id);
    }

    @RequestMapping(value = "/contacts")
    @ResponseBody
    public List<User> getContacts() {
        return userService.getContacts();
    }

    @RequestMapping(value = "/users/{id}")
    @ResponseBody
    public User getContact(@RequestBody long id) {
        return userService.getUser(id);
    }
}
