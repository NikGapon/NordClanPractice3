ALTER TABLE questions
    RENAME COLUMN lvl TO question_levels;

ALTER TABLE questions
ALTER COLUMN question_levels TYPE jsonb USING question_levels::jsonb;