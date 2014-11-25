package com.crucemelit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crucemelit.model.Exercise;
import com.crucemelit.repository.ExerciseRepository;
import com.crucemelit.service.ExerciseService;

@Service
@Transactional
public class ExerciseServiceImpl implements ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Override
    public void createExercise(Exercise exercise) {
        exerciseRepository.saveAndFlush(exercise);
    }

}
