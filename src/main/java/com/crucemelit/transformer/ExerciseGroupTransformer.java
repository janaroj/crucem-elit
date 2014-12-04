package com.crucemelit.transformer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crucemelit.dto.ExerciseGroupDto;
import com.crucemelit.model.ExerciseGroup;

@Component
public class ExerciseGroupTransformer {

    @Autowired
    ExerciseTransformer exerciseTransformer;

    public ExerciseGroupDto transformToDto(ExerciseGroup exerciseGroup) {
        return new ExerciseGroupDto(exerciseGroup);
    }

    public List<ExerciseGroupDto> transformToDto(List<ExerciseGroup> exerciseGroups) {
        List<ExerciseGroupDto> list = new ArrayList<>();
        for (ExerciseGroup exerciseGroup : exerciseGroups) {
            list.add(transformToDto(exerciseGroup));
        }
        return list;
    }

    public List<ExerciseGroupDto> transformToDtoWithExercises(List<ExerciseGroup> exerciseGroups) {
        List<ExerciseGroupDto> list = new ArrayList<>();
        for (ExerciseGroup exerciseGroup : exerciseGroups) {
            ExerciseGroupDto exerciseGroupDto = transformToDto(exerciseGroup);
            exerciseGroupDto.setExercises(exerciseTransformer.transformToDto(exerciseGroup.getExercises()));
            list.add(exerciseGroupDto);
        }
        return list;
    }
}
