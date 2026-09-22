package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

@SuppressWarnings({"unused", "RedundantModifiers"})
public class App {
    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect() {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 100;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection(
                        "jdbc:mysql://db:3306/employees?allowPublicKeyRetrieval=true&useSSL=false",
                        "root",
                        "example"
                );
                System.out.println("Successfully connected");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + i);
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect() {
        if (con != null) {
            try {
                // Close connection
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }

    /**
     * Extract employee information from the database including current title,
     * salary, department, and manager.
     */
    public Employee getEmployee(int ID) {
        try {
            // Using PreparedStatement to avoid unsafe SQL string concatenation
            String strSelect =
                    "SELECT emp.emp_no, emp.first_name, emp.last_name, " +
                            "       t.title, s.salary, d.dept_name, " +
                            "       CONCAT(m.first_name, ' ', m.last_name) AS manager " +
                            "FROM employees emp " +
                            "JOIN titles t ON emp.emp_no = t.emp_no AND t.to_date = '9999-01-01' " +
                            "JOIN salaries s ON emp.emp_no = s.emp_no AND s.to_date = '9999-01-01' " +
                            "JOIN dept_emp de ON emp.emp_no = de.emp_no AND de.to_date = '9999-01-01' " +
                            "JOIN departments d ON de.dept_no = d.dept_no " +
                            "LEFT JOIN dept_manager dm ON de.dept_no = dm.dept_no AND dm.to_date = '9999-01-01' " +
                            "LEFT JOIN employees m ON dm.emp_no = m.emp_no " +
                            "WHERE emp.emp_no = ?";

            PreparedStatement stmt = con.prepareStatement(strSelect);
            stmt.setInt(1, ID);

            ResultSet rset = stmt.executeQuery();

            if (rset.next()) {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("emp_no");
                emp.first_name = rset.getString("first_name");
                emp.last_name = rset.getString("last_name");
                emp.title = rset.getString("title");
                emp.salary = rset.getInt("salary");
                emp.dept_name = rset.getString("dept_name");
                emp.manager = rset.getString("manager");
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
     * Display employee details to console.
     */
    public void displayEmployee(Employee emp) {
        if (emp != null) {
            System.out.println(
                    emp.emp_no + " "
                            + emp.first_name + " "
                            + emp.last_name + "\n"
                            + emp.title + "\n"
                            + "Salary: " + emp.salary + "\n"
                            + emp.dept_name + "\n"
                            + "Manager: " + emp.manager + "\n"
            );
        } else {
            System.out.println("No employee found.");
        }
    }

    /**
     * Gets all current salaries of employees for a given role (title).
     *
     * @param title The role/title to filter by (e.g., "Engineer")
     * @return A list of Employee objects containing salary details.
     */
    public ArrayList<Employee> getSalariesByRole(String title) {
        try {
            // Using PreparedStatement and removing redundant ASC ordering
            String strSelect =
                    "SELECT employees.emp_no, employees.first_name, employees.last_name, salaries.salary " +
                            "FROM employees, salaries, titles " +
                            "WHERE employees.emp_no = salaries.emp_no " +
                            "AND employees.emp_no = titles.emp_no " +
                            "AND salaries.to_date = '9999-01-01' " +
                            "AND titles.to_date = '9999-01-01' " +
                            "AND titles.title = ? " +
                            "ORDER BY employees.emp_no";

            PreparedStatement stmt = con.prepareStatement(strSelect);
            stmt.setString(1, title);

            ResultSet rset = stmt.executeQuery();

            ArrayList<Employee> employees = new ArrayList<>();
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
            System.out.println("Failed to get salary details by role");
            return null;
        }
    }

    /**
     * Prints a list of employees and their salaries formatted into columns.
     *
     * @param employees The list of employees to print.
     */
    public void printSalaries(ArrayList<Employee> employees) {
        if (employees == null) {
            System.out.println("No employees found.");
            return;
        }

        // Print header
        System.out.printf("%-10s %-15s %-20s %-10s%n", "Emp No", "First Name", "Last Name", "Salary");

        // Loop over all employees
        for (Employee emp : employees) {
            if (emp == null) continue;
            System.out.printf("%-10s %-15s %-20s %-10s%n",
                    emp.emp_no, emp.first_name, emp.last_name, emp.salary);
        }
    }

    static void main(String[] args) {
        App a = new App();

        // Connect to database
        a.connect();

        // Retrieve current salaries for role "Engineer"
        ArrayList<Employee> employees = a.getSalariesByRole("Engineer");

        // Display results
        a.printSalaries(employees);

        // Disconnect from database
        a.disconnect();
    }
}