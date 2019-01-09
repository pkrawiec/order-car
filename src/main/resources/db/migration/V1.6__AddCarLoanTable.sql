CREATE TABLE `car_loan` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date_from` DATETIME NOT NULL,
  `date_to` DATETIME NOT NULL,
  `car_id` INT NOT NULL,
  `client_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_car_loan_car_idx` (`car_id` ASC),
  INDEX `fk_car_loan_client_idx` (`client_id` ASC),
  CONSTRAINT `fk_car_loan_car`
    FOREIGN KEY (`car_id`)
    REFERENCES `car` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_car_loan_client`
    FOREIGN KEY (`client_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)