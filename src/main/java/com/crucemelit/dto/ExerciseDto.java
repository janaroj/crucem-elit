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

    private boolean countTime;

    private boolean countWeight;

    private boolean countRepeats;

    private String comment;

    public ExerciseDto(Exercise exercise) {
        if (exercise != null) {
            this.id = exercise.getId();
            this.name = exercise.getName();
            this.exerciseType = exercise.getExerciseType();
            this.countTime = exercise.isCountTime();
            this.countWeight = exercise.isCountWeight();
            this.countRepeats = exercise.isCountRepeats();
            this.comment = exercise.getComment();
        }
    }
}
