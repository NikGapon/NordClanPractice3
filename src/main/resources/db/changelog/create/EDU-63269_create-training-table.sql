CREATE SEQUENCE IF NOT EXISTS training_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE IF NOT EXISTS training
(
    id              BIGINT DEFAULT nextval('training_seq') NOT NULL,
    student_id      UUID,
    mentor_id       UUID,
    training_manager_id UUID,
    template_id     BIGINT,
    training_status VARCHAR,
    creation_date   TIMESTAMP,
    update_date     TIMESTAMP,
    training_end_date TIMESTAMP,
    training_start_date TIMESTAMP,
    training_pause_date TIMESTAMP,
    status_id       BIGINT,
    version         INT    DEFAULT 0                       NOT NULL,
    PRIMARY KEY (id)
);