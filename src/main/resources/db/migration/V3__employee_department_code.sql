
ALTER TABLE employee_management.employee
ADD COLUMN IF NOT EXISTS department_code VARCHAR(40)
DEFAULT 'EC101';