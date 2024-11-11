CREATE SEQUENCE IF NOT EXISTS training_request_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE IF NOT EXISTS training_request
(
    id                  BIGINT DEFAULT nextval('training_request_seq') NOT NULL,
    status_id           BIGINT,
    author_id           UUID,
    training_manager_id UUID,
    student_id          UUID,
    mentor_id           UUID,
    training_status     VARCHAR,
    template_id         BIGINT,
    creation_date       TIMESTAMP,
    update_date         TIMESTAMP,
    training_end_date   TIMESTAMP,
    is_deleted          BOOLEAN,
    version             INT    DEFAULT 0                      NOT NULL,
    PRIMARY KEY (id)
);
