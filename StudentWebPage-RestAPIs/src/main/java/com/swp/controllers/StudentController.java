package com.swp.controllers;

import com.swp.entities.Gender;
import com.swp.entities.Student;
import com.swp.entities.data.StudentData;
import com.swp.repositories.SchoolRepository;
import com.swp.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepo;
    @Autowired
    private SchoolRepository schoolRepo;

    @GetMapping
    public List<Student> select() {
        return studentRepo.findAll();
    }

    @GetMapping("/{regNo}")
    public Optional<Student> select(@PathVariable Integer regNo) {
        return studentRepo.findById(regNo);
    }

    @PostMapping
    public String insert(@RequestBody StudentData studentData) {
        try {
            Student newStudent = studentData.toEntity(schoolRepo);
            studentRepo.save(newStudent);

            return "<h2>Successfully Inserted: " + studentData;
        } catch (NoSuchElementException e) {
            return "<h2>Insert Failed: " + e.getMessage();
        }
    }

    @PutMapping("/{regNo}")
    public String update(@PathVariable Integer regNo, @RequestBody StudentData studentData) {
        try {
            Student available = studentRepo.findById(regNo)
                    .orElseThrow(() -> new NoSuchElementException("Student Not Present"));

            Student newStudent = studentData.toEntity(schoolRepo);
            newStudent.setRegNo(available.getRegNo());
            studentRepo.save(newStudent);

            return "<h2>Successfully Updated: " + studentData;
        } catch (NoSuchElementException e) {
            return "<h2>Update Failed: " + e.getMessage();
        }
    }

    @PatchMapping("/{regNo}")
    public String patch(@PathVariable Integer regNo, @RequestBody StudentData studentData) {
        try {
            Student available = studentRepo.findById(regNo)
                    .orElseThrow(() -> new NoSuchElementException("Student Not Present"));

            studentData.toEntity(available, schoolRepo);
            studentRepo.save(available);
            return "<h2>Successfully Patched: " + available;
        } catch (NoSuchElementException e) {
            return "<h2>Patch Failed: " + e.getMessage();
        }
    }

    @DeleteMapping("/{regNo}")
    public String delete(@PathVariable Integer regNo) {
        if(!studentRepo.existsById(regNo)) return "Delete Failed: Student Not Present";
        String msg = "<h2>Successfully Deleted: " + studentRepo.findById(regNo);
        studentRepo.deleteById(regNo);
        return msg;
    }

    // ===========================================================================================

    @GetMapping("/result")
    public List<Student> result(@RequestParam boolean pass) {
        if(pass) return studentRepo.findAllByPercentageGreaterThanEqualOrderByPercentageDesc(40f);
        return studentRepo.findAllByPercentageLessThanEqualOrderByPercentageDesc(40f);
    }

    @GetMapping("/strength")
    public String strength(@RequestParam Gender gender, @RequestParam int standard) {
        return "Total " + gender + " Students in standard " + standard + " = " + studentRepo.countAllByGenderAndStandard(gender, standard);
    }
}
