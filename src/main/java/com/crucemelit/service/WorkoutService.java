package com.crucemelit.service;

import java.util.List;

import com.crucemelit.dto.WorkoutDto;

public interface WorkoutService {

    List<WorkoutDto> getWorkoutsWithResultsDto();

}
