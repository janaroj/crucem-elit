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

import com.crucemelit.model.Exercise;
import com.crucemelit.model.ExerciseType;
import com.crucemelit.model.Gym;
import com.crucemelit.service.ExerciseService;
import com.crucemelit.service.ExerciseTypeService;
import com.crucemelit.service.GymService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {

    @Autowired
    private GymService gymService;

    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private ExerciseTypeService exerciseTypeService;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = "/exercises", method = RequestMethod.POST)
    public ResponseEntity<String> createExercise(@RequestBody Exercise exercise) {
        exerciseService.createExercise(exercise);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @RequestMapping(value = "/exercises/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteExercise(@PathVariable long id) {
        exerciseService.deleteExercise(id);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @RequestMapping(value = "/exercises/{id}", method = RequestMethod.PUT)
    @SneakyThrows
    public ResponseEntity<String> updateExercise(@PathVariable long id, HttpServletRequest req) {
        Exercise exercise = exerciseService.getExercise(id);
        objectMapper.readerForUpdating(exercise).readValue(req.getReader());
        exerciseService.updateExercise(exercise);
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

}
