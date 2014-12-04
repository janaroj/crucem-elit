package com.crucemelit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crucemelit.model.Comment;
import com.crucemelit.model.Gym;

@Repository
public interface GymRepository extends JpaRepository<Gym, Long> {

    @Query("select g from Gym g where (lower(g.name) like (lower(:term) || '%'))")
    List<Gym> findBySearchTerm(@Param("term") String term);

    @Query("select g.comments from Gym g where g.id = (:id)")
    List<Comment> getCommentsByGymId(@Param("id") long id);

}
