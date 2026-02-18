package com.swp.controllers;

import com.swp.entities.Student;
import com.swp.repositories.mysql.MySQLStudentRepository;
import com.swp.repositories.postgresql.PostgreSQLStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    @Autowired
    private MySQLStudentRepository mysqlRepo;
    @Autowired
    private PostgreSQLStudentRepository postgresqlRepo;

    @PostMapping("/students")
    public String insertStudent(
            @RequestParam String name,
            @RequestParam String school,
            @RequestParam int rollNumber,
            @RequestParam int standard)
    {

        if(mysqlRepo.existsById(rollNumber) || postgresqlRepo.existsById(rollNumber)) {
            return "<h2>Student already exists: Duplicate Roll Number</h2>";
        }

        Student student = new Student(rollNumber, name, standard, school);
        mysqlRepo.save(student);
        postgresqlRepo.save(student);
        return "<h2>Inserted Student:<br>" + student + "</h2>";
    }
}
