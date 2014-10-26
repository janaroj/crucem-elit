package com.crucemelit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crucemelit.model.User;

public interface NativeQueryRepository extends JpaRepository<User, Long> {

    /*
     * @Query("select p.firstname, e.name, wer.result, p.sex, g.name, w.date " +
     * "from (select wer.result, wer.exercise_id, wer.workout_id " +
     * "from  workoutexerciseresult wer  inner join (select max(result) result, exercise_id from workoutexerciseresult group by exercise_id) ss on wer.exercise_id = ss.exercise_id "
     * + "and wer.result = ss.result) wer, " + "person p, excercise e, workout w, gym g " +
     * "where p.id = :userId and p.id = w.person and w.id = wer.workout_id and wer.exercise_id = e.id;") List<Object[]>
     * getUserRecords(@Param("userId") long userId);
     */
}
