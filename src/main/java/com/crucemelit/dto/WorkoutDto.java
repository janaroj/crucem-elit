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

    private List<ExerciseGroupDto> exerciseGroups;

    public WorkoutDto(Workout workout) {
        if (workout != null) {
            this.id = workout.getId();
            this.date = workout.getDate();
            this.name = workout.getName();
            this.comment = workout.getComment();
            this.gym = workout.getGymName();
            this.wod = workout.getWod().getName();
            this.result = workout.getResult();
        }
    }

    @Override
    public String toString() {
        return "WorkoutDto [id=" + id + ", date=" + date + ", name=" + name + ", comment=" + comment + ", gym=" + gym
                + ", wod=" + wod + ", result=" + result + ", user=" + user + ", exerciseGroups=" + exerciseGroups + "]";
    }

}
