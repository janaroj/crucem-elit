package com.crucemelit.model;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

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
    private ExerciseGroup wodExerciseGroup;

    @Before
    public void setUp() {
        workout = new Workout();
        wodExerciseGroup = createExerciseGroup(2, WOD_EXERCISE_GROUP, true, WOD_RESULT);
        workout.setExerciseGroups(createExerciseGroups());
    }

    @Test
    public void getResultTest() {
        assertEquals(WOD_RESULT, workout.getResult().getRepeats());
    }

    @Test
    public void getWodTest() {
        assertEquals(wodExerciseGroup, workout.getWod());
    }

    private Set<ExerciseGroup> createExerciseGroups() {
        Set<ExerciseGroup> exerciseGroups = new HashSet<>();
        exerciseGroups.add(createExerciseGroup(1, GROUP1, false, RESULT1));
        exerciseGroups.add(wodExerciseGroup);
        exerciseGroups.add(createExerciseGroup(3, GROUP2, false, RESULT2));
        return exerciseGroups;
    }

    private ExerciseGroup createExerciseGroup(long id, String name, boolean isWod, Double result) {
        ExerciseGroup exerciseGroup = new ExerciseGroup();
        exerciseGroup.setId(id);
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
