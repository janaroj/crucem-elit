package com.crucemelit.service;

import java.util.List;

import com.crucemelit.dto.ExerciseDto;
import com.crucemelit.model.Exercise;

public interface ExerciseService {

    void createExercise(Exercise exercise);

    List<ExerciseDto> getExercises();

    void deleteExercise(long id);

    Exercise getExercise(long id);

    void updateExercise(Exercise exercise);

}
