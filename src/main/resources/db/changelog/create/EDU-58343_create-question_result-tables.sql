CREATE SEQUENCE IF NOT EXISTS question_result_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS question_result
(
    question_id BIGINT,
    activity_id BIGINT,
    point INT,
    CONSTRAINT question_activity_pk PRIMARY KEY (question_id, activity_id),
    CONSTRAINT question_activity_fk_question_id FOREIGN KEY (question_id)
            REFERENCES questions (id) ON DELETE CASCADE,
        CONSTRAINT question_activity_fk_activity_id FOREIGN KEY (activity_id)
            REFERENCES activities (id) ON DELETE CASCADE
);