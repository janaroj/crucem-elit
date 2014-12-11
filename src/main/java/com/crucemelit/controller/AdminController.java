package com.crucemelit.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.crucemelit.domain.Role;
import com.crucemelit.dto.UserDto;
import com.crucemelit.model.ExerciseModel;
import com.crucemelit.model.ExerciseType;
import com.crucemelit.model.Gym;
import com.crucemelit.service.ExerciseModelService;
import com.crucemelit.service.ExerciseTypeService;
import com.crucemelit.service.GymService;
import com.crucemelit.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {

    @Autowired
    private GymService gymService;

    @Autowired
    private ExerciseModelService exerciseModelService;

    @Autowired
    private ExerciseTypeService exerciseTypeService;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = "/exercises", method = RequestMethod.POST)
    public ResponseEntity<String> createExercise(@RequestBody ExerciseModel exerciseModel) {
        exerciseModelService.createExerciseModel(exerciseModel);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @RequestMapping(value = "/exercises/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteExerciseModel(@PathVariable long id) {
        exerciseModelService.deleteExerciseModel(id);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @RequestMapping(value = "/exercises/{id}", method = RequestMethod.PUT)
    @SneakyThrows
    public ResponseEntity<String> updateExercise(@PathVariable long id, HttpServletRequest req) {
        ExerciseModel exerciseModel = exerciseModelService.getExerciseModel(id);
        objectMapper.readerForUpdating(exerciseModel).readValue(req.getReader());
        exerciseModelService.updateExerciseModel(exerciseModel);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @RequestMapping(value = "/exercisetypes")
    public List<ExerciseType> getExerciseTypes() {
        return exerciseTypeService.getExerciseTypes();
    }

    @RequestMapping(value = "/exercisetypes", method = RequestMethod.POST)
    public ResponseEntity<String> createExerciseType(@RequestBody ExerciseType exerciseType) {
        exerciseTypeService.createExerciseType(exerciseType);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @RequestMapping(value = "/exercisetypes/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteExerciseType(@PathVariable long id) {
        exerciseTypeService.deleteExerciseType(id);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @RequestMapping(value = "/exercisetypes/{id}", method = RequestMethod.PUT)
    @SneakyThrows
    public ResponseEntity<String> updateExerciseType(@PathVariable long id, HttpServletRequest req) {
        ExerciseType exerciseType = exerciseTypeService.getExerciseType(id);
        objectMapper.readerForUpdating(exerciseType).readValue(req.getReader());
        exerciseTypeService.updateExerciseType(exerciseType);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @RequestMapping(value = "/gyms", method = RequestMethod.POST)
    public ResponseEntity<String> createGym(@RequestBody Gym gym) {
        gymService.createGym(gym);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @RequestMapping(value = "/gyms/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteGym(@PathVariable long id) {
        gymService.deleteGym(id);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @RequestMapping(value = "/gyms/{id}", method = RequestMethod.PUT)
    @SneakyThrows
    public ResponseEntity<String> updateGym(@PathVariable long id, HttpServletRequest req) {
        Gym gym = gymService.getGym(id);
        objectMapper.readerForUpdating(gym).readValue(req.getReader());
        gymService.updateGym(gym);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @RequestMapping(value = "/users")
    public List<UserDto> getUsers() {
        return userService.getUsersWithAuthInfo();
    }

    @RequestMapping(value = "/users/role/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> changeUserRole(@PathVariable long id) {
        userService.changeUserRole(id);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @RequestMapping(value = "/roles")
    public Role[] getRoles() {
        return Role.values();
    }

}
