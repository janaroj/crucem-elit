package com.crucemelit.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.crucemelit.model.Workout;

@NoArgsConstructor
public @Data class WorkoutDto {

    private long id;

    private Date date;

    private String name;

    private String comment;

    private String gym;

    private String wod;

    private Result result;

    private UserDto user;

    private List<Result> exerciseResults;

    private Boolean completed;

    public WorkoutDto(Workout workout) {
        if (workout != null) {
            this.id = workout.getId();
            this.date = workout.getDate();
            this.name = workout.getName();
            this.comment = workout.getComment();
            this.gym = workout.getGymName();
            this.wod = workout.getWod().getName();
            this.result = workout.getResult();
            this.completed = workout.isCompleted();
        }
    }

}
