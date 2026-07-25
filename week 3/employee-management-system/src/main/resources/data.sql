-- Seed data for manual testing of the Employee Management System endpoints.
INSERT INTO department (id, name) VALUES (1, 'Engineering');
INSERT INTO department (id, name) VALUES (2, 'Human Resources');
INSERT INTO department (id, name) VALUES (3, 'Finance');

INSERT INTO employee (id, name, email, department_id, created_by, last_modified_by, created_date, last_modified_date)
VALUES (1, 'Arjun Rao', 'arjun.rao@example.com', 1, 'system', 'system', NOW(), NOW());
INSERT INTO employee (id, name, email, department_id, created_by, last_modified_by, created_date, last_modified_date)
VALUES (2, 'Meera Iyer', 'meera.iyer@example.com', 1, 'system', 'system', NOW(), NOW());
INSERT INTO employee (id, name, email, department_id, created_by, last_modified_by, created_date, last_modified_date)
VALUES (3, 'Kiran Shah', 'kiran.shah@example.com', 2, 'system', 'system', NOW(), NOW());
INSERT INTO employee (id, name, email, department_id, created_by, last_modified_by, created_date, last_modified_date)
VALUES (4, 'Priya Nair', 'priya.nair@example.com', 3, 'system', 'system', NOW(), NOW());
