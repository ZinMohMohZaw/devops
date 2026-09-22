# USE CASE: 7 Update an Employee's Details

## CHARACTERISTIC INFORMATION

### Goal in Context
As an *HR advisor* I want to *update an employee's details* so that *employee's details are kept up-to-date.*

### Scope
Company.

### Level
Primary task.

### Preconditions
Employee record exists in database.

### Success End Condition
Updated employee information is saved to database.

### Failed End Condition
Employee record remains unchanged.

### Primary Actor
HR Advisor.

### Trigger
HR Advisor requests edit access for a specific employee ID.

## MAIN SUCCESS SCENARIO

1. HR Advisor searches for employee ID.
2. HR System displays current employee record.
3. HR Advisor modifies fields (e.g., new title or salary).
4. HR System validates updated input.
5. HR System saves updated record to database.

## EXTENSIONS

4. **Invalid field entry**:
    1. HR System displays validation error and rejects update.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0