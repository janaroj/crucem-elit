package com.crucemelit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.crucemelit.model.ExerciseModel;

@Repository
public interface ExerciseModelRepository extends JpaRepository<ExerciseModel, Long> {

    @Query("SELECT em FROM ExerciseModel em LEFT JOIN FETCH em.exerciseType")
    List<ExerciseModel> findAllWithTypes();

}
