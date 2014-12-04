package com.crucemelit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crucemelit.model.User;
import com.crucemelit.model.Workout;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    List<Workout> findByExerciseGroupsRecordNotNull();

    List<Workout> findAllByUser(User user);

    Workout findOneByIdAndUser(long id, User user);

}
