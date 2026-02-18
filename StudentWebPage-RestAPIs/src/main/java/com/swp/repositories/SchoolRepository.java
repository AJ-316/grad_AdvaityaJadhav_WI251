package com.swp.repositories;

import com.swp.entities.School;
import com.swp.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School, Integer> {

    boolean existsByName(String name);
    Optional<School> findByName(String name);
}
