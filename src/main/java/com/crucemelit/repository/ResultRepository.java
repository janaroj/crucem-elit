package com.crucemelit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crucemelit.model.Result;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

}
