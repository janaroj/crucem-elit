package com.crucemelit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crucemelit.dto.WorkoutDto;
import com.crucemelit.model.ExerciseGroup;
import com.crucemelit.model.User;
import com.crucemelit.model.Workout;
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

    @Override
    public WorkoutDto getUserWorkoutDto(long id, User user) {
        return workoutTransformer.transformToDtoWithExerciseGroups((workoutRepository.findOneByIdAndUser(id, user)));
    }

    @Override
    public List<WorkoutDto> getWorkoutsWithExerciseGroupsDto() {
        return workoutTransformer.transformToDtoWithExerciseGroups(workoutRepository.findAll());
    }

    @Override
    public List<WorkoutDto> getUserWorkoutsDto(User user) {
        return workoutTransformer.transformToDto(workoutRepository.findAllByUser(user));
    }

    @Override
    public void updateWorkout(Workout workout) {
        for (ExerciseGroup group : workout.getExerciseGroups()) {
            if (group.getRecord() != null) {
                group.getRecord().setExerciseGroup(group);
            }
        }
        workoutRepository.saveAndFlush(workout);
    }

    @Override
    public Workout getWorkoutById(long id) {
        return workoutRepository.findOne(id);
    }

    @Override
    public List<WorkoutDto> getUserUpcomingWorkoutsDto(User currentUser) {
        Pageable topFive = new PageRequest(0, 5);
        return workoutTransformer.transformToDto(workoutRepository.findByUserAndCompletedFalseOrderByDateAsc(currentUser,
                topFive));
    }

}
