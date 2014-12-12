package com.crucemelit.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crucemelit.model.User;
import com.crucemelit.model.Workout;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    List<Workout> findByExerciseGroupsRecordNotNull();

    @Query("SELECT DISTINCT w FROM Workout w "
            + "LEFT JOIN FETCH w.exerciseGroups eg "
            + "LEFT JOIN FETCH eg.record "
            + "WHERE w.user = (:user)")
    List<Workout> findAllByUser(@Param("user") User user);

    @Query("SELECT w FROM Workout w "
            + "LEFT JOIN FETCH w.user u "
            + "LEFT JOIN FETCH w.exerciseGroups eg "
            + "LEFT JOIN FETCH eg.record "
            + "LEFT JOIN FETCH eg.exercises e "
            + "LEFT JOIN FETCH e.record "
            + "LEFT JOIN FETCH e.exerciseModel em "
            + "WHERE w.id = (:id) and w.user = (:user)")
    Workout findOneByIdAndUser(@Param("id") long id, @Param("user") User user);

    List<Workout> findByUserAndCompletedFalseOrderByDateAsc(User currentUser, Pageable topFive);

    @Query("SELECT DISTINCT w FROM Workout w "
            + "LEFT JOIN FETCH w.user u "
            + "LEFT JOIN FETCH u.gym "
            + "LEFT JOIN FETCH w.exerciseGroups eg "
            + "LEFT JOIN FETCH eg.record "
            + "LEFT JOIN FETCH eg.exercises e "
            + "LEFT JOIN FETCH e.record "
            + "LEFT JOIN FETCH e.exerciseModel em "
            + "LEFT JOIN FETCH em.exerciseType "
            + "WHERE w.completed = true")
    List<Workout> findCompletedWithResults();

}
