package com.crucemelit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crucemelit.model.Record;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    /*
     * @Query(
     * "delete from Record r join fetch r.exerciseGroup eg join fetch eg.workout w join fetch w.user u where u.id = (:userId) and r.id = "
     * )
     * 
     * @Query("delete from Record r where r.id = (:id) and r.exerciseGroup.workout.user.id = (:userId)") void
     * deleteByIdAndUser(@Param("id") long id, @Param("userId") long userId);
     */

}
