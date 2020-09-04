-- MySQL Script generated by MySQL Workbench
-- qua 02 set 2020 20:42:41
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';


-- -----------------------------------------------------
-- Table `embedded`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `embedded` ;

CREATE TABLE IF NOT EXISTS `embedded` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `mac_address` VARCHAR(17) NOT NULL,
  `display_name` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `nro_mac_UNIQUE` (`mac_address` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `data`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `data` ;

CREATE TABLE IF NOT EXISTS `data` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `embedded_id` BIGINT UNSIGNED NOT NULL,
  `timestamp` TIMESTAMP NOT NULL,
  `luminosity` VARCHAR(45) NULL,
  `humidity` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_action_embedded_idx` (`embedded_id` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  CONSTRAINT `fk_action_embedded`
    FOREIGN KEY (`embedded_id`)
    REFERENCES `embedded` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `schedule`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `schedule` ;

CREATE TABLE IF NOT EXISTS `schedule` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `cron` VARCHAR(45) NOT NULL,
  `description` VARCHAR(45) NULL,
  `embedded_id` BIGINT UNSIGNED NOT NULL,
  `active` TINYINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_schedule_embedded1_idx` (`embedded_id` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  CONSTRAINT `fk_schedule_embedded1`
    FOREIGN KEY (`embedded_id`)
    REFERENCES `embedded` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user` ;

CREATE TABLE IF NOT EXISTS `user` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(200) NOT NULL,
  `password` TEXT NOT NULL,
  `mobile_id` VARCHAR(500) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`login` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `action`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `action` ;

CREATE TABLE IF NOT EXISTS `action` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `schedule_id` BIGINT UNSIGNED NOT NULL,
  `data` TEXT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_action_schedule1_idx` (`schedule_id` ASC) VISIBLE,
  CONSTRAINT `fk_action_schedule1`
    FOREIGN KEY (`schedule_id`)
    REFERENCES `schedule` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `user_has_embedded`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user_has_embedded` ;

CREATE TABLE IF NOT EXISTS `user_has_embedded` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `embedded_id` BIGINT UNSIGNED NOT NULL,
  `user_id` BIGINT UNSIGNED NOT NULL,
  INDEX `fk_user_has_embedded_embedded1_idx` (`embedded_id` ASC) VISIBLE,
  INDEX `fk_user_has_embedded_user1_idx` (`user_id` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  CONSTRAINT `fk_user_has_embedded_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_embedded_embedded1`
    FOREIGN KEY (`embedded_id`)
    REFERENCES `embedded` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
