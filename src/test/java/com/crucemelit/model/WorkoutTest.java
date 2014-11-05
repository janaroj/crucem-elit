package com.crucemelit.model;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WorkoutTest {
	
	private final String WOD_EXERCISE = "WOD EXERCISE";
	private final String EXERCISE1 = "EXERCISE 1";
	private final String EXERCISE2 = "EXERCISE 2";
	
	@Mock
	private Workout workout;
	
	@Before
	public void setUp() {
		when(workout.getExercises()).thenReturn(getExercises());
	}

	private List<Exercise> getExercises() {
		List<Exercise> exercises = new ArrayList<>();
		
		exercises.add(createExercise(EXERCISE1, false));
		exercises.add(createExercise(WOD_EXERCISE, true));
		exercises.add(createExercise(EXERCISE2, false));
		
		return exercises;
	}

	private Exercise createExercise(String name, boolean isWod) {
		Exercise exercise = new Exercise();
		exercise.setName(name);
		exercise.setWod(isWod);
		
		Record record = new Record();
		record.setResult(25);
		exercise.setRecord(record);
		return exercise;
	}

	@Test
	public void getResultTest() {
		
	}
	
	@Test
	public void getWodTest() {
		assertEquals(WOD_EXERCISE, workout.getWod());
	}
}
