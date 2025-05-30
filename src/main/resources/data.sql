INSERT INTO role (id, name) VALUES (1, 'ADMIN')
    ON CONFLICT (id) DO NOTHING;

INSERT INTO role (id, name) VALUES (2, 'USER')
    ON CONFLICT (id) DO NOTHING; 