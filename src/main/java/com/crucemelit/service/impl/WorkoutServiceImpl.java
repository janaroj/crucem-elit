package com.crucemelit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crucemelit.dto.WorkoutDto;
import com.crucemelit.repository.WorkoutRepository;
import com.crucemelit.service.WorkoutService;
import com.crucemelit.transformer.WorkoutTransformer;

@Service
@Transactional
public class WorkoutServiceImpl implements WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private WorkoutTransformer workoutTransformer;

    @Override
    public List<WorkoutDto> getWorkoutsWithResultsDto() {
        return workoutTransformer.transformToDtoWithUserInfo(workoutRepository.findByExerciseGroupsRecordNotNull());
    }

}
