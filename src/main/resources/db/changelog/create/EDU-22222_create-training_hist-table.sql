-- Создание последовательности, если она не существует
CREATE SEQUENCE IF NOT EXISTS training_hist_seq START WITH 1 INCREMENT BY 1;

-- Создание таблицы, если она не существует
CREATE TABLE IF NOT EXISTS training_hist
(
    id BIGINT DEFAULT nextval('training_hist_seq') NOT NULL,
    request_id INTEGER NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    user_id UUID NOT NULL,
    action VARCHAR NOT NULL,
    field VARCHAR NOT NULL,
    prev_value VARCHAR,
    value_str VARCHAR NOT NULL,
    type VARCHAR NOT NULL,
    CONSTRAINT training_hist_pk PRIMARY KEY (id)
);