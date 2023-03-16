-- Заполнение данными таблицы roles
INSERT INTO roles (role_id, role_name)
VALUES (1, 'PROJECT_MANAGER'),
       (2, 'TASK_MANAGER');

-- Заполнение данными таблицы users
INSERT INTO users (user_id, role_id, user_username, user_password, user_email)
VALUES (1, 1, 'Крош', 'krosh123', 'krosh@example.com'),
       (2, 2, 'Ёжик', 'ezhik789', 'ezhik@example.com');

-- Заполнение данными таблицы projects
INSERT INTO projects (proj_id, proj_name, proj_start_date, proj_end_date)
VALUES (1, 'Скамейка', '2023-03-15', '2023-03-31'),
       (2, 'Тайна древних сокровищ', '2023-04-01', '2023-04-30');


-- Заполнение данными таблицы tasks
INSERT INTO tasks (task_id, project_id, task_name, task_author, task_performer,
                   task_release_version, task_status)
VALUES (1, 1, 'Смастерить скамейку', 'Крош', 'Ёжик', '1.0', 'BACKLOG'),
       (2, 1, 'Покрасить скамейку', 'Крош', 'Ёжик', '1.0', 'IN_PROGRESS'),
       (3, 2, 'Разгадать «карту сокровищ»', 'Крош', 'Ёжик', '1.0', 'DONE');

-- Заполнение данными таблицы releases
INSERT INTO releases (rls_id, project_id, rls_start_date, rls_end_date)
VALUES (1, 1, '2023-03-15', '2023-03-20'),
       (2, 2, '2023-04-01', '2023-04-15');
