package com.crucemelit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crucemelit.model.ExerciseModel;
import com.crucemelit.repository.ExerciseModelRepository;
import com.crucemelit.service.ExerciseModelService;

@Service
@Transactional
public class ExerciseModelServiceImpl implements ExerciseModelService {

    @Autowired
    private ExerciseModelRepository exerciseModelRepository;

    @Override
    public void createExerciseModel(ExerciseModel exerciseModel) {
        exerciseModelRepository.saveAndFlush(exerciseModel);
    }

    @Override
    public List<ExerciseModel> getExerciseModels() {
        return exerciseModelRepository.findAllWithTypes();
    }

    @Override
    public void deleteExerciseModel(long id) {
        exerciseModelRepository.delete(id);
    }

    @Override
    public ExerciseModel getExerciseModel(long id) {
        return exerciseModelRepository.findOne(id);
    }

    @Override
    public void updateExerciseModel(ExerciseModel exerciseModel) {
        exerciseModelRepository.saveAndFlush(exerciseModel);
    }

}
