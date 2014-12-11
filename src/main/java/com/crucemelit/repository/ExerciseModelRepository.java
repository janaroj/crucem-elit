package com.crucemelit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crucemelit.model.ExerciseModel;

@Repository
public interface ExerciseModelRepository extends JpaRepository<ExerciseModel, Long> {

}
