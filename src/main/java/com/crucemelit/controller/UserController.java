package com.crucemelit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crucemelit.model.Gym;
import com.crucemelit.service.GymService;

@Controller
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private GymService gymService;

    @RequestMapping(value = "/gyms")
    @ResponseBody
    public List<Gym> getGyms() {
        return gymService.getGyms();
    }

}
