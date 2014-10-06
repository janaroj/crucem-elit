package com.crucemelit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crucemelit.model.Gym;

@Repository
public interface GymRepository extends JpaRepository<Gym, Long> {

}
