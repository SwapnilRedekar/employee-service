
CREATE SEQUENCE IF NOT EXISTS employee_id_seq
AS INTEGER
INCREMENT BY 1
START WITH 1
OWNED BY employee_management.employee.employee_id;