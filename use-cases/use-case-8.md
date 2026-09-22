# USE CASE: 8 Delete an Employee's Details

## CHARACTERISTIC INFORMATION

### Goal in Context
As an *HR advisor* I want to *delete an employee's details* so that *the company is compliant with data retention legislation.*

### Scope
Company.

### Level
Primary task.

### Preconditions
Target employee record exists in database.

### Success End Condition
Employee record is permanently deleted or marked inactive.

### Failed End Condition
Employee record is retained.

### Primary Actor
HR Advisor.

### Trigger
HR Advisor initiates deletion of employee record.

## MAIN SUCCESS SCENARIO

1. HR Advisor inputs employee ID for deletion.
2. HR System prompts for confirmation.
3. HR Advisor confirms deletion.
4. HR System deletes employee record from database.
5. HR System confirms deletion success.

## EXTENSIONS

3. **HR Advisor cancels deletion**:
    1. HR System aborts operation without changing database.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0