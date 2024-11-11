INSERT INTO users_roles (user_id, role_id) VALUES
    ((SELECT id FROM users WHERE first_name = 'user10'), 8);