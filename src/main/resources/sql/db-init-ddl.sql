CREATE SCHEMA IF NOT EXISTS tracker_schema;

-- ПОЛЬЗОВАТЕЛИ
CREATE TABLE IF NOT EXISTS tracker_schema.users(
    id      INT      GENERATED ALWAYS AS IDENTITY    PRIMARY KEY    NOT NULL,
    email   VARCHAR  NOT NULL,
    name    VARCHAR  NOT NULL,
    status  VARCHAR  NOT null default 'CHILLING',
    salary  MONEY    NOT NULL,

    -- ОГРАНИЧЕНИЯ
    CONSTRAINT email_uniqueness_users UNIQUE (email),
    CONSTRAINT check_email_length_users CHECK(LENGTH(email) > 0 and TRIM(email) <> ''),
    CONSTRAINT check_name_length_users CHECK(LENGTH(name) > 0 and TRIM(name) <> ''),
    CONSTRAINT check_status_length_users CHECK(LENGTH(status) > 0 and TRIM(status) <> ''),
    CONSTRAINT check_status_values_users CHECK(status IN ('WORKING', 'CHILLING')),
    CONSTRAINT salary_positive_users CHECK(salary > 0::money)
);

-- ПРОЕКТЫ
CREATE TABLE IF NOT EXISTS tracker_schema.projects(
    id          INT     GENERATED ALWAYS AS IDENTITY    PRIMARY KEY     NOT NULL,
    name        VARCHAR     UNIQUE         NOT NULL,
    status      VARCHAR     default 'PROGRESS' NOT NULL,

    -- ОГРАНИЧЕНИЯ
    CONSTRAINT name_uniqueness_projects UNIQUE (name),
    CONSTRAINT check_name_length_projects CHECK(LENGTH(name) > 0 and TRIM(name) <> ''),
    CONSTRAINT check_status_length_projects CHECK(LENGTH(status) > 0 and TRIM(status) <> ''),
    CONSTRAINT check_status_values_projects CHECK(status IN ('PROGRESS', 'STOPPED'))
);

-- ИНТЕГРАЦИИ
CREATE TABLE IF NOT EXISTS tracker_schema.integrations(
    id          INT      GENERATED ALWAYS AS IDENTITY    PRIMARY KEY    NOT NULL,
    name        VARCHAR NOT NULL,
    url         VARCHAR NOT NULL,
    project_id  INT NOT NULL,

    -- ОГРАНИЧЕНИЯ
    CONSTRAINT fk_projects_integrations FOREIGN KEY (project_id) REFERENCES tracker_schema.projects(id) ON DELETE CASCADE,
    CONSTRAINT name_uniqueness_integrations UNIQUE (name),
    CONSTRAINT check_name_length_integrations CHECK(LENGTH(name) > 0 and TRIM(name) <> ''),
    CONSTRAINT url_uniqueness_integrations UNIQUE (url),
    CONSTRAINT check_url_length_integrations CHECK(LENGTH(url) > 0 and TRIM(url) <> '')
);

-- ЗАДАЧИ
CREATE TABLE IF NOT EXISTS tracker_schema.tasks(
    id          INT      GENERATED ALWAYS AS IDENTITY    PRIMARY KEY    NOT NULL,
    name        VARCHAR     NOT NULL,
    description VARCHAR(150)     NOT NULL,
    status      VARCHAR     NOT null default 'UNDONE',
    deadline    DATE        NOT NULL,
    created_at  DATE        NOT NULL    DEFAULT CURRENT_DATE,
    project_id  INT     NOT NULL,
    implementer INT     NOT NULL,

    -- ОГРАНИЧЕНИЯ
    CONSTRAINT fk_project_tasks FOREIGN KEY (project_id) REFERENCES tracker_schema.projects(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_tasks FOREIGN KEY (implementer) REFERENCES tracker_schema.users(id) ON DELETE CASCADE,
    CONSTRAINT check_name_length_tasks CHECK(LENGTH(name) > 0 and TRIM(name) <> ''),
    CONSTRAINT check_desc_length_tasks CHECK(LENGTH(description) > 0 and TRIM(description) <> ''),
    CONSTRAINT check_status_values_tasks CHECK(status IN ('DONE', 'UNDONE')),
    CONSTRAINT check_deadline_not_in_past CHECK(deadline > CURRENT_DATE)
);
