package com.crucemelit.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
//TODO REDO
public class WorkoutTest {

    private final String WOD_EXERCISE = "WOD EXERCISE";
    private final String EXERCISE1 = "EXERCISE 1";
    private final String EXERCISE2 = "EXERCISE 2";
    private final Integer WOD_RESULT = 11;
    private final Integer RESULT1 = 25;
    private final Integer RESULT2 = null;

    private Workout workout;

    @Before
    public void setUp() {
        workout = new Workout();
    }

    private List<Exercise> createExercises() {
        List<Exercise> exercises = new ArrayList<>();

        exercises.add(createExercise(EXERCISE1, false, RESULT1));
        exercises.add(createExercise(WOD_EXERCISE, true, WOD_RESULT));
        exercises.add(createExercise(EXERCISE2, false, RESULT2));

        return exercises;
    }

    private Exercise createExercise(String name, boolean isWod, Integer result) {
        Exercise exercise = new Exercise();
        exercise.setName(name);

        if (result != null) {
            exercise.setRecord(createRecord(result));
        }

        return exercise;
    }

    private Record createRecord(Integer result) {
        Record record = new Record();
        return record;
    }

}
