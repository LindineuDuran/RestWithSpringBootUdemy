CREATE DATABASE IF NOT EXISTS rest_with_spring_boot_udemy DEFAULT CHARACTER SET latin1 DEFAULT ENCRYPTION='N';
USE rest_with_spring_boot_udemy;

DROP TABLE IF EXISTS person;
CREATE TABLE `person` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(100) NOT NULL,
  `first_name` varchar(80) NOT NULL,
  `gender` varchar(6) NOT NULL,
  `last_name` varchar(80) NOT NULL,
  PRIMARY KEY (`id`)
)