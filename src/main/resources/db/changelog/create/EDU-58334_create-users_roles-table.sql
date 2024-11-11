CREATE TABLE IF NOT EXISTS users_roles
(
    user_id UUID REFERENCES users (id),
    role_id INT REFERENCES roles (id),
    PRIMARY KEY (user_id, role_id)
);