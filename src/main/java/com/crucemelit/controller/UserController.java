package com.crucemelit.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

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

import com.crucemelit.domain.Gender;
import com.crucemelit.dto.EmailDto;
import com.crucemelit.dto.GymDto;
import com.crucemelit.dto.Suggestion;
import com.crucemelit.dto.UserDto;
import com.crucemelit.dto.WorkoutDto;
import com.crucemelit.model.User;
import com.crucemelit.service.GymService;
import com.crucemelit.service.SearchService;
import com.crucemelit.service.UserService;
import com.crucemelit.service.WorkoutService;
import com.crucemelit.util.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private GymService gymService;

    @Autowired
    private UserService userService;

    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = "/gyms")
    @ResponseBody
    public List<GymDto> getGyms() {
        return gymService.getGymsDto();
    }

    @RequestMapping(value = "/gyms/{id}")
    @ResponseBody
    public GymDto getGym(@PathVariable long id) {
        return gymService.getGymDto(id);
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
    public List<UserDto> getContacts() {
        return userService.getContactsDto();
    }

    @RequestMapping(value = "/friends")
    @ResponseBody
    public List<UserDto> getFriends() {
        return userService.getFriendsDto();
    }

    @RequestMapping(value = "/friends/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<String> removeFriend(@PathVariable long id) {
        userService.removeFriend(id);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @RequestMapping(value = "/friends/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> addFriend(@PathVariable long id) {
        userService.addFriend(id);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @RequestMapping(value = "/users/{id}")
    @ResponseBody
    public UserDto getContact(@PathVariable long id) {
        return userService.getUserDto(id);
    }

    @RequestMapping(value = "/profile")
    @ResponseBody
    public UserDto getProfile() {
        return userService.getCurrentUserDto();
    }

    @RequestMapping(value = "/profile/picture", method = RequestMethod.PUT)
    @ResponseBody
    public String uploadProfilePicture(HttpServletRequest req) {
        return Utility.uploadPicture(req, userService);
    }

    @RequestMapping(value = "/profile/picture/{id}")
    @ResponseBody
    public String getProfilePicture(@PathVariable long id) {
        return userService.getPicture(id);
    }

    @RequestMapping(value = "/invite", method = RequestMethod.POST)
    @ResponseBody
    public void inviteUser(@RequestBody EmailDto emailDto) {
        userService.sendInviteEmail(emailDto.getEmail());
    }

    @RequestMapping(value = "/workouts")
    @ResponseBody
    public List<WorkoutDto> getUserWorkouts() {
        return userService.getUserWorkoutsDto();
    }

    @RequestMapping(value = "/workouts/results")
    @ResponseBody
    public List<WorkoutDto> getWorkouts() {
        return workoutService.getWorkoutsWithResultsDto();
    }

    @RequestMapping(value = "/genders")
    @ResponseBody
    public Gender[] getGenders() {
        return Gender.values();
    }

    @RequestMapping(value = "/gym/{id}/picture", method = RequestMethod.PUT)
    @ResponseBody
    @SneakyThrows
    public String uploadGymPicture(HttpServletRequest req, @PathVariable long id) {
        return Utility.uploadPicture(req, gymService, id);
    }

    @RequestMapping(value = "/gym/picture/{id}")
    @ResponseBody
    public String getGymPicture(@PathVariable long id) {
        return gymService.getPicture(id);
    }

    @RequestMapping(value = "/update/user", method = RequestMethod.PUT)
    @ResponseBody
    @SneakyThrows
    public ResponseEntity<String> updateUser(HttpServletRequest req) {
        User user = userService.getCurrentUser();
        objectMapper.readerForUpdating(user).readValue(req.getReader());
        userService.updateUser(user);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

}
