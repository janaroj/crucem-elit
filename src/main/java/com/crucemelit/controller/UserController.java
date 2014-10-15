package com.crucemelit.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.SneakyThrows;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crucemelit.dto.EmailDto;
import com.crucemelit.model.Gym;
import com.crucemelit.model.User;
import com.crucemelit.service.GymService;
import com.crucemelit.service.UserService;
import com.crucemelit.util.Utility;

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
    public Gym getGym(@PathVariable long id) {
        return gymService.getGym(id);
    }

    @RequestMapping(value = "/gym/join/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> joinGym(@PathVariable long id) {
        userService.joinGym(gymService.getGym(id));
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @RequestMapping(value = "/gym/leave")
    @ResponseBody
    public ResponseEntity<String> leaveGym() {
        userService.leaveGym();
        return new ResponseEntity<String>("Successful", HttpStatus.OK);
    }

    @RequestMapping(value = "/contacts")
    @ResponseBody
    public List<User> getContacts() {
        return userService.getContacts();
    }

    @RequestMapping(value = "/users/{id}")
    @ResponseBody
    public User getContact(@PathVariable long id) {
        return userService.getUser(id);
    }

    @RequestMapping(value = "/profile")
    @ResponseBody
    public User getProfile() {
        return userService.getCurrentUser();
    }

    @RequestMapping(value = "/profile/picture", method = RequestMethod.PUT)
    @ResponseBody
    @SneakyThrows
    public void uploadProfilePicture(HttpServletRequest req) {
        ServletFileUpload upload = new ServletFileUpload();
        FileItemIterator iterator = upload.getItemIterator(req);

        if (iterator.hasNext()) {
            FileItemStream itemStream = iterator.next();
            userService.setProfilePicture(Utility.getBytesFromStream(itemStream.openStream()));
        }
    }

    @RequestMapping(value = "/profile/picture/{id}")
    @ResponseBody
    public String getProfilePicture(@PathVariable long id) {
        return userService.getProfilePicture(id);
    }

    @RequestMapping(value = "/invite", method = RequestMethod.POST)
    @ResponseBody
    public void inviteUser(@RequestBody EmailDto emailDto) {
        userService.sendInviteEmail(emailDto.getEmail());
    }
}
