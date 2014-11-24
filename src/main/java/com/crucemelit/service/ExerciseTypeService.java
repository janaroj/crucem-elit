package com.crucemelit.service;

import java.util.List;

import com.crucemelit.model.ExerciseType;

public interface ExerciseTypeService {

    List<ExerciseType> getExerciseTypes();

    ExerciseType getExerciseType(long id);

    void createExerciseType(ExerciseType exerciseType);

    void deleteExerciseType(long id);

    void updateExerciseType(ExerciseType exerciseType);

}
