package com.crucemelit.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crucemelit.dto.UserDto;
import com.crucemelit.dto.WorkoutDto;
import com.crucemelit.model.Workout;

@Component
public class WorkoutTransformer {

    @Autowired
    private ExerciseGroupTransformer exerciseGroupTransformer;

    public WorkoutDto transformToDto(Workout workout) {
        return new WorkoutDto(workout);
    }

    public List<WorkoutDto> transformToDto(Collection<Workout> workouts) {
        List<WorkoutDto> list = new ArrayList<>();
        for (Workout workout : workouts) {
            list.add(transformToDto(workout));
        }
        return list;
    }

    public WorkoutDto transformToDtoWithUserInfo(Workout workout) {
        WorkoutDto workoutDto = transformToDto(workout);
        workoutDto.setUser(new UserDto(workout.getUser()));
        return workoutDto;
    }

    public List<WorkoutDto> transformToDtoWithUserInfo(Collection<Workout> workouts) {
        List<WorkoutDto> list = new ArrayList<>();
        for (Workout workout : workouts) {
            list.add(transformToDtoWithUserInfo(workout));
        }
        return list;
    }

    public WorkoutDto transformToDtoWithExerciseGroups(Workout workout) {
        WorkoutDto workoutDto = transformToDto(workout);
        workoutDto.setExerciseGroups(exerciseGroupTransformer.transformToDtoWithExercises(workout.getExerciseGroups()));
        workoutDto.setUser(new UserDto(workout.getUser()));
        return workoutDto;
    }

    public List<WorkoutDto> transformToDtoWithExerciseGroups(Collection<Workout> workouts) {
        List<WorkoutDto> list = new ArrayList<>();
        for (Workout workout : workouts) {
            WorkoutDto workoutDto = transformToDtoWithExerciseGroups(workout);
            list.add(workoutDto);
        }
        return list;
    }

}
