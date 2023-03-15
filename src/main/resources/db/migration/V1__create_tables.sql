CREATE TABLE projects
(
    proj_id         SERIAL PRIMARY KEY,
    proj_name       VARCHAR(255) NOT NULL,
    proj_start_date TIMESTAMP    NOT NULL,
    proj_end_date   TIMESTAMP
);

CREATE TABLE tasks
(
    task_id              SERIAL PRIMARY KEY,
    project_id           INTEGER      NOT NULL REFERENCES projects (proj_id) ON DELETE CASCADE,
    task_name            VARCHAR(255) NOT NULL,
    task_author          VARCHAR(255) NOT NULL,
    task_performer       VARCHAR(255) NOT NULL,
    task_release_version VARCHAR(255) NOT NULL,
    task_status          VARCHAR(255) NOT NULL
);

CREATE TABLE releases
(
    rls_id         SERIAL PRIMARY KEY,
    project_id     INTEGER   NOT NULL REFERENCES projects (proj_id) ON DELETE CASCADE,
    rls_start_date TIMESTAMP NOT NULL,
    rls_end_date   TIMESTAMP NOT NULL
);

CREATE TABLE roles
(
    role_id   SERIAL PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE users
(
    user_id       SERIAL PRIMARY KEY,
    role_id       INTEGER      NOT NULL REFERENCES roles (role_id),
    user_username VARCHAR(255) NOT NULL UNIQUE,
    user_password VARCHAR(255) NOT NULL,
    user_email    VARCHAR(50)  NOT NULL UNIQUE
);