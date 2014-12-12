package com.crucemelit.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.crucemelit.model.ExerciseGroup;

@NoArgsConstructor
public @Data class ExerciseGroupDto {

    private long id;

    private String name;

    private List<ExerciseDto> exercises;

    private boolean wod;

    private Result record;

    public ExerciseGroupDto(ExerciseGroup exerciseGroup) {
        if (exerciseGroup != null) {
            this.id = exerciseGroup.getId();
            this.name = exerciseGroup.getName();
            this.wod = exerciseGroup.isWod();
            this.record = exerciseGroup.getResult();
        }
    }

}
