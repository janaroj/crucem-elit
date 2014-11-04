package com.crucemelit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crucemelit.model.Record;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

}
