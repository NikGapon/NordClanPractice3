CREATE SEQUENCE IF NOT EXISTS training_request_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE IF NOT EXISTS request
(
    id                  BIGINT DEFAULT nextval('training_request_seq') NOT NULL,
    status_id           BIGINT,
    author_id           BIGINT,
    training_manager_id BIGINT,
    student_id          UUID,
    mentor_id           UUID,
    template_id         BIGINT,
    creation_date       TIMESTAMP,
    update_date         TIMESTAMP,
    end_date            TIMESTAMP,
    is_deleted          BOOLEAN,
    version             INT    DEFAULT 0                      NOT NULL,
    PRIMARY KEY (id)
);
