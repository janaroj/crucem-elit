package com.crucemelit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crucemelit.model.Exercise;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

}
