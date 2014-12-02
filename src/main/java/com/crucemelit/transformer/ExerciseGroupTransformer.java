package com.crucemelit.transformer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.crucemelit.dto.ExerciseGroupDto;
import com.crucemelit.model.ExerciseGroup;

@Component
public class ExerciseGroupTransformer {

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
}
