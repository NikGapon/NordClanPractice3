CREATE SEQUENCE IF NOT EXISTS questions_seq START WITH 1 INCREMENT BY 1;

CREATE TYPE lvl_state AS ENUM ('JUNIOR', 'MIDDLE', 'SENIOR');
CREATE TYPE type_state AS ENUM ('THEORY', 'PRACTIC_TASK');

CREATE TABLE IF NOT EXISTS questions
(
    id BIGINT DEFAULT nextval('questions_seq') NOT NULL,
    question TEXT NOT NULL,
    answer TEXT,
    link TEXT,
    type VARCHAR(20),
    lvl VARCHAR(20),
    group_id BIGINT,
    CONSTRAINT questions_pk PRIMARY KEY (id),
    CONSTRAINT questions_fk_group_id FOREIGN KEY (group_id)
        REFERENCES groups (id) ON DELETE CASCADE
);