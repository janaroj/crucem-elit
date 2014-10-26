package com.crucemelit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crucemelit.repository.WorkoutRepository;
import com.crucemelit.service.WorkoutService;

@Service
@Transactional
public class WorkoutServiceImpl implements WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;

    /*
     * public List<Workout> getUserWorkouts(long id) { return workoutRepository.getUserWorkouts(id); }
     * 
     * @Override public Workout getWorkout(long id) { Workout workout = workoutRepository.getOne(id); if (workout ==
     * null) { throw new EntityNotFoundException(); } return workout; }
     */

}
