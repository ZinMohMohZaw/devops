package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    static App app;

    @BeforeAll
    static void init() {
        app = new App();
    }

    // ==========================================
    // Tests for printSalaries
    // ==========================================

    @Test
    void printSalariesTestNull() {
        app.printSalaries(null);
    }

    @Test
    void printSalariesTestEmpty() {
        ArrayList<Employee> employees = new ArrayList<Employee>();
        app.printSalaries(employees);
    }

    @Test
    void printSalariesTestContainsNull() {
        ArrayList<Employee> employees = new ArrayList<Employee>();
        employees.add(null);
        app.printSalaries(employees);
    }

    @Test
    void printSalariesTestNormal() {
        ArrayList<Employee> employees = new ArrayList<Employee>();
        Employee emp = new Employee();
        emp.emp_no = 1;
        emp.first_name = "Kevin";
        emp.last_name = "Chalmers";
        emp.title = "Engineer";
        emp.salary = 55000;
        employees.add(emp);
        app.printSalaries(employees);
    }

    // ==========================================
    // Exercise: Tests for displayEmployee
    // ==========================================

    @Test
    void displayEmployeeTestNull() {
        app.displayEmployee(null);
    }

    @Test
    void displayEmployeeTestNormal() {
        Employee emp = new Employee();
        emp.emp_no = 1;
        emp.first_name = "Kevin";
        emp.last_name = "Chalmers";
        emp.title = "Engineer";
        emp.salary = 55000;

        Department dept = new Department();
        dept.dept_no = "d001";
        dept.dept_name = "Engineering";
        emp.dept = dept;

        Employee mgr = new Employee();
        mgr.first_name = "Manager";
        mgr.last_name = "User";
        emp.manager = mgr;

        app.displayEmployee(emp);
    }

    @Test
    void displayEmployeeTestMissingDeptAndManager() {
        Employee emp = new Employee();
        emp.emp_no = 2;
        emp.first_name = "John";
        emp.last_name = "Doe";
        emp.title = "Analyst";
        emp.salary = 45000;
        emp.dept = null;
        emp.manager = null;

        app.displayEmployee(emp);
    }
}