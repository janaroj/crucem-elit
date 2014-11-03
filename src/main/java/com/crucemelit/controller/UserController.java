package com.crucemelit.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import lombok.AllArgsConstructor;
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
import com.crucemelit.dto.Suggestion;
import com.crucemelit.model.Gym;
import com.crucemelit.model.Result;
import com.crucemelit.model.User;
import com.crucemelit.service.GymService;
import com.crucemelit.service.ResultService;
import com.crucemelit.service.SearchService;
import com.crucemelit.service.UserService;
import com.crucemelit.util.Utility;

@Controller
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private GymService gymService;

    @Autowired
    private UserService userService;

    @Autowired
    private ResultService resultService;

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

    @RequestMapping(value = "/search/{term}")
    @ResponseBody
    @SneakyThrows
    public List<Suggestion> search(@PathVariable final String term) {
        final List<Suggestion> suggestions = new ArrayList<>();

        @AllArgsConstructor
        class SearchTask implements Runnable {
            private SearchService service;

            @Override
            public void run() {
                suggestions.addAll(service.search(term));
            }
        }
        ;

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(new SearchTask(userService));
        executorService.execute(new SearchTask(gymService));
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);

        return suggestions;
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

    @RequestMapping(value = "/records")
    @ResponseBody
    public List<Result> getResults() {
        return resultService.getResults();
    }

}
