import entities.Employee;
import handlers.EmployeeHandler;
import types.Department;
import types.Designation;
import types.Gender;

public class Main {
    public static void main(String[] args) {
        EmployeeHandler employeeHandler = new EmployeeHandler();
        employeeHandler.init(
                new Employee("Aarav Sharma", 26, Gender.MALE, 52000, Designation.ENGINEER, Department.ENGINEERING),
                new Employee("Diya Patel", 29, Gender.FEMALE, 48000, Designation.CLERK, Department.HR),
                new Employee("Kabir Mehta", 34, Gender.MALE, 75000, Designation.MANAGER, Department.IT),
                new Employee("Isha Nair", 27, Gender.FEMALE, 61000, Designation.ENGINEER, Department.IT),
                new Employee("Rohan Gupta", 31, Gender.MALE, 56000, Designation.ENGINEER, Department.ENGINEERING),
                new Employee("Ananya Singh", 25, Gender.FEMALE, 45000, Designation.CLERK, Department.ADMINISTRATION),
                new Employee("Vivaan Desai", 38, Gender.MALE, 92000, Designation.MANAGER, Department.FINANCE),
                new Employee("Meera Iyer", 33, Gender.FEMALE, 68000, Designation.ENGINEER, Department.OPERATIONS),
                new Employee("Arjun Verma", 28, Gender.MALE, 59000, Designation.ENGINEER, Department.SALES),
                new Employee("Sara Khan", 30, Gender.FEMALE, 72000, Designation.MANAGER, Department.MARKETING),

                new Employee("Neel Joshi", 24, Gender.MALE, 42000, Designation.CLERK, Department.SALES),
                new Employee("Pooja Kulkarni", 36, Gender.FEMALE, 88000, Designation.MANAGER, Department.HR),
                new Employee("Aditya Rao", 27, Gender.MALE, 64000, Designation.ENGINEER, Department.ENGINEERING),
                new Employee("Tanvi Bhosale", 29, Gender.FEMALE, 53000, Designation.ENGINEER, Department.OPERATIONS),
                new Employee("Siddharth Jain", 41, Gender.MALE, 98000, Designation.MANAGER, Department.ADMINISTRATION),
                new Employee("Ritika Malhotra", 32, Gender.FEMALE, 61000, Designation.ENGINEER, Department.MARKETING),
                new Employee("Manav Kapoor", 26, Gender.MALE, 50000, Designation.CLERK, Department.FINANCE),
                new Employee("Nisha Chatterjee", 28, Gender.FEMALE, 57000, Designation.ENGINEER, Department.IT),
                new Employee("Kunal Soni", 35, Gender.MALE, 83000, Designation.MANAGER, Department.OPERATIONS),
                new Employee("Aditi Reddy", 23, Gender.FEMALE, 41000, Designation.CLERK, Department.HR),

                new Employee("Pranav Ghosh", 30, Gender.MALE, 65000, Designation.ENGINEER, Department.ENGINEERING),
                new Employee("Sneha Menon", 27, Gender.FEMALE, 54000, Designation.ENGINEER, Department.SALES),
                new Employee("Harsh Vardhan", 39, Gender.MALE, 91000, Designation.MANAGER, Department.IT),
                new Employee("Maya Fernandes", 31, Gender.FEMALE, 69000, Designation.ENGINEER, Department.OPERATIONS),
                new Employee("Om Prakash", 25, Gender.MALE, 46000, Designation.CLERK, Department.ADMINISTRATION),
                new Employee("Shreya Das", 34, Gender.FEMALE, 77000, Designation.MANAGER, Department.MARKETING),
                new Employee("Raghav Pandey", 28, Gender.MALE, 60000, Designation.ENGINEER, Department.ENGINEERING),
                new Employee("Juhi Agrawal", 26, Gender.FEMALE, 52000, Designation.CLERK, Department.FINANCE),
                new Employee("Samar Thakur", 33, Gender.MALE, 78000, Designation.MANAGER, Department.SALES),
                new Employee("Neha Saxena", 29, Gender.FEMALE, 63000, Designation.ENGINEER, Department.IT)
        );

        employeeHandler.printTotalEmployees();
        employeeHandler.printCountMaleFemale();
        employeeHandler.printNameManagers();
        employeeHandler.printTotalExpenseByDepartment();
        employeeHandler.printHighestPaidEmployee();
        employeeHandler.printTopSeniorEmployees(5);

        employeeHandler.hikeSalaryOf(employeeHandler.getMatchManagerPredicate().negate(), 20);
    }
}
