package com.crucemelit.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.crucemelit.model.Exercise;
import com.crucemelit.model.ExerciseGroup;
import com.crucemelit.model.Record;
import com.crucemelit.model.Workout;

@NoArgsConstructor
public @Data class ExerciseGroupDto {

    private long id;

    private String name;

    private Workout workout;

    private List<Exercise> exercises;

    private boolean wod;

    private Record record;

    public ExerciseGroupDto(ExerciseGroup exerciseGroup) {
        if (exerciseGroup != null) {
            this.id = exerciseGroup.getId();
            this.name = exerciseGroup.getName();
            this.workout = exerciseGroup.getWorkout();
            this.exercises = exerciseGroup.getExercises();
            this.wod = exerciseGroup.isWod();
            this.record = exerciseGroup.getRecord();
        }
    }

}
