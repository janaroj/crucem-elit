package com.crucemelit.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.crucemelit.model.Exercise;
import com.crucemelit.model.ExerciseType;

@NoArgsConstructor
public @Data class ExerciseDto {

    private long id;

    private String name;

    private ExerciseType exerciseType;

    private Boolean countTime;

    private Boolean countWeight;

    private Boolean countRepeats;

    private String comment;

    public ExerciseDto(Exercise exercise) {
        if (exercise != null) {
            this.id = exercise.getId();
            this.name = exercise.getName();
            this.exerciseType = exercise.getExerciseType();
            this.countTime = exercise.getCountTime();
            this.countWeight = exercise.getCountWeight();
            this.countRepeats = exercise.getCountRepeats();
            this.comment = exercise.getComment();
        }
    }
}
