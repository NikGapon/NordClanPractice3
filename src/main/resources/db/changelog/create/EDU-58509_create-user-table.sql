CREATE TABLE IF NOT EXISTS users
(
    id uuid NOT NULL,
    first_name CHARACTER VARYING(30),
    last_name CHARACTER VARYING(30),
    surname CHARACTER VARYING(30),
    creation_date DATE,
    update_date DATE,
    is_deleted boolean,
    is_root boolean,
    login_shell VARCHAR(255),
    is_active BOOLEAN,
    mail VARCHAR(255),
    post VARCHAR(255),
    telegram VARCHAR(255),
    birthday DATE,
    first_day DATE,
    last_day DATE,
    city VARCHAR(255),
    CONSTRAINT users_pkey PRIMARY KEY (id)
);