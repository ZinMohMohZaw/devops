# USE CASE: 2 Produce a Report on the Salary of Employees in a Department

## CHARACTERISTIC INFORMATION

### Goal in Context
As an *HR advisor* I want to *produce a report on the salary of employees in a department* so that *I can support financial reporting of the organisation.*

### Scope
Company.

### Level
Primary task.

### Preconditions
Database contains active department, employee, and salary data.

### Success End Condition
A department-specific salary report is generated.

### Failed End Condition
No report is generated.

### Primary Actor
HR Advisor.

### Trigger
HR Advisor selects a specific department for salary reporting.

## MAIN SUCCESS SCENARIO

1. HR Advisor requests salary report by department.
2. HR Advisor provides/selects target department name.
3. HR System extracts salary records for active employees in that department.
4. HR System displays/prints the department salary report.

## EXTENSIONS

3. **Department does not exist or has no active employees**:
    1. HR System displays error message stating no records were found.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0