# USE CASE: 4 Produce a Report on the Salary of Employees of a Given Role

## CHARACTERISTIC INFORMATION

### Goal in Context
As an *HR advisor* I want to *produce a report on the salary of employees of a given role* so that *I can support financial reporting of the organisation.*

### Scope
Company.

### Level
Primary task.

### Preconditions
Database contains active employee, role, and salary data.

### Success End Condition
A report of salaries for employees of a specified role is available.

### Failed End Condition
No report is generated.

### Primary Actor
HR Advisor.

### Trigger
HR Advisor requests salary report for a specific job role.

## MAIN SUCCESS SCENARIO

1. HR Advisor requests salary report by role.
2. HR Advisor selects target role title.
3. HR System extracts salary records for active employees with the specified role.
4. HR System displays/prints the salary report.

## EXTENSIONS

3. **Role does not exist or has no active employees**:
    1. HR System informs user that no records were found.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0