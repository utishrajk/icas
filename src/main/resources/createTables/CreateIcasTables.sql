
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `icas` DEFAULT CHARACTER SET utf8 ;
USE `icas` ;

-- -----------------------------------------------------
-- Table `icas`.`security_questions_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`security_questions_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`provider_taxonomy_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`provider_taxonomy_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`administrative_gender_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`administrative_gender_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`abstract_provider`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`abstract_provider` (
  `dtype` VARCHAR(31) NOT NULL,
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `enumeration_date` VARCHAR(30) NULL DEFAULT NULL,
  `first_line_mailing_address` VARCHAR(30) NOT NULL,
  `first_line_practice_location_address` VARCHAR(30) NULL DEFAULT NULL,
  `last_update_date` VARCHAR(30) NULL DEFAULT NULL,
  `mailing_address_city_name` VARCHAR(30) NOT NULL,
  `mailing_address_country_code` VARCHAR(30) NOT NULL,
  `mailing_address_fax_number` VARCHAR(30) NULL DEFAULT NULL,
  `mailing_address_postal_code` VARCHAR(30) NOT NULL,
  `mailing_address_state_name` VARCHAR(30) NULL DEFAULT NULL,
  `mailing_address_telephone_number` VARCHAR(30) NULL DEFAULT NULL,
  `nonusstate` VARCHAR(30) NULL DEFAULT NULL,
  `npi` VARCHAR(30) NOT NULL,
  `practice_location_address_city_name` VARCHAR(30) NULL DEFAULT NULL,
  `practice_location_address_country_code` VARCHAR(30) NULL DEFAULT NULL,
  `practice_location_address_fax_number` VARCHAR(30) NULL DEFAULT NULL,
  `practice_location_address_postal_code` VARCHAR(30) NULL DEFAULT NULL,
  `practice_location_address_state_name` VARCHAR(30) NULL DEFAULT NULL,
  `practice_location_address_telephone_number` VARCHAR(30) NULL DEFAULT NULL,
  `provider_entity_type` INT(11) NULL DEFAULT NULL,
  `second_line_mailing_address` VARCHAR(30) NULL DEFAULT NULL,
  `second_line_practice_location_address` VARCHAR(30) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  `website` VARCHAR(255) NULL DEFAULT NULL,
  `account_non_locked` BIT(1) NULL DEFAULT NULL,
  `answer1` VARCHAR(255) NULL DEFAULT NULL,
  `answer2` VARCHAR(255) NULL DEFAULT NULL,
  `date_of_birth` DATETIME NULL DEFAULT NULL,
  `first_name` VARCHAR(50) NULL DEFAULT NULL,
  `is_verified` BIT(1) NULL DEFAULT NULL,
  `last_name` VARCHAR(50) NULL DEFAULT NULL,
  `middle_name` VARCHAR(30) NULL DEFAULT NULL,
  `name_prefix_code` VARCHAR(30) NULL DEFAULT NULL,
  `name_suffix` VARCHAR(30) NULL DEFAULT NULL,
  `user_name` VARCHAR(50) NULL DEFAULT NULL,
  `authorized_official_first_name` VARCHAR(30) NULL DEFAULT NULL,
  `authorized_official_last_name` VARCHAR(30) NULL DEFAULT NULL,
  `authorized_official_name_prefix` VARCHAR(30) NULL DEFAULT NULL,
  `authorized_official_telephone_number` VARCHAR(30) NULL DEFAULT NULL,
  `authorized_official_title` VARCHAR(30) NULL DEFAULT NULL,
  `org_name` VARCHAR(30) NULL DEFAULT NULL,
  `other_org_name` VARCHAR(30) NULL DEFAULT NULL,
  `provider_taxonomy_code` BIGINT(20) NULL DEFAULT NULL,
  `administrative_gender_code` BIGINT(20) NULL DEFAULT NULL,
  `question1` BIGINT(20) NULL DEFAULT NULL,
  `question2` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_bb4kbd862fnafgqk26964bvit` (`email` ASC),
  UNIQUE INDEX `UK_thwi4ibpanesigdfbx3iqt0bg` (`user_name` ASC),
  INDEX `FK_57fwnct73hpwqlcgobifdb8v2` (`provider_taxonomy_code` ASC),
  INDEX `FK_m823gm1y4wj9fhk28peaptn6y` (`administrative_gender_code` ASC),
  INDEX `FK_72w8u2rlpoaos3risnanvv7f7` (`question1` ASC),
  INDEX `FK_6w6sg6m7b9djb88hjy0uwvqa6` (`question2` ASC),
  CONSTRAINT `FK_6w6sg6m7b9djb88hjy0uwvqa6`
    FOREIGN KEY (`question2`)
    REFERENCES `icas`.`security_questions_code` (`id`),
  CONSTRAINT `FK_57fwnct73hpwqlcgobifdb8v2`
    FOREIGN KEY (`provider_taxonomy_code`)
    REFERENCES `icas`.`provider_taxonomy_code` (`id`),
  CONSTRAINT `FK_72w8u2rlpoaos3risnanvv7f7`
    FOREIGN KEY (`question1`)
    REFERENCES `icas`.`security_questions_code` (`id`),
  CONSTRAINT `FK_m823gm1y4wj9fhk28peaptn6y`
    FOREIGN KEY (`administrative_gender_code`)
    REFERENCES `icas`.`administrative_gender_code` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`act_status_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`act_status_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`activity`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`activity` (
  `activity_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `action` VARCHAR(255) NOT NULL,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `ip_address` VARCHAR(255) NULL DEFAULT NULL,
  `timestamp` DATETIME NOT NULL,
  `username` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`activity_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 569
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`address_use_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`address_use_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`adverse_event_type_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`adverse_event_type_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`allergen_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`allergen_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`telecom_use_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`telecom_use_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`country_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`country_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`state_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`state_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`marital_status_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`marital_status_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`language_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`language_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`ethnic_group_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`ethnic_group_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`religious_affiliation_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`religious_affiliation_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`race_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`race_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`patient`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`patient` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `city` VARCHAR(255) NULL DEFAULT NULL,
  `nonusstate` VARCHAR(255) NULL DEFAULT NULL,
  `postal_code` VARCHAR(255) NULL DEFAULT NULL,
  `street_address_line` VARCHAR(255) NULL DEFAULT NULL,
  `birth_day` DATETIME NULL DEFAULT NULL,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `first_name` VARCHAR(30) NOT NULL,
  `last_name` VARCHAR(30) NOT NULL,
  `medical_record_number` VARCHAR(30) NULL DEFAULT NULL,
  `patient_id_number` VARCHAR(30) NULL DEFAULT NULL,
  `prefix` VARCHAR(30) NULL DEFAULT NULL,
  `social_security_number` VARCHAR(255) NULL DEFAULT NULL,
  `telephone` VARCHAR(255) NULL DEFAULT NULL,
  `username` VARCHAR(255) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  `address_use_code` BIGINT(20) NULL DEFAULT NULL,
  `country_code` BIGINT(20) NULL DEFAULT NULL,
  `state_code` BIGINT(20) NULL DEFAULT NULL,
  `administrative_gender_code` BIGINT(20) NULL DEFAULT NULL,
  `ethnic_group_code` BIGINT(20) NULL DEFAULT NULL,
  `language_code` BIGINT(20) NULL DEFAULT NULL,
  `marital_status_code` BIGINT(20) NULL DEFAULT NULL,
  `race_code` BIGINT(20) NULL DEFAULT NULL,
  `religious_affiliation_code` BIGINT(20) NULL DEFAULT NULL,
  `telecom_use_code` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_1orqrmpirngnbw03ay0pepv4d` (`address_use_code` ASC),
  INDEX `FK_2kdw3ok799ys1j0qgem1rpy4f` (`country_code` ASC),
  INDEX `FK_8k9onlusuixbxj4f7wrneb6n0` (`state_code` ASC),
  INDEX `FK_6vxgani9f2dr9sj48x08slqis` (`administrative_gender_code` ASC),
  INDEX `FK_igp5a99ss7r7ukdogxchk4ebw` (`ethnic_group_code` ASC),
  INDEX `FK_hmuafip3dfiiqd153o743f4pg` (`language_code` ASC),
  INDEX `FK_eqet7itg5qkaci24nnxw14y3s` (`marital_status_code` ASC),
  INDEX `FK_thtg84kyv9a5bv1aadqe8cwn3` (`race_code` ASC),
  INDEX `FK_pnsp3hikrrvqao8b6mg4uet2g` (`religious_affiliation_code` ASC),
  INDEX `FK_ss7ageqwyctto7xxppr5cx972` (`telecom_use_code` ASC),
  CONSTRAINT `FK_ss7ageqwyctto7xxppr5cx972`
    FOREIGN KEY (`telecom_use_code`)
    REFERENCES `icas`.`telecom_use_code` (`id`),
  CONSTRAINT `FK_1orqrmpirngnbw03ay0pepv4d`
    FOREIGN KEY (`address_use_code`)
    REFERENCES `icas`.`address_use_code` (`id`),
  CONSTRAINT `FK_2kdw3ok799ys1j0qgem1rpy4f`
    FOREIGN KEY (`country_code`)
    REFERENCES `icas`.`country_code` (`id`),
  CONSTRAINT `FK_6vxgani9f2dr9sj48x08slqis`
    FOREIGN KEY (`administrative_gender_code`)
    REFERENCES `icas`.`administrative_gender_code` (`id`),
  CONSTRAINT `FK_8k9onlusuixbxj4f7wrneb6n0`
    FOREIGN KEY (`state_code`)
    REFERENCES `icas`.`state_code` (`id`),
  CONSTRAINT `FK_eqet7itg5qkaci24nnxw14y3s`
    FOREIGN KEY (`marital_status_code`)
    REFERENCES `icas`.`marital_status_code` (`id`),
  CONSTRAINT `FK_hmuafip3dfiiqd153o743f4pg`
    FOREIGN KEY (`language_code`)
    REFERENCES `icas`.`language_code` (`id`),
  CONSTRAINT `FK_igp5a99ss7r7ukdogxchk4ebw`
    FOREIGN KEY (`ethnic_group_code`)
    REFERENCES `icas`.`ethnic_group_code` (`id`),
  CONSTRAINT `FK_pnsp3hikrrvqao8b6mg4uet2g`
    FOREIGN KEY (`religious_affiliation_code`)
    REFERENCES `icas`.`religious_affiliation_code` (`id`),
  CONSTRAINT `FK_thtg84kyv9a5bv1aadqe8cwn3`
    FOREIGN KEY (`race_code`)
    REFERENCES `icas`.`race_code` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 266
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`allergy_severity_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`allergy_severity_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`allergy_reaction_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`allergy_reaction_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`allergy`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`allergy` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `allergy_end_date` DATETIME NULL DEFAULT NULL,
  `allergy_start_date` DATETIME NULL DEFAULT NULL,
  `note` VARCHAR(255) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  `adverse_event_type_code` BIGINT(20) NULL DEFAULT NULL,
  `allergen_code` BIGINT(20) NULL DEFAULT NULL,
  `allergy_reaction_code` BIGINT(20) NULL DEFAULT NULL,
  `allergy_severity_code` BIGINT(20) NULL DEFAULT NULL,
  `allergy_status_code` BIGINT(20) NULL DEFAULT NULL,
  `patient` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_9exnpp3my9wu25p95w2gj3vj0` (`adverse_event_type_code` ASC),
  INDEX `FK_24v9ckh877tue1fj3iqn4ibht` (`allergen_code` ASC),
  INDEX `FK_9g331itvojn3ri0qu4pt1plq4` (`allergy_reaction_code` ASC),
  INDEX `FK_2ka3u4op5in8bq55iaq1vkvd7` (`allergy_severity_code` ASC),
  INDEX `FK_p05i0nrc52rc78blqf0dv6sy` (`allergy_status_code` ASC),
  INDEX `FK_qvh1bh9lw4u0g5lnxb7m7hm6n` (`patient` ASC),
  CONSTRAINT `FK_qvh1bh9lw4u0g5lnxb7m7hm6n`
    FOREIGN KEY (`patient`)
    REFERENCES `icas`.`patient` (`id`),
  CONSTRAINT `FK_24v9ckh877tue1fj3iqn4ibht`
    FOREIGN KEY (`allergen_code`)
    REFERENCES `icas`.`allergen_code` (`id`),
  CONSTRAINT `FK_2ka3u4op5in8bq55iaq1vkvd7`
    FOREIGN KEY (`allergy_severity_code`)
    REFERENCES `icas`.`allergy_severity_code` (`id`),
  CONSTRAINT `FK_9exnpp3my9wu25p95w2gj3vj0`
    FOREIGN KEY (`adverse_event_type_code`)
    REFERENCES `icas`.`adverse_event_type_code` (`id`),
  CONSTRAINT `FK_9g331itvojn3ri0qu4pt1plq4`
    FOREIGN KEY (`allergy_reaction_code`)
    REFERENCES `icas`.`allergy_reaction_code` (`id`),
  CONSTRAINT `FK_p05i0nrc52rc78blqf0dv6sy`
    FOREIGN KEY (`allergy_status_code`)
    REFERENCES `icas`.`act_status_code` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`unit_of_measure_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`unit_of_measure_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`assessment_scale_observation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`assessment_scale_observation` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(255) NULL DEFAULT NULL,
  `code_system` VARCHAR(255) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(255) NULL DEFAULT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(255) NULL DEFAULT NULL,
  `original_text` VARCHAR(255) NULL DEFAULT NULL,
  `assessment_scale_observation_date_time` DATETIME NULL DEFAULT NULL,
  `measured_value` DOUBLE NULL DEFAULT NULL,
  `derivation_expr` VARCHAR(255) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  `assessment_scale_observation_status_code` BIGINT(20) NULL DEFAULT NULL,
  `unit_of_measure_code` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_sf817frxy98q6ixy64hcao2je` (`assessment_scale_observation_status_code` ASC),
  INDEX `FK_plkun5biv6wkuj9hdw85fn1bq` (`unit_of_measure_code` ASC),
  CONSTRAINT `FK_plkun5biv6wkuj9hdw85fn1bq`
    FOREIGN KEY (`unit_of_measure_code`)
    REFERENCES `icas`.`unit_of_measure_code` (`id`),
  CONSTRAINT `FK_sf817frxy98q6ixy64hcao2je`
    FOREIGN KEY (`assessment_scale_observation_status_code`)
    REFERENCES `icas`.`act_status_code` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`assessment_scale_supporting_observation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`assessment_scale_supporting_observation` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(255) NULL DEFAULT NULL,
  `code_system` VARCHAR(255) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(255) NULL DEFAULT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(255) NULL DEFAULT NULL,
  `original_text` VARCHAR(255) NULL DEFAULT NULL,
  `assessment_scale_supporting_observation_date_time` DATETIME NOT NULL,
  `measured_value` DOUBLE NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  `assessment_scale_supporting_observation_status_code` BIGINT(20) NULL DEFAULT NULL,
  `unit_of_measure_code` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_2loiqd5pdh26m4an3vgnykek9` (`assessment_scale_supporting_observation_status_code` ASC),
  INDEX `FK_orgravu58s8lmf8pjqdndmab5` (`unit_of_measure_code` ASC),
  CONSTRAINT `FK_orgravu58s8lmf8pjqdndmab5`
    FOREIGN KEY (`unit_of_measure_code`)
    REFERENCES `icas`.`unit_of_measure_code` (`id`),
  CONSTRAINT `FK_2loiqd5pdh26m4an3vgnykek9`
    FOREIGN KEY (`assessment_scale_supporting_observation_status_code`)
    REFERENCES `icas`.`act_status_code` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`assessment_scale_assessment_scale_supporting_observations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`assessment_scale_assessment_scale_supporting_observations` (
  `assessment_scale_observation` BIGINT(20) NOT NULL,
  `assessment_scale_supporting_observations` BIGINT(20) NOT NULL,
  PRIMARY KEY (`assessment_scale_observation`, `assessment_scale_supporting_observations`),
  INDEX `FK_1t90qikmppb2q2v4cvv8wkr49` (`assessment_scale_supporting_observations` ASC),
  CONSTRAINT `FK_a0rb29n4qa862r3hv7e9f4ed2`
    FOREIGN KEY (`assessment_scale_observation`)
    REFERENCES `icas`.`assessment_scale_observation` (`id`),
  CONSTRAINT `FK_1t90qikmppb2q2v4cvv8wkr49`
    FOREIGN KEY (`assessment_scale_supporting_observations`)
    REFERENCES `icas`.`assessment_scale_supporting_observation` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`authority`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`authority` (
  `name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`name`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`body_site_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`body_site_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`cgi_i_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`cgi_i_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`cgi_s_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`cgi_s_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`credential`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`credential` (
  `credential_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `created_date` DATETIME NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `user_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`credential_id`),
  INDEX `FK_fpa1gd4lqje9x97np2thmqmk1` (`user_id` ASC),
  CONSTRAINT `FK_fpa1gd4lqje9x97np2thmqmk1`
    FOREIGN KEY (`user_id`)
    REFERENCES `icas`.`abstract_provider` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`document_type_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`document_type_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`document`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`document` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `document_provenance` VARCHAR(255) NULL DEFAULT NULL,
  `content` LONGTEXT NULL DEFAULT NULL,
  `content_standard_name` VARCHAR(255) NULL DEFAULT NULL,
  `content_standard_version` VARCHAR(255) NULL DEFAULT NULL,
  `content_type` VARCHAR(255) NOT NULL,
  `description` VARCHAR(500) NULL DEFAULT NULL,
  `document_size` BIGINT(20) NOT NULL,
  `document_url` VARCHAR(250) NULL DEFAULT NULL,
  `name` VARCHAR(30) NOT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  `document_type_code` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_ta07sh0cxthx8eo7wpp51f3e2` (`document_type_code` ASC),
  CONSTRAINT `FK_ta07sh0cxthx8eo7wpp51f3e2`
    FOREIGN KEY (`document_type_code`)
    REFERENCES `icas`.`document_type_code` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`encounter`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`encounter` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `encounter_event_time` DATETIME NULL DEFAULT NULL,
  `encounter_free_text` VARCHAR(255) NULL DEFAULT NULL,
  `code` VARCHAR(255) NULL DEFAULT NULL,
  `code_system` VARCHAR(255) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(255) NULL DEFAULT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(255) NULL DEFAULT NULL,
  `original_text` VARCHAR(255) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  `patient` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_5i0qokxwr0wga0qacrn9nwo6y` (`patient` ASC),
  CONSTRAINT `FK_5i0qokxwr0wga0qacrn9nwo6y`
    FOREIGN KEY (`patient`)
    REFERENCES `icas`.`patient` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`encounter_performers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`encounter_performers` (
  `encounter` BIGINT(20) NOT NULL,
  `performers` BIGINT(20) NOT NULL,
  PRIMARY KEY (`encounter`, `performers`),
  INDEX `FK_jx49s7f365x7hhmibuvsc4kk` (`performers` ASC),
  CONSTRAINT `FK_8kvx550bbckaiacerun43369t`
    FOREIGN KEY (`encounter`)
    REFERENCES `icas`.`encounter` (`id`),
  CONSTRAINT `FK_jx49s7f365x7hhmibuvsc4kk`
    FOREIGN KEY (`performers`)
    REFERENCES `icas`.`abstract_provider` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`facility_type_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`facility_type_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`feedback_ratings_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`feedback_ratings_code` (
  `rating_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `rating_value` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`rating_id`),
  UNIQUE INDEX `UK_4fyxgw6c2ccahwfaxmke6i9yy` (`rating_value` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`feedback_services_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`feedback_services_code` (
  `service_id` BIGINT(20) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`service_id`),
  UNIQUE INDEX `UK_to30ds3jq30dbs8wldf66w5ys` (`name` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`functional_status_problem_observation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`functional_status_problem_observation` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(255) NULL DEFAULT NULL,
  `code_system` VARCHAR(255) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(255) NULL DEFAULT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(255) NULL DEFAULT NULL,
  `original_text` VARCHAR(255) NULL DEFAULT NULL,
  `functional_status_problem_observation_date_time` DATETIME NOT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  `functional_status_problem_observation_status_code` BIGINT(20) NULL DEFAULT NULL,
  `patient` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_oxvvtg7isqyqakgix99co8w5x` (`functional_status_problem_observation_status_code` ASC),
  INDEX `FK_chg5eo8r2tc037bj07gp36vle` (`patient` ASC),
  CONSTRAINT `FK_chg5eo8r2tc037bj07gp36vle`
    FOREIGN KEY (`patient`)
    REFERENCES `icas`.`patient` (`id`),
  CONSTRAINT `FK_oxvvtg7isqyqakgix99co8w5x`
    FOREIGN KEY (`functional_status_problem_observation_status_code`)
    REFERENCES `icas`.`act_status_code` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`functional_status_problem_assessment_scale_observations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`functional_status_problem_assessment_scale_observations` (
  `functional_status_problem_observation` BIGINT(20) NOT NULL,
  `assessment_scale_observations` BIGINT(20) NOT NULL,
  PRIMARY KEY (`functional_status_problem_observation`, `assessment_scale_observations`),
  INDEX `FK_g7a1p2s50wjc9q3ohbyjmvxfp` (`assessment_scale_observations` ASC),
  CONSTRAINT `FK_m5p5utr5cparxfhd8uf60u3ds`
    FOREIGN KEY (`functional_status_problem_observation`)
    REFERENCES `icas`.`functional_status_problem_observation` (`id`),
  CONSTRAINT `FK_g7a1p2s50wjc9q3ohbyjmvxfp`
    FOREIGN KEY (`assessment_scale_observations`)
    REFERENCES `icas`.`assessment_scale_observation` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`functional_status_result_observation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`functional_status_result_observation` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(255) NULL DEFAULT NULL,
  `code_system` VARCHAR(255) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(255) NULL DEFAULT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(255) NULL DEFAULT NULL,
  `original_text` VARCHAR(255) NULL DEFAULT NULL,
  `functional_status_result_observation_date_time` DATETIME NOT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  `functional_status_result_observation_status_code` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_4rs0gfdk8h72wcqvbvjt14g82` (`functional_status_result_observation_status_code` ASC),
  CONSTRAINT `FK_4rs0gfdk8h72wcqvbvjt14g82`
    FOREIGN KEY (`functional_status_result_observation_status_code`)
    REFERENCES `icas`.`act_status_code` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`functional_status_result__assessment_scale_observations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`functional_status_result__assessment_scale_observations` (
  `functional_status_result_observation` BIGINT(20) NOT NULL,
  `assessment_scale_observations` BIGINT(20) NOT NULL,
  PRIMARY KEY (`functional_status_result_observation`, `assessment_scale_observations`),
  INDEX `FK_rbhpm4sh3rhmqflj28fbb2887` (`assessment_scale_observations` ASC),
  CONSTRAINT `FK_ocyh372s2cv7u1yc7mg1uebpr`
    FOREIGN KEY (`functional_status_result_observation`)
    REFERENCES `icas`.`functional_status_result_observation` (`id`),
  CONSTRAINT `FK_rbhpm4sh3rhmqflj28fbb2887`
    FOREIGN KEY (`assessment_scale_observations`)
    REFERENCES `icas`.`assessment_scale_observation` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`functional_status_result_organizer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`functional_status_result_organizer` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(255) NULL DEFAULT NULL,
  `code_system` VARCHAR(255) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(255) NULL DEFAULT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(255) NULL DEFAULT NULL,
  `original_text` VARCHAR(255) NULL DEFAULT NULL,
  `functional_status_result_organizer_date_time` DATETIME NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  `functional_status_result_organizer_status_code` BIGINT(20) NULL DEFAULT NULL,
  `patient` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_b9w7j8bl4lf07jm5r14308rw` (`functional_status_result_organizer_status_code` ASC),
  INDEX `FK_frv16n03tyfk7dvjqoj9oitih` (`patient` ASC),
  CONSTRAINT `FK_frv16n03tyfk7dvjqoj9oitih`
    FOREIGN KEY (`patient`)
    REFERENCES `icas`.`patient` (`id`),
  CONSTRAINT `FK_b9w7j8bl4lf07jm5r14308rw`
    FOREIGN KEY (`functional_status_result_organizer_status_code`)
    REFERENCES `icas`.`act_status_code` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`functional_status_result_organizer_observations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`functional_status_result_organizer_observations` (
  `functional_status_result_organizer` BIGINT(20) NOT NULL,
  `functional_status_result_observations` BIGINT(20) NOT NULL,
  PRIMARY KEY (`functional_status_result_organizer`, `functional_status_result_observations`),
  INDEX `FK_1edawot9ga7yu6qj5gwenlgsc` (`functional_status_result_observations` ASC),
  CONSTRAINT `FK_lq4uxm5u39epgtdeaj2ebllph`
    FOREIGN KEY (`functional_status_result_organizer`)
    REFERENCES `icas`.`functional_status_result_organizer` (`id`),
  CONSTRAINT `FK_1edawot9ga7yu6qj5gwenlgsc`
    FOREIGN KEY (`functional_status_result_observations`)
    REFERENCES `icas`.`functional_status_result_observation` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`hibernate_sequences`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`hibernate_sequences` (
  `sequence_name` VARCHAR(255) NULL DEFAULT NULL,
  `sequence_next_hi_value` INT(11) NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`hospital_discharge_instruction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`hospital_discharge_instruction` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `hospital_discharge_instruction_date_time` DATETIME NOT NULL,
  `hospital_discharge_instruction_text` LONGTEXT NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  `patient` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_h7s0ed26krn2ltx23qimw6wy7` (`patient` ASC),
  CONSTRAINT `FK_h7s0ed26krn2ltx23qimw6wy7`
    FOREIGN KEY (`patient`)
    REFERENCES `icas`.`patient` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`individual_provider_feedback_services`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`individual_provider_feedback_services` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `individualprovider_id` BIGINT(20) NULL DEFAULT NULL,
  `rating_id` BIGINT(20) NULL DEFAULT NULL,
  `service_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_r9pmi2exmsug14jl1gmwxu28y` (`individualprovider_id` ASC),
  INDEX `FK_s8xhaqspe3pcll095hh6m2w92` (`rating_id` ASC),
  INDEX `FK_ip0pkapi4n7m8gfsvyiygt7y0` (`service_id` ASC),
  CONSTRAINT `FK_ip0pkapi4n7m8gfsvyiygt7y0`
    FOREIGN KEY (`service_id`)
    REFERENCES `icas`.`feedback_services_code` (`service_id`),
  CONSTRAINT `FK_r9pmi2exmsug14jl1gmwxu28y`
    FOREIGN KEY (`individualprovider_id`)
    REFERENCES `icas`.`abstract_provider` (`id`),
  CONSTRAINT `FK_s8xhaqspe3pcll095hh6m2w92`
    FOREIGN KEY (`rating_id`)
    REFERENCES `icas`.`feedback_ratings_code` (`rating_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`int_group_to_message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`int_group_to_message` (
  `GROUP_KEY` CHAR(36) NOT NULL DEFAULT '',
  `MESSAGE_ID` CHAR(36) NOT NULL DEFAULT '',
  `REGION` VARCHAR(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`GROUP_KEY`, `MESSAGE_ID`, `REGION`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`int_message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`int_message` (
  `MESSAGE_ID` CHAR(36) NOT NULL DEFAULT '',
  `REGION` VARCHAR(100) NOT NULL DEFAULT '',
  `CREATED_DATE` DATETIME NOT NULL,
  `MESSAGE_BYTES` BLOB NULL DEFAULT NULL,
  PRIMARY KEY (`MESSAGE_ID`, `REGION`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`int_message_group`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`int_message_group` (
  `GROUP_KEY` CHAR(36) NOT NULL DEFAULT '',
  `REGION` VARCHAR(100) NOT NULL DEFAULT '',
  `MARKED` BIGINT(20) NULL DEFAULT NULL,
  `COMPLETE` BIGINT(20) NULL DEFAULT NULL,
  `LAST_RELEASED_SEQUENCE` BIGINT(20) NULL DEFAULT NULL,
  `CREATED_DATE` DATETIME NOT NULL,
  `UPDATED_DATE` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`GROUP_KEY`, `REGION`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`treatment_plan`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`treatment_plan` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `evidence` VARCHAR(255) NULL DEFAULT NULL,
  `goals_note` VARCHAR(255) NULL DEFAULT NULL,
  `long_term_goal` VARCHAR(255) NULL DEFAULT NULL,
  `objectives_note` VARCHAR(255) NULL DEFAULT NULL,
  `plan_create_time` DATETIME NULL DEFAULT NULL,
  `primary_diagnosis` VARCHAR(255) NULL DEFAULT NULL,
  `short_term_goal` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 14
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`intervention`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`intervention` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `cpt_code` VARCHAR(255) NULL DEFAULT NULL,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `notes` VARCHAR(255) NULL DEFAULT NULL,
  `resolution_date` DATETIME NULL DEFAULT NULL,
  `target_date` DATETIME NULL DEFAULT NULL,
  `treatmentplan` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_j78e3hdk43vl2jbqqem3up8dm` (`treatmentplan` ASC),
  CONSTRAINT `FK_j78e3hdk43vl2jbqqem3up8dm`
    FOREIGN KEY (`treatmentplan`)
    REFERENCES `icas`.`treatment_plan` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 14
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`language_ability_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`language_ability_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`language_proficiency_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`language_proficiency_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`login_attempt`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`login_attempt` (
  `attempt_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `attempts` INT(11) NOT NULL,
  `last_modified` DATETIME NULL DEFAULT NULL,
  `username` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`attempt_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`route_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`route_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`medication_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`medication_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`medication_delivery_method_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`medication_delivery_method_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`product_form_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`product_form_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`medication`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`medication` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `measured_value` DOUBLE NULL DEFAULT NULL,
  `free_text_sig` VARCHAR(250) NOT NULL,
  `medication_end_date` DATETIME NULL DEFAULT NULL,
  `medication_note` VARCHAR(255) NULL DEFAULT NULL,
  `medication_start_date` DATETIME NOT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  `body_site_code` BIGINT(20) NULL DEFAULT NULL,
  `unit_of_measure_code` BIGINT(20) NULL DEFAULT NULL,
  `medication_code` BIGINT(20) NULL DEFAULT NULL,
  `medication_delivery_method_code` BIGINT(20) NULL DEFAULT NULL,
  `medication_status_code` BIGINT(20) NULL DEFAULT NULL,
  `patient` BIGINT(20) NULL DEFAULT NULL,
  `product_form_code` BIGINT(20) NULL DEFAULT NULL,
  `route_code` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_gwhtsc5a707e3q55w6l5spfop` (`body_site_code` ASC),
  INDEX `FK_oa8wiav0v2fi5sft9q8gr7kt` (`unit_of_measure_code` ASC),
  INDEX `FK_drag6vxawvevvi469tqa991ns` (`medication_code` ASC),
  INDEX `FK_flfq5rqtudwx1qb95jrvb5ayo` (`medication_delivery_method_code` ASC),
  INDEX `FK_ok2aixxxox2n5y5kn8cy8wn3h` (`medication_status_code` ASC),
  INDEX `FK_ienfmk9j746w4aj0cp9fh272r` (`patient` ASC),
  INDEX `FK_s3534ii2ih4jr3t8muudoxra5` (`product_form_code` ASC),
  INDEX `FK_mwagwurf3xyxdxpc9ty3kce7y` (`route_code` ASC),
  CONSTRAINT `FK_mwagwurf3xyxdxpc9ty3kce7y`
    FOREIGN KEY (`route_code`)
    REFERENCES `icas`.`route_code` (`id`),
  CONSTRAINT `FK_drag6vxawvevvi469tqa991ns`
    FOREIGN KEY (`medication_code`)
    REFERENCES `icas`.`medication_code` (`id`),
  CONSTRAINT `FK_flfq5rqtudwx1qb95jrvb5ayo`
    FOREIGN KEY (`medication_delivery_method_code`)
    REFERENCES `icas`.`medication_delivery_method_code` (`id`),
  CONSTRAINT `FK_gwhtsc5a707e3q55w6l5spfop`
    FOREIGN KEY (`body_site_code`)
    REFERENCES `icas`.`body_site_code` (`id`),
  CONSTRAINT `FK_ienfmk9j746w4aj0cp9fh272r`
    FOREIGN KEY (`patient`)
    REFERENCES `icas`.`patient` (`id`),
  CONSTRAINT `FK_oa8wiav0v2fi5sft9q8gr7kt`
    FOREIGN KEY (`unit_of_measure_code`)
    REFERENCES `icas`.`unit_of_measure_code` (`id`),
  CONSTRAINT `FK_ok2aixxxox2n5y5kn8cy8wn3h`
    FOREIGN KEY (`medication_status_code`)
    REFERENCES `icas`.`act_status_code` (`id`),
  CONSTRAINT `FK_s3534ii2ih4jr3t8muudoxra5`
    FOREIGN KEY (`product_form_code`)
    REFERENCES `icas`.`product_form_code` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`medication_code_outcomes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`medication_code_outcomes` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`objectives`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`objectives` (
  `treatment_objectives` BIGINT(20) NOT NULL,
  `objectives` VARCHAR(255) NULL DEFAULT NULL,
  INDEX `FK_khio5g6of6goj7cinw91j5kjr` (`treatment_objectives` ASC),
  CONSTRAINT `FK_khio5g6of6goj7cinw91j5kjr`
    FOREIGN KEY (`treatment_objectives`)
    REFERENCES `icas`.`treatment_plan` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`service_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`service_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`organization_service`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`organization_service` (
  `orgainzation_id` BIGINT(20) NOT NULL,
  `service_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`orgainzation_id`, `service_id`),
  INDEX `FK_6xqb8w5q1gqryr9ipvt670tir` (`service_id` ASC),
  CONSTRAINT `FK_ee676l7de5lncer7aw30bc8c8`
    FOREIGN KEY (`orgainzation_id`)
    REFERENCES `icas`.`abstract_provider` (`id`),
  CONSTRAINT `FK_6xqb8w5q1gqryr9ipvt670tir`
    FOREIGN KEY (`service_id`)
    REFERENCES `icas`.`service_code` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`procedure_type_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`procedure_type_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`outcome`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`outcome` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `start_date` DATETIME NOT NULL,
  `symptoms` VARCHAR(30) NULL DEFAULT NULL,
  `tolerability` VARCHAR(30) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  `cgiicode` BIGINT(20) NULL DEFAULT NULL,
  `cgiscode` BIGINT(20) NULL DEFAULT NULL,
  `medication_code_outcome` BIGINT(20) NULL DEFAULT NULL,
  `patient` BIGINT(20) NULL DEFAULT NULL,
  `procedure_type` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_4vut1ub8iu3ge9coiu8o4qthl` (`cgiicode` ASC),
  INDEX `FK_9tfi5y2asne2a14hcsiwa85rt` (`cgiscode` ASC),
  INDEX `FK_qo4ov3guf78dwl4xkn3jhq415` (`medication_code_outcome` ASC),
  INDEX `FK_fjcq4tbcx4ep7v6d9295ce2ca` (`patient` ASC),
  INDEX `FK_ehqscpqru994l8x6yt2bippxi` (`procedure_type` ASC),
  CONSTRAINT `FK_ehqscpqru994l8x6yt2bippxi`
    FOREIGN KEY (`procedure_type`)
    REFERENCES `icas`.`procedure_type_code` (`id`),
  CONSTRAINT `FK_4vut1ub8iu3ge9coiu8o4qthl`
    FOREIGN KEY (`cgiicode`)
    REFERENCES `icas`.`cgi_i_code` (`id`),
  CONSTRAINT `FK_9tfi5y2asne2a14hcsiwa85rt`
    FOREIGN KEY (`cgiscode`)
    REFERENCES `icas`.`cgi_s_code` (`id`),
  CONSTRAINT `FK_fjcq4tbcx4ep7v6d9295ce2ca`
    FOREIGN KEY (`patient`)
    REFERENCES `icas`.`patient` (`id`),
  CONSTRAINT `FK_qo4ov3guf78dwl4xkn3jhq415`
    FOREIGN KEY (`medication_code_outcome`)
    REFERENCES `icas`.`medication_code_outcomes` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`patient_clinical_document_type_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`patient_clinical_document_type_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`patient_clinical_document`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`patient_clinical_document` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `document_provenance` VARCHAR(255) NULL DEFAULT NULL,
  `content` LONGTEXT NULL DEFAULT NULL,
  `content_standard_name` VARCHAR(255) NOT NULL,
  `content_standard_version` VARCHAR(255) NOT NULL,
  `content_type` VARCHAR(255) NOT NULL,
  `description` VARCHAR(500) NULL DEFAULT NULL,
  `document_size` BIGINT(20) NOT NULL,
  `document_url` VARCHAR(100) NULL DEFAULT NULL,
  `name` VARCHAR(30) NOT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  `patient` BIGINT(20) NULL DEFAULT NULL,
  `patient_clinical_document_type_code` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_lw93h5ntnrnao0k1avsn9r6kv` (`patient` ASC),
  INDEX `FK_5gy6xraf52o2qcx41apyogbbk` (`patient_clinical_document_type_code` ASC),
  CONSTRAINT `FK_5gy6xraf52o2qcx41apyogbbk`
    FOREIGN KEY (`patient_clinical_document_type_code`)
    REFERENCES `icas`.`patient_clinical_document_type_code` (`id`),
  CONSTRAINT `FK_lw93h5ntnrnao0k1avsn9r6kv`
    FOREIGN KEY (`patient`)
    REFERENCES `icas`.`patient` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`patient_goal`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`patient_goal` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `goal_achievement_target_time` DATETIME NULL DEFAULT NULL,
  `code` VARCHAR(255) NULL DEFAULT NULL,
  `code_system` VARCHAR(255) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(255) NULL DEFAULT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(255) NULL DEFAULT NULL,
  `original_text` VARCHAR(255) NULL DEFAULT NULL,
  `goal_pursuit_effective_time` DATETIME NULL DEFAULT NULL,
  `measured_value` DOUBLE NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  `body_site_code` BIGINT(20) NULL DEFAULT NULL,
  `patient` BIGINT(20) NULL DEFAULT NULL,
  `unit_of_measure_code` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_qjk45eokiwboskahyp1g6621u` (`body_site_code` ASC),
  INDEX `FK_ac7ox0xpayvlyrb6bskpg8bfs` (`patient` ASC),
  INDEX `FK_opdyast7f2fsl7lm62syxj7nk` (`unit_of_measure_code` ASC),
  CONSTRAINT `FK_opdyast7f2fsl7lm62syxj7nk`
    FOREIGN KEY (`unit_of_measure_code`)
    REFERENCES `icas`.`unit_of_measure_code` (`id`),
  CONSTRAINT `FK_ac7ox0xpayvlyrb6bskpg8bfs`
    FOREIGN KEY (`patient`)
    REFERENCES `icas`.`patient` (`id`),
  CONSTRAINT `FK_qjk45eokiwboskahyp1g6621u`
    FOREIGN KEY (`body_site_code`)
    REFERENCES `icas`.`body_site_code` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`patient_providers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`patient_providers` (
  `patient` BIGINT(20) NOT NULL,
  `providers` BIGINT(20) NOT NULL,
  PRIMARY KEY (`patient`, `providers`),
  UNIQUE INDEX `UK_e331byerr3upxyv814ligqqbs` (`providers` ASC),
  CONSTRAINT `FK_nn5paxva2wrv59odlck81e8k2`
    FOREIGN KEY (`patient`)
    REFERENCES `icas`.`patient` (`id`),
  CONSTRAINT `FK_e331byerr3upxyv814ligqqbs`
    FOREIGN KEY (`providers`)
    REFERENCES `icas`.`abstract_provider` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`plan_of_care`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`plan_of_care` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `plan_of_care_date_time` DATETIME NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  `patient` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_bup1ee14476atllg0oun78jgx` (`patient` ASC),
  CONSTRAINT `FK_bup1ee14476atllg0oun78jgx`
    FOREIGN KEY (`patient`)
    REFERENCES `icas`.`patient` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`procedure_observation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`procedure_observation` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `procedure_end_date` DATETIME NULL DEFAULT NULL,
  `procedure_start_date` DATETIME NOT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  `body_site_code` BIGINT(20) NULL DEFAULT NULL,
  `patient` BIGINT(20) NULL DEFAULT NULL,
  `procedure_status_code` BIGINT(20) NULL DEFAULT NULL,
  `procedure_type` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_6rly6t3sw4jl67eytaw8kb8b3` (`body_site_code` ASC),
  INDEX `FK_eynojcdb80cp12n6p4nw8l3k8` (`patient` ASC),
  INDEX `FK_6rw9jawbt094kds5axgveyvw` (`procedure_status_code` ASC),
  INDEX `FK_876xqk3l8epy6lu8xsc4x7sal` (`procedure_type` ASC),
  CONSTRAINT `FK_876xqk3l8epy6lu8xsc4x7sal`
    FOREIGN KEY (`procedure_type`)
    REFERENCES `icas`.`procedure_type_code` (`id`),
  CONSTRAINT `FK_6rly6t3sw4jl67eytaw8kb8b3`
    FOREIGN KEY (`body_site_code`)
    REFERENCES `icas`.`body_site_code` (`id`),
  CONSTRAINT `FK_6rw9jawbt094kds5axgveyvw`
    FOREIGN KEY (`procedure_status_code`)
    REFERENCES `icas`.`act_status_code` (`id`),
  CONSTRAINT `FK_eynojcdb80cp12n6p4nw8l3k8`
    FOREIGN KEY (`patient`)
    REFERENCES `icas`.`patient` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`plan_of_care_interventions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`plan_of_care_interventions` (
  `plan_of_care` BIGINT(20) NOT NULL,
  `interventions` BIGINT(20) NOT NULL,
  PRIMARY KEY (`plan_of_care`, `interventions`),
  INDEX `FK_4mfa6oswubte842jt2svwv4sb` (`interventions` ASC),
  CONSTRAINT `FK_7f5ci3ejc10ho0i65r5mbftjb`
    FOREIGN KEY (`plan_of_care`)
    REFERENCES `icas`.`plan_of_care` (`id`),
  CONSTRAINT `FK_4mfa6oswubte842jt2svwv4sb`
    FOREIGN KEY (`interventions`)
    REFERENCES `icas`.`procedure_observation` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`plan_of_care_patient_goals`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`plan_of_care_patient_goals` (
  `plan_of_care` BIGINT(20) NOT NULL,
  `patient_goals` BIGINT(20) NOT NULL,
  PRIMARY KEY (`plan_of_care`, `patient_goals`),
  INDEX `FK_573j5fm5ag5ho7qjjx4mawt3y` (`patient_goals` ASC),
  CONSTRAINT `FK_inydao3jadem8ky3l16vel9c5`
    FOREIGN KEY (`plan_of_care`)
    REFERENCES `icas`.`plan_of_care` (`id`),
  CONSTRAINT `FK_573j5fm5ag5ho7qjjx4mawt3y`
    FOREIGN KEY (`patient_goals`)
    REFERENCES `icas`.`patient_goal` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`problem_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`problem_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`problem`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`problem` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `age_at_on_set` INT(11) NULL DEFAULT NULL,
  `problem_end_date` DATETIME NULL DEFAULT NULL,
  `problem_start_date` DATETIME NOT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  `patient` BIGINT(20) NULL DEFAULT NULL,
  `problem_code` BIGINT(20) NULL DEFAULT NULL,
  `problem_status_code` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_co992gsensot0eyu764x9131o` (`patient` ASC),
  INDEX `FK_kpj6s1hrhhdneorsopcb9mgwr` (`problem_code` ASC),
  INDEX `FK_42ctft5xvlacwco5djfdkatcy` (`problem_status_code` ASC),
  CONSTRAINT `FK_42ctft5xvlacwco5djfdkatcy`
    FOREIGN KEY (`problem_status_code`)
    REFERENCES `icas`.`act_status_code` (`id`),
  CONSTRAINT `FK_co992gsensot0eyu764x9131o`
    FOREIGN KEY (`patient`)
    REFERENCES `icas`.`patient` (`id`),
  CONSTRAINT `FK_kpj6s1hrhhdneorsopcb9mgwr`
    FOREIGN KEY (`problem_code`)
    REFERENCES `icas`.`problem_code` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`plan_of_care_patient_problems`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`plan_of_care_patient_problems` (
  `plan_of_care` BIGINT(20) NOT NULL,
  `patient_problems` BIGINT(20) NOT NULL,
  PRIMARY KEY (`plan_of_care`, `patient_problems`),
  INDEX `FK_6i8nyiyfua4veljfmqidadoic` (`patient_problems` ASC),
  CONSTRAINT `FK_6asgjprft0uaf6wry7cr1as79`
    FOREIGN KEY (`plan_of_care`)
    REFERENCES `icas`.`plan_of_care` (`id`),
  CONSTRAINT `FK_6i8nyiyfua4veljfmqidadoic`
    FOREIGN KEY (`patient_problems`)
    REFERENCES `icas`.`problem` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`prefix_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`prefix_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`procedure_observation_procedure_performers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`procedure_observation_procedure_performers` (
  `procedure_observation` BIGINT(20) NOT NULL,
  `procedure_performers` BIGINT(20) NOT NULL,
  PRIMARY KEY (`procedure_observation`, `procedure_performers`),
  INDEX `FK_m1s6jbih32chhaycce9ljkffx` (`procedure_performers` ASC),
  CONSTRAINT `FK_m7jogs0o01r1cl1rsfks1rrno`
    FOREIGN KEY (`procedure_observation`)
    REFERENCES `icas`.`procedure_observation` (`id`),
  CONSTRAINT `FK_m1s6jbih32chhaycce9ljkffx`
    FOREIGN KEY (`procedure_performers`)
    REFERENCES `icas`.`abstract_provider` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`reason_for_visit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`reason_for_visit` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `reason_for_visit_date_time` DATETIME NOT NULL,
  `reason_for_visit_text` LONGTEXT NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  `patient` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_37u8hfonohp0m1meqpuqfrcr1` (`patient` ASC),
  CONSTRAINT `FK_37u8hfonohp0m1meqpuqfrcr1`
    FOREIGN KEY (`patient`)
    REFERENCES `icas`.`patient` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`result_interpretation_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`result_interpretation_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`result_organizer_status_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`result_organizer_status_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`result_organizer_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`result_organizer_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`result_organizer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`result_organizer` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `result_organizer_date_time` DATETIME NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  `patient` BIGINT(20) NULL DEFAULT NULL,
  `result_organizer_code` BIGINT(20) NULL DEFAULT NULL,
  `result_organizer_status_code` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_b3iyml6awcnv9i7hc8qe3kp52` (`patient` ASC),
  INDEX `FK_k775n14y7m7ow7mh3qhmk1yo4` (`result_organizer_code` ASC),
  INDEX `FK_hewi3gb3dbw2432018hv2i94t` (`result_organizer_status_code` ASC),
  CONSTRAINT `FK_hewi3gb3dbw2432018hv2i94t`
    FOREIGN KEY (`result_organizer_status_code`)
    REFERENCES `icas`.`result_organizer_status_code` (`id`),
  CONSTRAINT `FK_b3iyml6awcnv9i7hc8qe3kp52`
    FOREIGN KEY (`patient`)
    REFERENCES `icas`.`patient` (`id`),
  CONSTRAINT `FK_k775n14y7m7ow7mh3qhmk1yo4`
    FOREIGN KEY (`result_organizer_code`)
    REFERENCES `icas`.`result_organizer_code` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`result_observation_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`result_observation_type` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`result_observation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`result_observation` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `result_observation_date_time` DATETIME NOT NULL,
  `result_observation_note` VARCHAR(255) NULL DEFAULT NULL,
  `result_observation_value` VARCHAR(255) NULL DEFAULT NULL,
  `result_reference_range_max` VARCHAR(255) NULL DEFAULT NULL,
  `result_reference_range_min` VARCHAR(255) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  `result_observation_interpretation_code` BIGINT(20) NULL DEFAULT NULL,
  `result_observation_measure_code` BIGINT(20) NULL DEFAULT NULL,
  `result_observation_type_code` BIGINT(20) NULL DEFAULT NULL,
  `result_organizer_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_jfg9brdt9dw2b93so3nbk24eg` (`result_observation_interpretation_code` ASC),
  INDEX `FK_aokbm4dqqru602fi661bwla8k` (`result_observation_measure_code` ASC),
  INDEX `FK_o6f32nv24ad4s2xevrph8logd` (`result_observation_type_code` ASC),
  INDEX `FK_3lfn74seu8lr4i47kyjujwd79` (`result_organizer_id` ASC),
  CONSTRAINT `FK_3lfn74seu8lr4i47kyjujwd79`
    FOREIGN KEY (`result_organizer_id`)
    REFERENCES `icas`.`result_organizer` (`id`),
  CONSTRAINT `FK_aokbm4dqqru602fi661bwla8k`
    FOREIGN KEY (`result_observation_measure_code`)
    REFERENCES `icas`.`unit_of_measure_code` (`id`),
  CONSTRAINT `FK_jfg9brdt9dw2b93so3nbk24eg`
    FOREIGN KEY (`result_observation_interpretation_code`)
    REFERENCES `icas`.`result_interpretation_code` (`id`),
  CONSTRAINT `FK_o6f32nv24ad4s2xevrph8logd`
    FOREIGN KEY (`result_observation_type_code`)
    REFERENCES `icas`.`result_observation_type` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`result_observation_status_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`result_observation_status_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`service_code_organizational_provider`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`service_code_organizational_provider` (
  `service_code` BIGINT(20) NOT NULL,
  `organizational_provider` BIGINT(20) NOT NULL,
  PRIMARY KEY (`service_code`, `organizational_provider`),
  INDEX `FK_knc231hy08fe3x3adpgkp2y91` (`organizational_provider` ASC),
  CONSTRAINT `FK_lajmvj634gtbnfh8pqltygmso`
    FOREIGN KEY (`service_code`)
    REFERENCES `icas`.`service_code` (`id`),
  CONSTRAINT `FK_knc231hy08fe3x3adpgkp2y91`
    FOREIGN KEY (`organizational_provider`)
    REFERENCES `icas`.`abstract_provider` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`social_history_type_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`social_history_type_code` (
  `id` BIGINT(20) NOT NULL,
  `code` VARCHAR(250) NOT NULL,
  `code_system` VARCHAR(250) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(250) NOT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(250) NOT NULL,
  `original_text` VARCHAR(250) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`social_history`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`social_history` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `social_history_end_date` DATETIME NULL DEFAULT NULL,
  `social_history_free_text` VARCHAR(255) NULL DEFAULT NULL,
  `social_history_start_date` DATETIME NOT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  `patient` BIGINT(20) NULL DEFAULT NULL,
  `social_history_status_code` BIGINT(20) NULL DEFAULT NULL,
  `social_history_type_code` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_h7r24kb0pl35slxyx2d1sgosj` (`patient` ASC),
  INDEX `FK_3llxhf7mswxgk770b9gp1s1s2` (`social_history_status_code` ASC),
  INDEX `FK_8agygr5lxr2vkj2lsd7760rj2` (`social_history_type_code` ASC),
  CONSTRAINT `FK_8agygr5lxr2vkj2lsd7760rj2`
    FOREIGN KEY (`social_history_type_code`)
    REFERENCES `icas`.`social_history_type_code` (`id`),
  CONSTRAINT `FK_3llxhf7mswxgk770b9gp1s1s2`
    FOREIGN KEY (`social_history_status_code`)
    REFERENCES `icas`.`act_status_code` (`id`),
  CONSTRAINT `FK_h7r24kb0pl35slxyx2d1sgosj`
    FOREIGN KEY (`patient`)
    REFERENCES `icas`.`patient` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`user_authority`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`user_authority` (
  `id` BIGINT(20) NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`, `name`),
  INDEX `FK_7j6cnvqw11gwiiwf8j7g052va` (`name` ASC),
  CONSTRAINT `FK_dkaclheaubxo9smqa9ffsx7sc`
    FOREIGN KEY (`id`)
    REFERENCES `icas`.`abstract_provider` (`id`),
  CONSTRAINT `FK_7j6cnvqw11gwiiwf8j7g052va`
    FOREIGN KEY (`name`)
    REFERENCES `icas`.`authority` (`name`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`uspstf_general_recommendation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`uspstf_general_recommendation` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `clinical` VARCHAR(255) NULL DEFAULT NULL,
  `clinical_url` VARCHAR(255) NULL DEFAULT NULL,
  `other` VARCHAR(255) NULL DEFAULT NULL,
  `other_url` VARCHAR(255) NULL DEFAULT NULL,
  `rationale` VARCHAR(255) NULL DEFAULT NULL,
  `recommendation_date_time` DATETIME NOT NULL,
  `title` VARCHAR(255) NULL DEFAULT NULL,
  `topic` VARCHAR(255) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`uspstf_specific_recommendation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`uspstf_specific_recommendation` (
  `id_` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `grade` VARCHAR(255) NULL DEFAULT NULL,
  `grade_ver` VARCHAR(255) NULL DEFAULT NULL,
  `id` VARCHAR(255) NULL DEFAULT NULL,
  `rationale` VARCHAR(255) NULL DEFAULT NULL,
  `recommendation_date_time` DATETIME NOT NULL,
  `risk_name` VARCHAR(255) NULL DEFAULT NULL,
  `risk_text` VARCHAR(255) NULL DEFAULT NULL,
  `serv_freq` VARCHAR(255) NULL DEFAULT NULL,
  `text` VARCHAR(255) NULL DEFAULT NULL,
  `title` VARCHAR(255) NULL DEFAULT NULL,
  `url` VARCHAR(255) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  `uspstf_general_recommendation` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id_`),
  INDEX `FK_t32xgm1qqtr1gsuqij2yx91c1` (`uspstf_general_recommendation` ASC),
  CONSTRAINT `FK_t32xgm1qqtr1gsuqij2yx91c1`
    FOREIGN KEY (`uspstf_general_recommendation`)
    REFERENCES `icas`.`uspstf_general_recommendation` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`uspstf_general_recommendation_uspstf_specific_recommendations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`uspstf_general_recommendation_uspstf_specific_recommendations` (
  `uspstf_general_recommendation` BIGINT(20) NOT NULL,
  `uspstf_specific_recommendations` BIGINT(20) NOT NULL,
  PRIMARY KEY (`uspstf_general_recommendation`, `uspstf_specific_recommendations`),
  UNIQUE INDEX `UK_31omkf3woi3q6o5237qsuvf22` (`uspstf_specific_recommendations` ASC),
  CONSTRAINT `FK_m5yx8f681bet1m4jl9gb5fvcs`
    FOREIGN KEY (`uspstf_general_recommendation`)
    REFERENCES `icas`.`uspstf_general_recommendation` (`id`),
  CONSTRAINT `FK_31omkf3woi3q6o5237qsuvf22`
    FOREIGN KEY (`uspstf_specific_recommendations`)
    REFERENCES `icas`.`uspstf_specific_recommendation` (`id_`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`uspstf_recommendation_tool`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`uspstf_recommendation_tool` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `text` VARCHAR(255) NULL DEFAULT NULL,
  `title` VARCHAR(255) NULL DEFAULT NULL,
  `url` VARCHAR(255) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`uspstf_specific_recommendation_tools`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`uspstf_specific_recommendation_tools` (
  `uspstf_specific_recommendation` BIGINT(20) NOT NULL,
  `tools` BIGINT(20) NOT NULL,
  PRIMARY KEY (`uspstf_specific_recommendation`, `tools`),
  UNIQUE INDEX `UK_kc5w8opekg5w2ex0pdx6027oh` (`tools` ASC),
  CONSTRAINT `FK_5o2ikvtwdkofm7ss3c5dfsha3`
    FOREIGN KEY (`uspstf_specific_recommendation`)
    REFERENCES `icas`.`uspstf_specific_recommendation` (`id_`),
  CONSTRAINT `FK_kc5w8opekg5w2ex0pdx6027oh`
    FOREIGN KEY (`tools`)
    REFERENCES `icas`.`uspstf_recommendation_tool` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`verification_token`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`verification_token` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `time_created` DATETIME NULL DEFAULT NULL,
  `uuid` VARCHAR(36) NULL DEFAULT NULL,
  `version` INT(11) NOT NULL,
  `expiry_date` DATETIME NULL DEFAULT NULL,
  `token` VARCHAR(36) NULL DEFAULT NULL,
  `token_type` VARCHAR(255) NULL DEFAULT NULL,
  `verified` BIT(1) NOT NULL,
  `user_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_q6jibbenp7o9v6tq178xg88hg` (`user_id` ASC),
  CONSTRAINT `FK_q6jibbenp7o9v6tq178xg88hg`
    FOREIGN KEY (`user_id`)
    REFERENCES `icas`.`abstract_provider` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`vital_sign_observation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`vital_sign_observation` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `result_reference_range` VARCHAR(255) NULL DEFAULT NULL,
  `version` INT(11) NULL DEFAULT NULL,
  `vital_sign_observation_date_time` DATETIME NOT NULL,
  `code` VARCHAR(255) NULL DEFAULT NULL,
  `code_system` VARCHAR(255) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(255) NULL DEFAULT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(255) NULL DEFAULT NULL,
  `original_text` VARCHAR(255) NULL DEFAULT NULL,
  `measured_value` DOUBLE NULL DEFAULT NULL,
  `vital_sign_observation_status_code` BIGINT(20) NULL DEFAULT NULL,
  `unit_of_measure_code` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_qg5cmi6s39klc0dssgdvfxqfh` (`vital_sign_observation_status_code` ASC),
  INDEX `FK_t4a7llyr4e9bwty893o61wdje` (`unit_of_measure_code` ASC),
  CONSTRAINT `FK_t4a7llyr4e9bwty893o61wdje`
    FOREIGN KEY (`unit_of_measure_code`)
    REFERENCES `icas`.`unit_of_measure_code` (`id`),
  CONSTRAINT `FK_qg5cmi6s39klc0dssgdvfxqfh`
    FOREIGN KEY (`vital_sign_observation_status_code`)
    REFERENCES `icas`.`act_status_code` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`vital_sign_observation_observation_interpretation_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`vital_sign_observation_observation_interpretation_code` (
  `vital_sign_observation` BIGINT(20) NOT NULL,
  `vital_sign_observation_interpretation_code` BIGINT(20) NOT NULL,
  PRIMARY KEY (`vital_sign_observation`, `vital_sign_observation_interpretation_code`),
  INDEX `FK_h45pkxocsoq3e2ob3ktmuplyk` (`vital_sign_observation_interpretation_code` ASC),
  CONSTRAINT `FK_1vs6qn1v40gp3il840c6nd7x4`
    FOREIGN KEY (`vital_sign_observation`)
    REFERENCES `icas`.`vital_sign_observation` (`id`),
  CONSTRAINT `FK_h45pkxocsoq3e2ob3ktmuplyk`
    FOREIGN KEY (`vital_sign_observation_interpretation_code`)
    REFERENCES `icas`.`result_interpretation_code` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`vital_sign_organizer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`vital_sign_organizer` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `version` INT(11) NULL DEFAULT NULL,
  `code` VARCHAR(255) NULL DEFAULT NULL,
  `code_system` VARCHAR(255) NULL DEFAULT NULL,
  `code_system_name` VARCHAR(255) NULL DEFAULT NULL,
  `code_system_version` DOUBLE NULL DEFAULT NULL,
  `display_name` VARCHAR(255) NULL DEFAULT NULL,
  `original_text` VARCHAR(255) NULL DEFAULT NULL,
  `vital_sign_organizer_date_time` DATETIME NULL DEFAULT NULL,
  `patient` BIGINT(20) NULL DEFAULT NULL,
  `vital_sign_organizer_status_code` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_6hamlfyd4ns0velqmupfcs3b6` (`patient` ASC),
  INDEX `FK_26emhknf3d7v2p79av8bcm0l6` (`vital_sign_organizer_status_code` ASC),
  CONSTRAINT `FK_26emhknf3d7v2p79av8bcm0l6`
    FOREIGN KEY (`vital_sign_organizer_status_code`)
    REFERENCES `icas`.`act_status_code` (`id`),
  CONSTRAINT `FK_6hamlfyd4ns0velqmupfcs3b6`
    FOREIGN KEY (`patient`)
    REFERENCES `icas`.`patient` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `icas`.`vital_sign_organizer_vital_sign_observations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `icas`.`vital_sign_organizer_vital_sign_observations` (
  `vital_sign_organizer` BIGINT(20) NOT NULL,
  `vital_sign_observations` BIGINT(20) NOT NULL,
  PRIMARY KEY (`vital_sign_organizer`, `vital_sign_observations`),
  INDEX `FK_mmv7ucaprp51f9c2p7jwmrncj` (`vital_sign_observations` ASC),
  CONSTRAINT `FK_aaulwq1fraxr50m36q1mnn8go`
    FOREIGN KEY (`vital_sign_organizer`)
    REFERENCES `icas`.`vital_sign_organizer` (`id`),
  CONSTRAINT `FK_mmv7ucaprp51f9c2p7jwmrncj`
    FOREIGN KEY (`vital_sign_observations`)
    REFERENCES `icas`.`vital_sign_observation` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
