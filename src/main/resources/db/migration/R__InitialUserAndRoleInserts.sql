INSERT INTO role (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO role (id, name) VALUES (2, 'ROLE_USER');

-- pass same as login

INSERT INTO user(username, password, role_id) VALUES
	('admin', '$2a$11$70Xpp7/vQsnlZNZMQSx/6OHusrhf8dY8B5YoR6N7TfwNxrMJp4OcK', 1);

INSERT INTO user(username, password, role_id) VALUES
	('user', '$2a$10$6jsH8LVkJHiQTLfvo0hA1.kmoMSz2behyDBUJmdQUXddI8AYygZcC', 2);