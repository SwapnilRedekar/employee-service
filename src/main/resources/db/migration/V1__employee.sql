
CREATE TABLE IF NOT EXISTS employee_management.employee (
   employee_id INTEGER PRIMARY KEY,
   first_name VARCHAR(20),
   last_name VARCHAR(20),
   email VARCHAR(30) UNIQUE,
   grade VARCHAR(3),
   date_of_birth DATE,
   salary NUMERIC(15,2)
)