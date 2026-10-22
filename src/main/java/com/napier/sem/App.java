package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

public class App {

    private Connection con = null;


    public void connect() {
        connect("db:3306", 30000);
    }

    public void connect(String location, int delay) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            return;
        }

        int retries = 10;

        for (int i = 0; i < retries; i++) {
            System.out.println("Connecting to database...");

            try {
                con = DriverManager.getConnection(
                        "jdbc:mysql://" + location
                                + "/employees?allowPublicKeyRetrieval=true&useSSL=false",
                        "root",
                        "example"
                );

                System.out.println("Successfully connected");
                return;

            } catch (SQLException e) {
                System.out.println("Failed to connect to database attempt " + (i + 1));
                System.out.println(e.getMessage());

                if (i < retries - 1) {
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }
        }
    }

    public void disconnect() {
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }

    /**
     * Gets all the current employees and salaries.
     *
     * @return A list of all employees and salaries, or null if there is an error.
     */
    public ArrayList<Employee> getAllSalaries() {
        try {
            Statement stmt = con.createStatement();
            String strSelect =
                    "SELECT employees.emp_no, employees.first_name, employees.last_name, salaries.salary "
                            + "FROM employees, salaries "
                            + "WHERE employees.emp_no = salaries.emp_no AND salaries.to_date = '9999-01-01' "
                            + "ORDER BY employees.emp_no ASC";
            ResultSet rset = stmt.executeQuery(strSelect);
            ArrayList<Employee> employees = new ArrayList<Employee>();
            while (rset.next()) {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("employees.emp_no");
                emp.first_name = rset.getString("employees.first_name");
                emp.last_name = rset.getString("employees.last_name");
                emp.salary = rset.getInt("salaries.salary");
                employees.add(emp);
            }
            return employees;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get salary details");
            return null;
        }
    }

    /**
     * Prints a list of employees.
     * @param employees The list of employees to print.
     */
    public void printSalaries(ArrayList<Employee> employees) {
        // Check employees list is not null
        if (employees == null) {
            System.out.println("No employees");
            return;
        }

        // Print header
        System.out.printf("%-10s %-15s %-20s %-8s%n", "Emp No", "First Name", "Last Name", "Salary");

        // Loop over all employees in the list
        for (Employee emp : employees) {
            // Check individual employee element is not null
            if (emp == null) {
                continue;
            }

            String emp_string =
                    String.format("%-10s %-15s %-20s %-8s",
                            emp.emp_no, emp.first_name, emp.last_name, emp.salary);
            System.out.println(emp_string);
        }
    }

    /**
     * Gets a department by its name.
     * @param dept_name The name of the department to retrieve.
     * @return The Department object, or null if not found.
     */
    public Department getDepartment(String dept_name) {
        try {
            Statement stmt = con.createStatement();
            String strSelect =
                    "SELECT departments.dept_no, departments.dept_name, "
                            + "employees.emp_no, employees.first_name, employees.last_name "
                            + "FROM departments "
                            + "LEFT JOIN dept_manager ON departments.dept_no = dept_manager.dept_no AND dept_manager.to_date = '9999-01-01' "
                            + "LEFT JOIN employees ON dept_manager.emp_no = employees.emp_no "
                            + "WHERE departments.dept_name = '" + dept_name + "'";

            ResultSet rset = stmt.executeQuery(strSelect);
            if (rset.next()) {
                Department dept = new Department();
                dept.dept_no = rset.getString("departments.dept_no");
                dept.dept_name = rset.getString("departments.dept_name");

                if (rset.getString("employees.emp_no") != null) {
                    Employee mgr = new Employee();
                    mgr.emp_no = rset.getInt("employees.emp_no");
                    mgr.first_name = rset.getString("employees.first_name");
                    mgr.last_name = rset.getString("employees.last_name");
                    dept.manager = mgr;
                }
                return dept;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get department details");
            return null;
        }
    }

    /**
     * Gets current salaries of employees in a given department.
     * @param dept The department to get salaries for.
     * @return A list of employees in the department with salary information.
     */
    public ArrayList<Employee> getSalariesByDepartment(Department dept) {
        if (dept == null || dept.dept_no == null) {
            System.out.println("Invalid department specified.");
            return null;
        }
        try {
            Statement stmt = con.createStatement();
            String strSelect =
                    "SELECT employees.emp_no, employees.first_name, employees.last_name, salaries.salary "
                            + "FROM employees, salaries, dept_emp, departments "
                            + "WHERE employees.emp_no = salaries.emp_no "
                            + "AND employees.emp_no = dept_emp.emp_no "
                            + "AND dept_emp.dept_no = departments.dept_no "
                            + "AND salaries.to_date = '9999-01-01' "
                            + "AND dept_emp.to_date = '9999-01-01' "
                            + "AND departments.dept_no = '" + dept.dept_no + "' "
                            + "ORDER BY employees.emp_no ASC";

            ResultSet rset = stmt.executeQuery(strSelect);
            ArrayList<Employee> employees = new ArrayList<Employee>();
            while (rset.next()) {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("employees.emp_no");
                emp.first_name = rset.getString("employees.first_name");
                emp.last_name = rset.getString("employees.last_name");
                emp.salary = rset.getInt("salaries.salary");
                emp.dept = dept;
                employees.add(emp);
            }
            return employees;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get salary details by department");
            return null;
        }
    }

    /**
     * Adds a new employee to the database.
     * @param emp The Employee object containing details to insert.
     */
    public void addEmployee(Employee emp) {
        if (emp == null) return;
        try {
            Statement stmt = con.createStatement();
            String strUpdate =
                    "INSERT INTO employees (emp_no, first_name, last_name, birth_date, gender, hire_date) " +
                            "VALUES (" + emp.emp_no + ", '" + emp.first_name + "', '" + emp.last_name + "', " +
                            "'1990-01-01', 'M', '2020-01-01')";
            stmt.executeUpdate(strUpdate);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to add employee");
        }
    }
    public static void main(String[] args) {
        // Create new Application and connect to database
        App a = new App();

        if(args.length < 1){
            a.connect("localhost:33060", 30000);
        }else{
            a.connect(args[0], Integer.parseInt(args[1]));
        }

        Department dept = a.getDepartment("Development");
        ArrayList<Employee> employees = a.getSalariesByDepartment(dept);


        // Print salary report
        a.printSalaries(employees);

        // Disconnect from database
        a.disconnect();
    }

    /**
     * Gets an employee by ID, including department and manager details.
     * @param ID The employee ID.
     * @return Employee object or null if not found.
     */
    public Employee getEmployee(int ID) {
        try {
            Statement stmt = con.createStatement();
            String strSelect =
                    "SELECT emp.emp_no, emp.first_name, emp.last_name, titles.title, salaries.salary, "
                            + "deps.dept_no, deps.dept_name, mgr.emp_no AS mgr_no, mgr.first_name AS mgr_first, mgr.last_name AS mgr_last "
                            + "FROM employees emp "
                            + "LEFT JOIN titles ON emp.emp_no = titles.emp_no AND titles.to_date = '9999-01-01' "
                            + "LEFT JOIN salaries ON emp.emp_no = salaries.emp_no AND salaries.to_date = '9999-01-01' "
                            + "LEFT JOIN dept_emp ON emp.emp_no = dept_emp.emp_no AND dept_emp.to_date = '9999-01-01' "
                            + "LEFT JOIN departments deps ON dept_emp.dept_no = deps.dept_no "
                            + "LEFT JOIN dept_manager dm ON deps.dept_no = dm.dept_no AND dm.to_date = '9999-01-01' "
                            + "LEFT JOIN employees mgr ON dm.emp_no = mgr.emp_no "
                            + "WHERE emp.emp_no = " + ID;

            ResultSet rset = stmt.executeQuery(strSelect);
            if (rset.next()) {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("emp.emp_no");
                emp.first_name = rset.getString("emp.first_name");
                emp.last_name = rset.getString("emp.last_name");
                emp.title = rset.getString("titles.title");
                emp.salary = rset.getInt("salaries.salary");

                Department dept = new Department();
                dept.dept_no = rset.getString("deps.dept_no");
                dept.dept_name = rset.getString("deps.dept_name");

                if (rset.getString("mgr_no") != null) {
                    Employee mgr = new Employee();
                    mgr.emp_no = rset.getInt("mgr_no");
                    mgr.first_name = rset.getString("mgr_first");
                    mgr.last_name = rset.getString("mgr_last");
                    dept.manager = mgr;
                }

                emp.dept = dept;
                emp.manager = dept.manager;
                return emp;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get employee details");
            return null;
        }
    }

    /**
     * Gets an employee by first name and last name.
     * @param firstName Employee's first name.
     * @param lastName Employee's last name.
     * @return Employee object or null if not found.
     */
    public Employee getEmployee(String firstName, String lastName) {
        try {
            Statement stmt = con.createStatement();
            String strSelect =
                    "SELECT emp_no FROM employees "
                            + "WHERE first_name = '" + firstName + "' AND last_name = '" + lastName + "'";

            ResultSet rset = stmt.executeQuery(strSelect);
            if (rset.next()) {
                int emp_no = rset.getInt("emp_no");
                return getEmployee(emp_no);
            } else {
                System.out.println("Employee not found: " + firstName + " " + lastName);
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get employee by name");
            return null;
        }
    }
    /**
     * Displays a single employee's details safely.
     * @param emp The employee object to display.
     */
    public void displayEmployee(Employee emp) {
        if (emp == null) {
            System.out.println("No employee details to display.");
            return;
        }
        System.out.println(
                emp.emp_no + " "
                        + emp.first_name + " "
                        + emp.last_name + "\n"
                        + (emp.title != null ? emp.title : "No Title") + "\n"
                        + "Salary: " + emp.salary + "\n"
                        + (emp.dept != null ? emp.dept.dept_name : "No Department") + "\n"
                        + "Manager: " + (emp.manager != null ? emp.manager.first_name + " " + emp.manager.last_name : "None") + "\n"
        );
    }
}


