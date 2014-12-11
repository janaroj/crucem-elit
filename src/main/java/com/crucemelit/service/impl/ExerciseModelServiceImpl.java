package com.crucemelit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crucemelit.model.ExerciseModel;
import com.crucemelit.repository.ExerciseModelRepository;
import com.crucemelit.service.ExerciseModelService;

@Service
public class ExerciseModelServiceImpl implements ExerciseModelService {

    @Autowired
    private ExerciseModelRepository exerciseModelRepository;

    @Override
    @Transactional
    public void createExerciseModel(ExerciseModel exerciseModel) {
        exerciseModelRepository.saveAndFlush(exerciseModel);
    }

    @Override
    public List<ExerciseModel> getExerciseModels() {
        return exerciseModelRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteExerciseModel(long id) {
        exerciseModelRepository.delete(id);
    }

    @Override
    public ExerciseModel getExerciseModel(long id) {
        return exerciseModelRepository.findOne(id);
    }

    @Override
    @Transactional
    public void updateExerciseModel(ExerciseModel exerciseModel) {
        exerciseModelRepository.saveAndFlush(exerciseModel);
    }

}
