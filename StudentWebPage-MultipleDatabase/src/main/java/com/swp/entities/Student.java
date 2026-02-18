package com.swp.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="students")
public class Student {

    @Id
    private int rollNumber;
    private String name;
    private int standard;

    private String school;

    public Student() {}

    public Student(int rollNumber, String name, int standard, String school) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.standard = standard;
        this.school = school;
    }

    @Override
    public String toString() {
        return "Student{" +
                "rollNumber=" + rollNumber +
                ", name='" + name + '\'' +
                ", standard=" + standard +
                ", school=" + school +
                '}';
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStandard() {
        return standard;
    }

    public void setStandard(int standard) {
        this.standard = standard;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }
}
