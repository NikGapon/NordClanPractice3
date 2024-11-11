CREATE SEQUENCE IF NOT EXISTS template_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS template
(
    id BIGINT DEFAULT nextval('template_seq') NOT NULL,
    name VARCHAR(200),
    description TEXT,
    threshold INTEGER,
    CONSTRAINT template_pk PRIMARY KEY (id)
);