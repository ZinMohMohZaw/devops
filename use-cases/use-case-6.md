# USE CASE: 6 View an Employee's Details

## CHARACTERISTIC INFORMATION

### Goal in Context
As an *HR advisor* I want to *view an employee's details* so that *the employee's promotion request can be supported.*

### Scope
Company.

### Level
Primary task.

### Preconditions
Target employee record exists in database.

### Success End Condition
Employee details (ID, name, role, salary, department, manager) are displayed.

### Failed End Condition
Employee details are not displayed.

### Primary Actor
HR Advisor.

### Trigger
HR Advisor inputs an employee ID to review records.

## MAIN SUCCESS SCENARIO

1. HR Advisor provides target employee ID.
2. HR System queries database using multi-table join (employee, dept, title, manager).
3. HR System displays full employee details.

## EXTENSIONS

2. **Employee ID does not exist**:
    1. HR System reports that employee record was not found.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0