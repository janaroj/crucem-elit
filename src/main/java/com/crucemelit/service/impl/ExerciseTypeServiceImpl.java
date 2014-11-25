package com.crucemelit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crucemelit.model.ExerciseType;
import com.crucemelit.repository.ExerciseTypeRepository;
import com.crucemelit.service.ExerciseTypeService;

@Service
@Transactional
public class ExerciseTypeServiceImpl implements ExerciseTypeService {

    @Autowired
    private ExerciseTypeRepository exerciseTypeRepository;

    @Override
    public List<ExerciseType> getExerciseTypes() {
        return exerciseTypeRepository.findAll();
    }

    @Override
    public ExerciseType getExerciseType(long id) {
        return exerciseTypeRepository.findOne(id);
    }

    @Override
    public void createExerciseType(ExerciseType exerciseType) {
        exerciseTypeRepository.saveAndFlush(exerciseType);
    }

    @Override
    public void deleteExerciseType(long id) {
        exerciseTypeRepository.delete(id);
    }

}
