package com.swp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="schools")
public class School {

    @Id
    @GeneratedValue
    private Integer regNo;
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public void setRegNo(Integer regNo) {
        this.regNo = regNo;
    }

    public Integer getRegNo() {
        return regNo;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "School{" +
                "regNo=" + regNo +
                ", name='" + name + '\'' +
                '}';
    }
}
