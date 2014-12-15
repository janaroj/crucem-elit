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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.crucemelit.domain.Gender;
import com.crucemelit.dto.CommentDto;
import com.crucemelit.dto.EmailDto;
import com.crucemelit.dto.GymDto;
import com.crucemelit.dto.Suggestion;
import com.crucemelit.dto.UserDto;
import com.crucemelit.dto.WorkoutDto;
import com.crucemelit.model.Comment;
import com.crucemelit.model.ExerciseModel;
import com.crucemelit.model.User;
import com.crucemelit.model.Workout;
import com.crucemelit.service.ExerciseModelService;
import com.crucemelit.service.GymService;
import com.crucemelit.service.RecordService;
import com.crucemelit.service.SearchService;
import com.crucemelit.service.UserService;
import com.crucemelit.service.WorkoutService;
import com.crucemelit.util.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
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

    @Autowired
    private ExerciseModelService exerciseModelService;

    @Autowired
    private RecordService recordService;

    @RequestMapping(value = "/exercises")
    public List<ExerciseModel> getExerciseModels() {
        return exerciseModelService.getExerciseModels();
    }

    @RequestMapping(value = "/gyms")
    public List<GymDto> getGyms() {
        return gymService.getGymDtos();
    }

    @RequestMapping(value = "/gyms/{id}")
    public GymDto getGym(@PathVariable long id) {
        return gymService.getGymDto(id);
    }

    @RequestMapping(value = "/search/{term}")
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
    public ResponseEntity<String> joinGym(@PathVariable long id) {
        userService.joinGym(gymService.getGym(id));
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @RequestMapping(value = "/gym/leave")
    public ResponseEntity<String> leaveGym() {
        userService.leaveGym();
        return new ResponseEntity<String>("Successful", HttpStatus.OK);
    }

    @RequestMapping(value = "/contacts")
    public List<UserDto> getContacts() {
        return userService.getContactsDto();
    }

    @RequestMapping(value = "/friends")
    public List<UserDto> getFriends() {
        return userService.getFriendsDto();
    }

    @RequestMapping(value = "/friends/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> removeFriend(@PathVariable long id) {
        userService.removeFriend(id);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @RequestMapping(value = "/friends/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> addFriend(@PathVariable long id) {
        userService.addFriend(id);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @RequestMapping(value = "/users/{id}")
    public UserDto getContact(@PathVariable long id) {
        return userService.getUserDto(id);
    }

    @RequestMapping(value = "/profile")
    public UserDto getProfile() {
        return userService.getCurrentUserDto();
    }

    @RequestMapping(value = "/profile/picture", method = RequestMethod.PUT)
    public String uploadProfilePicture(HttpServletRequest req) {
        return Utility.uploadPicture(req, userService);
    }

    @RequestMapping(value = "/profile/picture/{id}")
    public String getProfilePicture(@PathVariable long id) {
        return userService.getPicture(id);
    }

    @RequestMapping(value = "/invite", method = RequestMethod.POST)
    public void inviteUser(@RequestBody EmailDto emailDto) {
        userService.sendInviteEmail(emailDto.getEmail());
    }

    @RequestMapping(value = "/workouts")
    public List<WorkoutDto> getUserWorkouts() {
        return workoutService.getUserWorkoutsDto(userService.getCurrentUser());
    }

    @RequestMapping(value = "/workouts/records")
    public List<WorkoutDto> getUserWorkoutsExerciseResults() {
        return workoutService.getWorkoutsWithExerciseGroupsDto();
    }

    @RequestMapping(value = "/workouts/upcoming")
    public List<WorkoutDto> getUpcomingUserWorkouts() {
        return workoutService.getUserUpcomingWorkoutsDto(userService.getCurrentUser());
    }

    @RequestMapping(value = "/workouts", method = RequestMethod.POST)
    public ResponseEntity<String> createWorkout(@RequestBody Workout workout) {
        userService.createUserWorkout(workout);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @RequestMapping(value = "/workouts/{id}", method = RequestMethod.PUT)
    @SneakyThrows
    public ResponseEntity<String> updateWorkout(@PathVariable long id, HttpServletRequest req) {
        Workout workout = workoutService.getWorkoutById(id);
        objectMapper.readerForUpdating(workout).readValue(req.getReader());
        workoutService.updateWorkout(workout);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @RequestMapping(value = "/workouts/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteWorkoutById(@PathVariable long id) {
        userService.deleteUserWorkoutById(id);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @RequestMapping(value = "/workouts/results")
    public List<WorkoutDto> getWorkouts() {
        return workoutService.getWorkoutsWithResultsDto();
    }

    @RequestMapping(value = "/workouts/{id}")
    public WorkoutDto getWorkout(@PathVariable long id) {
        return workoutService.getUserWorkoutDto(id, userService.getCurrentUser());
    }

    @RequestMapping(value = "/genders")
    public Gender[] getGenders() {
        return Gender.values();
    }

    @RequestMapping(value = "/gym/{id}/picture", method = RequestMethod.PUT)
    @SneakyThrows
    public String uploadGymPicture(HttpServletRequest req, @PathVariable long id) {
        return Utility.uploadPicture(req, gymService, id);
    }

    @RequestMapping(value = "/gym/picture/{id}")
    public String getGymPicture(@PathVariable long id) {
        return gymService.getPicture(id);
    }

    @RequestMapping(value = "/gym/comments/{id}")
    public List<CommentDto> getGymComments(@PathVariable long id) {
        return gymService.getGymComments(id);
    }

    @RequestMapping(value = "/update/user", method = RequestMethod.PUT)
    @SneakyThrows
    public ResponseEntity<String> updateUser(HttpServletRequest req) {
        User user = userService.getCurrentUser();
        objectMapper.readerForUpdating(user).readValue(req.getReader());
        userService.updateUser(user);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @RequestMapping(value = "/comments", method = RequestMethod.POST)
    public ResponseEntity<Long> createComment(@RequestBody Comment comment) {
        userService.createUserComment(comment);
        return new ResponseEntity<Long>(comment.getId(), HttpStatus.OK);
    }

    @RequestMapping(value = "/comments/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteComment(@PathVariable long id) {
        userService.deleteUserCommentById(id);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

}
