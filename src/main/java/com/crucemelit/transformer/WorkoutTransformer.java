package com.crucemelit.transformer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.crucemelit.dto.WorkoutDto;
import com.crucemelit.model.Workout;

@Component
public class WorkoutTransformer {

    public WorkoutDto transformToDto(Workout workout) {
        return new WorkoutDto(workout);
    }

    public List<WorkoutDto> transformToDto(List<Workout> workouts) {
        List<WorkoutDto> list = new ArrayList<>();
        for (Workout workout : workouts) {
            list.add(transformToDto(workout));
        }
        return list;
    }
}
