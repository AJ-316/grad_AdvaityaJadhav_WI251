package entities;

import types.Department;
import types.Designation;
import types.Gender;

public class Employee {

    private String name;
    private int age;
    private Gender gender;
    private int salary;
    private Designation designation;
    private Department department;

    public Employee(String name, int age, Gender gender, int salary, Designation designation, Department department) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.designation = designation;
        this.department = department;
    }

    public void hikeSalary(float percent) {
        percent = 1 + percent/100f;
        salary = (int) (salary * percent);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public int getSalary() {
        return salary;
    }

    public Designation getDesignation() {
        return designation;
    }

    public Department getDepartment() {
        return department;
    }

    public String toString() {
        return "\u001b[32mEmployee\u001b[0m{" +
                "\u001b[34mname" +  "\u001b[0m=\u001b[33m" + "'" + name + '\'' + "\u001b[0m" +
                ", \u001b[34mage" +  "\u001b[0m=\u001b[33m" + age + "\u001b[0m" +
                ", \u001b[34mgender" +  "\u001b[0m=\u001b[33m" + gender + "\u001b[0m" +
                ", \u001b[34msalary" +  "\u001b[0m=\u001b[33m" + salary + "\u001b[0m" +
                ", \u001b[34mdesignation" +  "\u001b[0m=\u001b[33m" + designation + "\u001b[0m" +
                ", \u001b[34mdepartment" +  "\u001b[0m=\u001b[33m" + department + "\u001b[0m" +
                '}';
    }
}
