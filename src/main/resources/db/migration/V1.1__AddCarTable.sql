CREATE TABLE `car` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(255),
	`production_year` int(5) NOT NULL,
	`model` VARCHAR(255) NOT NULL,
	`manufacturer` VARCHAR(255) NOT NULL,
	`owner_id` int(11),
	PRIMARY KEY (`id`),
	CONSTRAINT FKrhfosfdhrey54ww3ecw6udg0h0d3 
		FOREIGN KEY(owner_id) 
		REFERENCES `user` (id) 
		ON DELETE NO ACTION
		ON UPDATE CASCADE
);