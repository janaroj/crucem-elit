package com.crucemelit.transformer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.crucemelit.dto.ExerciseDto;
import com.crucemelit.model.Exercise;

@Component
public class ExerciseTransformer {

    public ExerciseDto transformToDto(Exercise exercise) {
        return new ExerciseDto(exercise);
    }

    public List<ExerciseDto> transformToDto(List<Exercise> exercises) {
        List<ExerciseDto> list = new ArrayList<>();
        for (Exercise exercise : exercises) {
            list.add(transformToDto(exercise));
        }
        return list;
    }

}
