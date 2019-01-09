INSERT INTO role (id, role_name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO role (id, role_name) VALUES (2, 'ROLE_USER');

INSERT INTO user (username, password, role_id) VALUES ('admin', '$2a$11$70Xpp7/vQsnlZNZMQSx/6OHusrhf8dY8B5YoR6N7TfwNxrMJp4OcK', 1);
INSERT INTO user (username, password, role_id) VALUES ('user', '$2b$10$kaYCBYz2ozkpEAtyw6NE3u.i.8rQUvkpUax86Uy4SvLdU79tBUNaO', 2);