# USE CASE: 5 Add a New Employee's Details

## CHARACTERISTIC INFORMATION

### Goal in Context
As an *HR advisor* I want to *add a new employee's details* so that *I can ensure the new employee is paid.*

### Scope
Company.

### Level
Primary task.

### Preconditions
HR Advisor is logged into system; new employee details are available.

### Success End Condition
New employee profile is saved into database.

### Failed End Condition
New employee profile is not created.

### Primary Actor
HR Advisor.

### Trigger
HR Advisor initiates new employee creation workflow.

## MAIN SUCCESS SCENARIO

1. HR Advisor enters new employee personal, role, and salary information.
2. HR Advisor submits the record.
3. HR System validates input fields.
4. HR System creates new employee entry in database.
5. HR System confirms creation success.

## EXTENSIONS

3. **Invalid input data (e.g., negative salary, missing ID)**:
    1. HR System flags invalid fields and prompts HR Advisor to correct them.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0