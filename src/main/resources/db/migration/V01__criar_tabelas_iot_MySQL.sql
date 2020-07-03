-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema tccfacidapi
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema tccfacidapi
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `tccfacidapi` ;
USE `tccfacidapi` ;

-- -----------------------------------------------------
-- Table `tccfacidapi`.`raw_bodies`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tccfacidapi`.`raw_bodies` (
  `id` BIGINT(50) NOT NULL AUTO_INCREMENT,
  `app_id` VARCHAR(36) NOT NULL,
  `dev_id` VARCHAR(36) NOT NULL,
  `hardware_serial` VARCHAR(16) NULL,
  `port` INT(3) NOT NULL,
  `counter` BIGINT(50) NULL,
  `is_retry` TINYINT(1) NULL,
  `confirmed` TINYINT(1) NOT NULL,
  `payload_raw` VARCHAR(100) NULL,
  `schedule` VARCHAR(7) NULL,
  `filter` VARCHAR(10) NOT NULL,
  `ack` VARCHAR(19) NULL,
  `http_status` INT(3) NULL,
  `downlink_url` VARCHAR(200) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tccfacidapi`.`payload_fields`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tccfacidapi`.`payload_fields` (
  `id` BIGINT(50) NOT NULL AUTO_INCREMENT,
  `text` VARCHAR(50) NULL,
  `raw_bodies_id` BIGINT(50) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_payload_fields_raw_bodies1_idx` (`raw_bodies_id` ASC),
  CONSTRAINT `fk_payload_fields_raw_bodies1`
    FOREIGN KEY (`raw_bodies_id`)
    REFERENCES `tccfacidapi`.`raw_bodies` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tccfacidapi`.`metadata`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tccfacidapi`.`metadata` (
  `id` BIGINT(50) NOT NULL AUTO_INCREMENT,
  `time` VARCHAR(30) NOT NULL,
  `frequency` FLOAT NOT NULL,
  `modulation` VARCHAR(4) NOT NULL,
  `data_rate` VARCHAR(9) NOT NULL,
  `bit_rate` INT(5) NULL,
  `coding_rate` VARCHAR(3) NOT NULL,
  `latitude` FLOAT NULL,
  `longitude` FLOAT NULL,
  `altitude` INT(3) NULL,
  `raw_bodies_id` BIGINT(50) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_metadata_raw_bodies1_idx` (`raw_bodies_id` ASC),
  CONSTRAINT `fk_metadata_raw_bodies1`
    FOREIGN KEY (`raw_bodies_id`)
    REFERENCES `tccfacidapi`.`raw_bodies` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tccfacidapi`.`gateways`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tccfacidapi`.`gateways` (
  `id` BIGINT(50) NOT NULL AUTO_INCREMENT,
  `gtw_id` VARCHAR(20) NOT NULL,
  `timestamp` VARCHAR(10) NOT NULL,
  `time` VARCHAR(30) NOT NULL,
  `channel` INT(1) NOT NULL,
  `rssi` INT(3) NOT NULL,
  `snr` FLOAT NOT NULL,
  `rf_chain` INT(1) NOT NULL,
  `latitude` FLOAT NOT NULL,
  `longitude` FLOAT NOT NULL,
  `altitude` INT(3) NOT NULL,
  `metadata_id` BIGINT(50) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_gateways_metadata_idx` (`metadata_id` ASC),
  CONSTRAINT `fk_gateways_metadata`
    FOREIGN KEY (`metadata_id`)
    REFERENCES `tccfacidapi`.`metadata` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

