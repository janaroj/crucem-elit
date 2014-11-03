package com.crucemelit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crucemelit.model.Workout;
import com.crucemelit.repository.WorkoutRepository;
import com.crucemelit.service.WorkoutService;

@Service
@Transactional
public class WorkoutServiceImpl implements WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Override
    public List<Workout> getWorkouts() {
        return workoutRepository.findAll();
    }

}
