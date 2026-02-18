package com.swp.repositories.postgresql;

import com.swp.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostgreSQLStudentRepository extends JpaRepository<Student, Integer> {
}
