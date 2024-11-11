CREATE SEQUENCE IF NOT EXISTS activities_seq START WITH 1 INCREMENT BY 1;

CREATE TYPE activity_status AS ENUM ('WAIT', 'IN_PROGRESS', 'DONE');

CREATE TABLE IF NOT EXISTS activities
(
    id BIGINT DEFAULT nextval('activities_seq') NOT NULL,
    name VARCHAR(200),
    user_id UUID,
    mentor_id UUID,
    creation_date DATE,
    update_date DATE,
    end_date DATE,
    template_id BIGINT,
    activity_status VARCHAR(20),
    is_deleted VARCHAR(20),
    CONSTRAINT activities_pk PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (mentor_id) REFERENCES users (id),
    FOREIGN KEY (template_id) REFERENCES template (id)
);