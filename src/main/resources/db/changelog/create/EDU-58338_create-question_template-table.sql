CREATE TABLE IF NOT EXISTS question_template
(
    question_id BIGINT NOT NULL,
    template_id BIGINT NOT NULL,
    CONSTRAINT question_template_pk PRIMARY KEY (question_id, template_id),
    CONSTRAINT question_template_fk_question_id FOREIGN KEY (question_id)
        REFERENCES questions (id) ON DELETE CASCADE,
    CONSTRAINT question_template_fk_template_id FOREIGN KEY (template_id)
        REFERENCES template (id) ON DELETE CASCADE
);
