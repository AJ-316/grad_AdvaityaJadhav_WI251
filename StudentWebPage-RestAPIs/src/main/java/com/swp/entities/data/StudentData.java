package com.swp.entities.data;

import com.swp.entities.Gender;
import com.swp.entities.School;
import com.swp.entities.Student;
import com.swp.repositories.SchoolRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

public record StudentData(String name, Integer rollNo, Integer standard, Float percentage, Gender gender, Integer schoolRegNo) {

    public Student toEntity(SchoolRepository repo) throws NoSuchElementException {
        Student student = new Student();

        Optional<School> school = repo.findById(schoolRegNo);
        student.setSchool(school.orElseThrow(() -> new NoSuchElementException("School Not Present")));
        String nullValueMsg = null;
        if(name == null) nullValueMsg = "name Value Not Present";
        if(rollNo == null) nullValueMsg = "rollNo Value Not Present";
        if(standard == null) nullValueMsg = "standard Value Not Present";
        if(percentage == null) nullValueMsg = "percentage Value Not Present";
        if(gender == null) nullValueMsg = "gender Value Not Present";

        if (nullValueMsg != null) {
            throw new NoSuchElementException(nullValueMsg);
        }

        student.setName(name);
        student.setRollNo(rollNo);
        student.setStandard(standard);
        student.setPercentage(percentage);
        student.setGender(gender);

        return student;
    }

    public void toEntity(Student existing, SchoolRepository repo) throws NoSuchElementException {
        if(schoolRegNo != null) {
            Optional<School> school = repo.findById(schoolRegNo);
            existing.setSchool(school.orElseThrow(() -> new NoSuchElementException("School Not Present")));
        }

        if(name != null) existing.setName(name);
        if(rollNo != null) existing.setRollNo(rollNo);
        if(standard != null) existing.setStandard(standard);
        if(percentage != null) existing.setPercentage(percentage);
        if(gender != null) existing.setGender(gender);
    }
}
