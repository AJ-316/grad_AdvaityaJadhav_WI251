package com.swp.controllers;

import com.swp.entities.School;
import com.swp.entities.data.StandardData;
import com.swp.repositories.SchoolRepository;
import com.swp.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/students/school")
public class SchoolController {

    @Autowired
    private SchoolRepository schoolRepo;

    @Autowired
    private StudentRepository studentRepos;

    @GetMapping
    public List<School> select() {
        return schoolRepo.findAll();
    }

    @GetMapping(params="name")
    public Optional<School> select(@RequestParam String name) {
        return schoolRepo.findByName(name);
    }

    @GetMapping("/count")
    public String countInSchool(@RequestParam String name) {
        if(!schoolRepo.existsByName(name)) return "<h2>School Not Present";
        return "<h2>Total Students in School " + name + " = " + studentRepos.countAllBySchool_Name(name);
    }

    @GetMapping("/standard/count")
    public String countInStandard(@RequestParam(name="class") Integer standard) {
        StringBuilder builder = new StringBuilder("<h2>Strength in Standard " + standard + " for every School:<br></h2>");
        List<StandardData> standards = studentRepos.countStudentByStandard(standard);
        for (StandardData standardData : standards) {
            builder.append("<h3>School ").append(standardData.getSchoolName()).append(" with strength: ").append(standardData.getStrength()).append("</h3>");
        }

        return builder.toString();
    }

    @PostMapping
    public String insert(@RequestBody School schoolReq) {
        try {
            schoolRepo.save(schoolReq);

            return "<h2>Successfully Inserted: " + schoolReq;
        } catch (NoSuchElementException e) {
            return "<h2>Insert Failed: " + e.getMessage();
        }
    }

    @DeleteMapping("/{regNo}")
    public String delete(@PathVariable int regNo) {
        if(!schoolRepo.existsById(regNo)) return "<h2>Delete Failed: School Not Present";
        String msg = "<h2>Successfully Deleted: " + schoolRepo.findById(regNo);
        schoolRepo.deleteById(regNo);
        return msg;
    }
}
