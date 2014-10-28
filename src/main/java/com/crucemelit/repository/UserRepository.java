package com.crucemelit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crucemelit.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmailIgnoreCase(String email);

    @Query("select u from User u where (lower(u.firstName) like ('%' || lower(:term) || '%')) "
            + " or (lower(u.lastName) like ('%' || lower(:term) || '%'))"
            + " or (lower(u.email) like ('%' || lower(:term) || '%'))")
    List<User> findBySearchTerm(@Param("term") String term);

}
