package com.crucemelit.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class WorkoutTest {

    private final String GROUP1 = "GROUP 1";
    private final String GROUP2 = "GROUP 2";
    private final String WOD_EXERCISE_GROUP = "WOD";
    private final Double WOD_RESULT = 11d;
    private final Double RESULT1 = 25d;
    private final Double RESULT2 = null;

    private Workout workout;

    @Before
    public void setUp() {
        workout = new Workout();
        workout.setExerciseGroups(createExerciseGroups());

    }

    @Test
    public void getResultTest() {
        assertEquals(WOD_RESULT, workout.getResult().getRepeats());
    }

    @Test
    public void getWodTest() {
        assertEquals(WOD_EXERCISE_GROUP, workout.getWod());
    }

    private List<ExerciseGroup> createExerciseGroups() {
        List<ExerciseGroup> exerciseGroups = new ArrayList<>();
        exerciseGroups.add(createExerciseGroup(GROUP1, false, RESULT1));
        exerciseGroups.add(createExerciseGroup(WOD_EXERCISE_GROUP, true, WOD_RESULT));
        exerciseGroups.add(createExerciseGroup(GROUP2, false, RESULT2));
        return exerciseGroups;
    }

    private ExerciseGroup createExerciseGroup(String name, boolean isWod, Double result) {
        ExerciseGroup exerciseGroup = new ExerciseGroup();
        exerciseGroup.setName(name);
        exerciseGroup.setWod(isWod);
        if (result != null) {
            exerciseGroup.setRecord(createRecord(result));
        }

        return exerciseGroup;
    }

    private Record createRecord(Double result) {
        Record record = new Record();
        record.setRepeats(result);
        return record;
    }

}
