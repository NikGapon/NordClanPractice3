INSERT INTO users (id, first_name, last_name, surname, creation_date, update_date, is_deleted, is_root) VALUES
    (gen_random_uuid(),  'user1', 'user11', 'user111', '2000-11-11', '2002-12-11', false,false),
    (gen_random_uuid(),  'user2', 'user22', 'user222', '2002-11-11', '2004-12-11', false,false),
    (gen_random_uuid(), 'user3', 'user33', 'user333', '2004-11-11', '2005-12-11', true,false),
    (gen_random_uuid(),  'user4', 'user44', 'user444', '2000-11-11', '2002-12-11', false,false),
    (gen_random_uuid(), 'user5', 'user55', 'user555', '2002-11-11', '2004-12-11', false,false),
    (gen_random_uuid(),  'user6', 'user66', 'user666', '2004-11-11', '2005-12-11', true,false),
    (gen_random_uuid(),  'user7', 'user77', 'user777', '2000-11-11', '2002-12-11', false,false),
    (gen_random_uuid(),  'user8', 'user88', 'user888', '2002-11-11', '2004-12-11', false,false),
    (gen_random_uuid(),  'user9', 'user99', 'user999', '2004-11-11', '2005-12-11', true,false),
    ('3ccb1326-259f-44a4-8b66-3169f5da592c',  'user10', 'user1010', 'user101010', '2004-11-11', '2005-12-11', true,false);