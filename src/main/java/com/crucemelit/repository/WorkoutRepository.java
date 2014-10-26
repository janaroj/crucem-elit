package com.crucemelit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crucemelit.model.Workout;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    // List<Workout> getUserWorkouts(long id);

}
