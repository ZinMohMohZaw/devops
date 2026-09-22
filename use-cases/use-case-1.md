# USE CASE: 1 Produce a Report on the Salary of All Employees

## CHARACTERISTIC INFORMATION

### Goal in Context
As an *HR advisor* I want to *produce a report on the salary of all employees* so that *I can support financial reporting of the organisation.*

### Scope
Company.

### Level
Primary task.

### Preconditions
Database contains active employee and salary details.

### Success End Condition
A report of salaries for all current employees is generated and displayed.

### Failed End Condition
No report is generated.

### Primary Actor
HR Advisor.

### Trigger
HR Advisor requests a company-wide salary report.

## MAIN SUCCESS SCENARIO

1. HR Advisor requests salary report for all employees.
2. HR System queries database for active employee salary records.
3. HR System renders/prints the formatted salary report.

## EXTENSIONS

2. **No active employee records found**:
    1. HR System alerts user that no salary data exists.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0