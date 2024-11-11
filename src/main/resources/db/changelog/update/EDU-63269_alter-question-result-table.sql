ALTER TABLE question_result DROP CONSTRAINT IF EXISTS question_activity_fk_activity_id;
ALTER TABLE question_result DROP CONSTRAINT IF EXISTS question_activity_fk_question_id;

ALTER TABLE question_result RENAME COLUMN activity_id TO training_id;

ALTER TABLE question_result
    ADD CONSTRAINT question_fk_question_id FOREIGN KEY (question_id)
        REFERENCES questions (id) ON DELETE CASCADE;

ALTER TABLE question_result
    ADD CONSTRAINT training_fk_training_id FOREIGN KEY (training_id)
        REFERENCES training (id) ON DELETE CASCADE;

ALTER TABLE question_result RENAME CONSTRAINT question_activity_pk TO question_training_pk;