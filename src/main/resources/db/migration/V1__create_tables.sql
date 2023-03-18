-- Создание таблицы проектов
CREATE TABLE projects
(
    proj_id      SERIAL PRIMARY KEY,
    proj_title   VARCHAR(255) NOT NULL,
    proj_is_open BOOLEAN      NOT NULL DEFAULT TRUE
);

-- Создание таблицы задач
CREATE TABLE tasks
(
    task_id        SERIAL PRIMARY KEY,
    proj_id        INTEGER      NOT NULL,
    task_name      VARCHAR(255) NOT NULL,
    task_author    VARCHAR(255) NOT NULL,
    task_performer VARCHAR(255) NULL,
    task_status    VARCHAR(50)  NOT NULL CHECK (task_status IN ('BACKLOG', 'IN_PROGRESS', 'DONE')),
    FOREIGN KEY (proj_id)
        REFERENCES projects (proj_id)
        ON DELETE CASCADE
);

-- Создание таблицы релизов
CREATE TABLE releases
(
    rls_id         SERIAL PRIMARY KEY,
    task_id        INTEGER     NOT NULL,
    rls_version    VARCHAR(50) NOT NULL,
    rls_start_date TIMESTAMP   NOT NULL,
    rls_end_date   TIMESTAMP   NULL,
    FOREIGN KEY (task_id)
        REFERENCES tasks (task_id)
        ON DELETE CASCADE
);

-- Создание таблицы ролей
CREATE TABLE roles
(
    role_id   SERIAL PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL
);

-- Создание таблицы пользователей
CREATE TABLE users
(
    user_id       SERIAL PRIMARY KEY,
    role_id       INTEGER      NOT NULL,
    user_name     VARCHAR(255) NOT NULL UNIQUE,
    user_password VARCHAR(255) NOT NULL,
    FOREIGN KEY (role_id)
        REFERENCES roles (role_id)
);

-- Создание индексов
CREATE INDEX tasks_proj_id_index ON tasks (proj_id);
CREATE INDEX releases_task_id_index ON releases (task_id);