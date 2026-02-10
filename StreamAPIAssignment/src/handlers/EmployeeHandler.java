package handlers;

import entities.Employee;
import types.Department;
import types.Designation;
import types.Gender;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EmployeeHandler {

    private final List<Employee> employees;
    private final Predicate<Employee> matchManagerPredicate;

    public EmployeeHandler() {
        employees = new ArrayList<>();

        matchManagerPredicate = e -> e.getDesignation().equals(Designation.MANAGER);
    }

    public void init(Employee... employees) {
        this.employees.addAll(List.of(employees));
    }

    public void printHighestPaidEmployee() {
        System.out.println("====================================");
        if(employees.isEmpty()) return;

        Employee highestPaidEmp = employees.stream()
                .max(Comparator.comparingInt(Employee::getSalary))
                .orElse(null);

        System.out.println("Highest Paid Employee: " + highestPaidEmp);
    }

    public void printCountMaleFemale() {
        System.out.println("====================================");
        if(employees.isEmpty()) return;

        Map<Boolean, Long> genderListMap = employees.stream()
                .collect(Collectors.partitioningBy(
                        e -> e.getGender().equals(Gender.MALE),
                        Collectors.counting()
                ));

        genderListMap.forEach((isMale, count) -> System.out.println("There are \u001b[33m" + count + "\u001b[0m employees of gender \u001b[33m" + (isMale ? Gender.MALE : Gender.FEMALE) + "\u001b[0m"));
    }

    public void printTotalExpenseByDepartment() {
        System.out.println("====================================");
        if(employees.isEmpty()) return;

        Map<Department, Integer> expenseByDepartmentMap = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.summingInt(Employee::getSalary)
                ));

        expenseByDepartmentMap.forEach((department, expense) ->
                System.out.println("Total expense of Department \u001b[34m" + department + "\u001b[0m = \u001b[33m" + expense + "\u001b[0m"));
    }

    public void printTopSeniorEmployees(int count) {
        System.out.println("====================================");
        if(employees.isEmpty()) return;

        List<Employee> topSeniorEmployees = employees.stream()
                .sorted(Comparator.comparingInt(Employee::getAge).reversed())
                .limit(count)
                .toList();

        System.out.println("Top \u001b[33m" + count + "\u001b[0m Senior Employees: ");
        topSeniorEmployees.forEach(System.out::println);
    }

    public void printNameManagers() {
        System.out.println("====================================");
        if(employees.isEmpty()) return;

        List<String> managers = employees.stream()
                .filter(matchManagerPredicate)
                .map(Employee::getName)
                .toList();

        System.out.println("Total Managers: \u001b[33m" + managers.size() + "\u001b[0m");
        managers.forEach(System.out::println);
    }

    public void hikeSalaryOf(Predicate<Employee> matchEmployee, float percent) {
        System.out.println("====================================");
        if(employees.isEmpty()) return;

        List<Employee> hikedEmployees = employees.stream()
                .filter(matchEmployee)
                .toList();

        System.out.println("Hiking Salary of Employee(s): " + hikedEmployees.size());
        hikedEmployees.forEach(e -> {
            int oldSalary = e.getSalary();
            e.hikeSalary(percent);
            System.out.println(e);
            System.out.println("\tHiked : \u001b[34m" + oldSalary + "\u001b[0m -> \u001b[33m" + e.getSalary() + "\u001b[0m");
            System.out.println("--------------------------------------------------------");
        });
    }

    public Predicate<Employee> getMatchManagerPredicate() {
        return matchManagerPredicate;
    }

    public void printTotalEmployees() {
        System.out.println("====================================");
        System.out.println("Total Employees: \u001b[33m" + employees.size() + "\u001b[0m");
        employees.forEach(System.out::println);
    }

}
