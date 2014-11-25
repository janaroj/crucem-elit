package com.crucemelit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crucemelit.model.ExerciseType;

@Repository
public interface ExerciseTypeRepository extends JpaRepository<ExerciseType, Long> {

}
