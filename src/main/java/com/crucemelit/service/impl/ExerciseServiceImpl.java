package com.crucemelit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crucemelit.dto.ExerciseDto;
import com.crucemelit.model.Exercise;
import com.crucemelit.repository.ExerciseRepository;
import com.crucemelit.service.ExerciseService;
import com.crucemelit.transformer.ExerciseTransformer;

@Service
@Transactional
public class ExerciseServiceImpl implements ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private ExerciseTransformer exerciseTransformer;

    @Override
    public void createExercise(Exercise exercise) {
        exerciseRepository.saveAndFlush(exercise);
    }

    @Override
    public List<ExerciseDto> getExercises() {
        return exerciseTransformer.transformToDto(exerciseRepository.findAll());
    }

    @Override
    public void deleteExercise(long id) {
        exerciseRepository.delete(id);
    }

    @Override
    public Exercise getExercise(long id) {
        return exerciseRepository.findOne(id);
    }

    @Override
    public void updateExercise(Exercise exercise) {
        exerciseRepository.saveAndFlush(exercise);
    }

}
