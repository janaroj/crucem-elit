package com.crucemelit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crucemelit.model.Gym;

@Repository
public interface GymRepository extends JpaRepository<Gym, Long> {

	@Query("select g from Gym g where (lower(g.name) like (lower(:term) || '%'))")
	List<Gym> findBySearchTerm(@Param("term") String term);

}
