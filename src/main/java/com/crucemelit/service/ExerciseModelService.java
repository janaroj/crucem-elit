package com.crucemelit.service;

import java.util.List;

import com.crucemelit.model.ExerciseModel;

public interface ExerciseModelService {

    void createExerciseModel(ExerciseModel exerciseModel);

    List<ExerciseModel> getExerciseModels();

    void deleteExerciseModel(long id);

    ExerciseModel getExerciseModel(long id);

    void updateExerciseModel(ExerciseModel exerciseModel);

}
