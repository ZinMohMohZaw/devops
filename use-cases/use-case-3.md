# USE CASE: 3 Produce a Report on the Salary of Employees in My Department

## CHARACTERISTIC INFORMATION

### Goal in Context
As a *department manager* I want to *produce a report on the salary of employees in my department* so that *I can support financial reporting for my department.*

### Scope
Department.

### Level
Primary task.

### Preconditions
Manager is authenticated and assigned to a specific department; active employee data exists.

### Success End Condition
A salary report for the manager's assigned department is generated.

### Failed End Condition
No report is generated.

### Primary Actor
Department Manager.

### Trigger
Department Manager requests salary report for their managed department.

## MAIN SUCCESS SCENARIO

1. Department Manager requests salary report for their department.
2. HR System identifies manager's assigned department.
3. HR System queries salary data for active employees in that department.
4. HR System presents the salary report.

## EXTENSIONS

3. **No active employees found in department**:
    1. HR System notifies manager that no employee data is available.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0