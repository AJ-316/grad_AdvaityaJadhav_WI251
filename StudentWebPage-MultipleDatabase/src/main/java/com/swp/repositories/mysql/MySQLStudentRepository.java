package com.swp.repositories.mysql;

import com.swp.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MySQLStudentRepository extends JpaRepository<Student, Integer> {
}
