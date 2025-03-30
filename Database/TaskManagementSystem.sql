DROP DATABASE IF EXISTS TaskManagementSystem;
CREATE DATABASE TaskManagementSystem;
USE TaskManagementSystem;

-- Bảng User (CÓ created_at, updated_at)
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'EMPLOYEE') NOT NULL,
    is_active TINYINT(1) DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Bảng Project (CÓ created_at, updated_at)
DROP TABLE IF EXISTS project;
CREATE TABLE project (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Bảng Task (CÓ created_at, updated_at)
DROP TABLE IF EXISTS task;
CREATE TABLE task (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status ENUM('Pending', 'In Progress', 'Completed') DEFAULT 'Pending',
    assigned_to INT,
    project_id INT,
    priority ENUM('Low', 'Medium', 'High') NOT NULL DEFAULT 'Medium',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (assigned_to) REFERENCES `user`(id) ON DELETE SET NULL,
    FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE
);

-- Bảng Task History (CHỈ CÓ created_at)
DROP TABLE IF EXISTS task_history;
CREATE TABLE task_history (
    id INT AUTO_INCREMENT PRIMARY KEY,
    task_id INT,
    status ENUM('Pending', 'In Progress', 'Completed'),
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- CHỈ CÓ created_at
    changed_by INT,
    FOREIGN KEY (task_id) REFERENCES task(id) ON DELETE CASCADE,
    FOREIGN KEY (changed_by) REFERENCES `user`(id) ON DELETE SET NULL
);


-- Thêm User (Mật khẩu đã mã hóa bằng BCrypt)
INSERT INTO `user` (name, email, password, role, is_active) VALUES
('Admin User', 'admin@example.com', '$2a$10$O4Nf5DExNjbK5Op1mOZVOuT1GRnW7qkRIFjTjv8EkcOQ4C1pB7ISm', 'ADMIN', TRUE), -- password: admin123
('Employee One', 'employee1@example.com', '$2a$10$O4Nf5DExNjbK5Op1mOZVOuT1GRnW7qkRIFjTjv8EkcOQ4C1pB7ISm', 'EMPLOYEE', TRUE), -- password: password1
('Employee Two', 'employee2@example.com', '$2a$10$O4Nf5DExNjbK5Op1mOZVOuT1GRnW7qkRIFjTjv8EkcOQ4C1pB7ISm', 'EMPLOYEE', TRUE); -- password: password2

-- Thêm Project
INSERT INTO project (name, description) VALUES
('Project A', 'Develop a new feature'),
('Project B', 'Fix critical bugs'),
('Project C', 'Research and development');

-- Thêm Task
INSERT INTO task (title, description, status, assigned_to, project_id, priority) VALUES
('Implement login', 'Develop authentication system', 'Pending', 2, 1, 'High'),
('Fix database bug', 'Resolve foreign key issue', 'In Progress', 3, 2, 'Medium'),
('Write documentation', 'Update API docs', 'Completed', 2, 3, 'Low');

-- Thêm Task History
INSERT INTO task_history (task_id, status, changed_by) VALUES
(1, 'Pending', 2),
(2, 'In Progress', 3),
(3, 'Completed', 2);

