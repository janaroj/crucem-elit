package com.crucemelit.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.crucemelit.model.Exercise;
import com.crucemelit.model.ExerciseModel;

@NoArgsConstructor
public @Data class ExerciseDto {

    private long id;

    private ExerciseModel exerciseModel;

    private Result result;

    public ExerciseDto(Exercise exercise) {
        if (exercise != null) {
            this.id = exercise.getId();
            this.exerciseModel = exercise.getExerciseModel();
            this.result = exercise.getResult();
        }
    }
}
