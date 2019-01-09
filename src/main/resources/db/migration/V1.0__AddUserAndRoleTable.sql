CREATE TABLE `role` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `user` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`username` varchar(255),
	`password` varchar(255),
	`role_id` int(11),
	PRIMARY KEY (`id`),
    CONSTRAINT UQ_username UNIQUE (username),
	CONSTRAINT FKrhfovtciq1l558cw6udg0h0d3 
		FOREIGN KEY(role_id) 
		REFERENCES role (id) 
		ON DELETE NO ACTION
		ON UPDATE CASCADE
);