package com.crucemelit.service;

import java.util.List;

import com.crucemelit.dto.WorkoutDto;
import com.crucemelit.model.User;
import com.crucemelit.model.Workout;

public interface WorkoutService {

    List<WorkoutDto> getWorkoutsWithResultsDto();

    WorkoutDto getUserWorkoutDto(long id, User user);

    List<WorkoutDto> getUserWorkoutsDto(User user);

    void updateWorkout(Workout workout);

    Workout getWorkoutById(long id);

    List<WorkoutDto> getUserUpcomingWorkoutsDto(User currentUser);

    List<WorkoutDto> getWorkoutsWithExerciseGroupsDto();

    List<WorkoutDto> getWorkoutSuggestionsForUser(User currentUser);

}
