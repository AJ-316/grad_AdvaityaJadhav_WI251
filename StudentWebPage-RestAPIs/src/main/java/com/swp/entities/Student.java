package com.swp.entities;

import jakarta.persistence.*;

@Entity
@Table(name="students")
public class Student {

    @Id
    @GeneratedValue
    private Integer regNo;

    private Integer rollNo;
    private String name;
    private Integer standard;
    private Float percentage;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "schoolRegNo")
    private School school;

    public void setRegNo(Integer regNo) {
        this.regNo = regNo;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getRegNo() {
        return regNo;
    }

    public Integer getRollNo() {
        return rollNo;
    }

    public void setRollNo(Integer rollNo) {
        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStandard() {
        return standard;
    }

    public void setStandard(Integer standard) {
        this.standard = standard;
    }

    public Float getPercentage() {
        return percentage;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    @Override
    public String toString() {
        return "Student{" +
                "regNo=" + regNo +
                ", rollNo=" + rollNo +
                ", name='" + name + '\'' +
                ", standard=" + standard +
                ", percentage=" + percentage +
                ", gender=" + gender +
                ", school=" + school +
                '}';
    }
}
