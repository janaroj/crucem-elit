package com.crucemelit.service;

import java.util.List;

import com.crucemelit.dto.WorkoutDto;
import com.crucemelit.model.User;

public interface WorkoutService {

    List<WorkoutDto> getWorkoutsWithResultsDto();

    WorkoutDto getUserWorkoutDto(long id, User user);

    List<WorkoutDto> getUserWorkoutsDto(User user);

    List<WorkoutDto> getUserUpcomingWorkoutsDto(User currentUser);

}
