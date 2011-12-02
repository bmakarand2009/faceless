# ************************************************************
# Sequel Pro SQL dump
# Version 3408
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: mycanarydb.c8mvjbrpgxos.us-east-1.rds.amazonaws.com (MySQL 5.1.57-log)
# Database: devfaceless
# Generation Time: 2011-10-18 20:55:22 -0400
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table alpha
# ------------------------------------------------------------

DROP TABLE IF EXISTS `alpha`;

CREATE TABLE `alpha` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `c1` varchar(255) DEFAULT NULL,
  `c2` varchar(255) DEFAULT NULL,
  `date_created` datetime NOT NULL,
  `last_updated` datetime NOT NULL,
  `parent_id` bigint(20) NOT NULL,
  `pkey` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `parent_id` (`parent_id`,`pkey`),
  KEY `FK589B15E15B843E9` (`parent_id`),
  CONSTRAINT `FK589B15E15B843E9` FOREIGN KEY (`parent_id`) REFERENCES `client` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table alpha_child_alpha
# ------------------------------------------------------------

DROP TABLE IF EXISTS `alpha_child_alpha`;

CREATE TABLE `alpha_child_alpha` (
  `alpha_child_id` bigint(20) DEFAULT NULL,
  `child_alpha_id` bigint(20) DEFAULT NULL,
  KEY `FKCD7DB71A5E87312F` (`alpha_child_id`),
  KEY `FKCD7DB71A4B23FD4F` (`child_alpha_id`),
  CONSTRAINT `FKCD7DB71A4B23FD4F` FOREIGN KEY (`child_alpha_id`) REFERENCES `child_alpha` (`id`),
  CONSTRAINT `FKCD7DB71A5E87312F` FOREIGN KEY (`alpha_child_id`) REFERENCES `alpha` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table alpha_contacts
# ------------------------------------------------------------

DROP TABLE IF EXISTS `alpha_contacts`;

CREATE TABLE `alpha_contacts` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `c1` varchar(255) DEFAULT NULL,
  `c10` varchar(255) DEFAULT NULL,
  `c11` varchar(255) DEFAULT NULL,
  `c12` varchar(255) DEFAULT NULL,
  `c13` varchar(255) DEFAULT NULL,
  `c14` varchar(255) DEFAULT NULL,
  `c15` varchar(255) DEFAULT NULL,
  `c16` varchar(255) DEFAULT NULL,
  `c17` varchar(255) DEFAULT NULL,
  `c18` varchar(255) DEFAULT NULL,
  `c2` varchar(255) DEFAULT NULL,
  `c3` varchar(255) DEFAULT NULL,
  `c4` varchar(255) DEFAULT NULL,
  `c5` varchar(255) DEFAULT NULL,
  `c6` varchar(255) DEFAULT NULL,
  `c7` varchar(255) DEFAULT NULL,
  `c8` varchar(255) DEFAULT NULL,
  `c9` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) NOT NULL,
  `date_created` datetime NOT NULL,
  `desc_field1` longtext,
  `desc_field2` longtext,
  `email_addr` varchar(255) NOT NULL,
  `last_updated` datetime NOT NULL,
  `owner_id` bigint(20) NOT NULL,
  `parent_id` bigint(20) NOT NULL,
  `source_type` varchar(12) NOT NULL,
  `updated_by` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `parent_id` (`parent_id`,`email_addr`),
  KEY `FK82578A1415B843E9` (`parent_id`),
  KEY `FK82578A1449E1749` (`owner_id`),
  CONSTRAINT `FK82578A1449E1749` FOREIGN KEY (`owner_id`) REFERENCES `groups` (`id`),
  CONSTRAINT `FK82578A1415B843E9` FOREIGN KEY (`parent_id`) REFERENCES `client` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table bar
# ------------------------------------------------------------

DROP TABLE IF EXISTS `bar`;

CREATE TABLE `bar` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `bar_name` varchar(255) NOT NULL,
  `parent_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `parent_id` (`parent_id`,`bar_name`),
  KEY `FK17C13B6BBC129` (`parent_id`),
  CONSTRAINT `FK17C13B6BBC129` FOREIGN KEY (`parent_id`) REFERENCES `foo2` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table book
# ------------------------------------------------------------

DROP TABLE IF EXISTS `book`;

CREATE TABLE `book` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table child_alpha
# ------------------------------------------------------------

DROP TABLE IF EXISTS `child_alpha`;

CREATE TABLE `child_alpha` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `c1` varchar(255) DEFAULT NULL,
  `c2` varchar(255) DEFAULT NULL,
  `c3` varchar(255) DEFAULT NULL,
  `c4` varchar(255) DEFAULT NULL,
  `c5` varchar(255) DEFAULT NULL,
  `c6` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) NOT NULL,
  `pkey` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `parent_id` (`parent_id`,`pkey`),
  KEY `FKC6ECC47BD2D9A120` (`parent_id`),
  CONSTRAINT `FKC6ECC47BD2D9A120` FOREIGN KEY (`parent_id`) REFERENCES `alpha` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table child_vendors_mo
# ------------------------------------------------------------

DROP TABLE IF EXISTS `child_vendors_mo`;

CREATE TABLE `child_vendors_mo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `c1` varchar(255) DEFAULT NULL,
  `c2` varchar(255) DEFAULT NULL,
  `c3` varchar(255) DEFAULT NULL,
  `c4` varchar(255) DEFAULT NULL,
  `c5` varchar(255) DEFAULT NULL,
  `c6` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) NOT NULL,
  `pkey` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `parent_id` (`parent_id`,`pkey`),
  KEY `FK6B7DD1397DC8630` (`parent_id`),
  CONSTRAINT `FK6B7DD1397DC8630` FOREIGN KEY (`parent_id`) REFERENCES `vendors_mo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `child_vendors_mo` WRITE;
/*!40000 ALTER TABLE `child_vendors_mo` DISABLE KEYS */;

INSERT INTO `child_vendors_mo` (`id`, `version`, `c1`, `c2`, `c3`, `c4`, `c5`, `c6`, `parent_id`, `pkey`)
VALUES
	(1,0,NULL,'hello','world','goodness',NULL,NULL,1,'abc@gmail.com'),
	(2,0,NULL,'hello','world','goodness',NULL,NULL,1,'abc1@gmail.com'),
	(3,0,NULL,'hello','world','goodness',NULL,NULL,1,'abc2@gmail.com'),
	(4,0,NULL,'hello','world','goodness',NULL,NULL,4,'abc@gmail.com'),
	(5,0,NULL,'hello','world','goodness',NULL,NULL,5,'abc@gmail.com'),
	(6,0,NULL,'hello','world','goodness',NULL,NULL,6,'abc@gmail.com'),
	(7,0,NULL,'hello','world','goodness',NULL,NULL,7,'abc@gmail.com'),
	(8,0,NULL,'hello','world','goodness',NULL,NULL,8,'abc@gmail.com'),
	(9,0,NULL,'hello','world','goodness',NULL,NULL,9,'abc@gmail.com'),
	(10,0,NULL,'hello','world','goodness',NULL,NULL,10,'abc@gmail.com'),
	(11,0,NULL,'hello','world','goodness',NULL,NULL,13,'abc@gmail.com'),
	(12,0,NULL,'hello','world','goodness',NULL,NULL,14,'abc@gmail.com'),
	(13,0,NULL,'hello','world','goodness',NULL,NULL,15,'abc@gmail.com'),
	(14,0,NULL,'hello','world','goodness',NULL,NULL,16,'abc@gmail.com'),
	(15,0,NULL,'hello','world','goodness',NULL,NULL,17,'abc@gmail.com'),
	(16,0,NULL,'hello','world','goodness',NULL,NULL,18,'abc@gmail.com'),
	(17,0,NULL,'hello','world','goodness',NULL,NULL,19,'abc@gmail.com'),
	(18,0,NULL,'hello','world','goodness',NULL,NULL,20,'abc@gmail.com'),
	(19,0,NULL,'hello','world','goodness',NULL,NULL,21,'abc@gmail.com'),
	(20,0,NULL,'hello','world','goodness',NULL,NULL,22,'abc@gmail.com');

/*!40000 ALTER TABLE `child_vendors_mo` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table client
# ------------------------------------------------------------

DROP TABLE IF EXISTS `client`;

CREATE TABLE `client` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `org_id` varchar(255) NOT NULL,
  `org_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `org_id` (`org_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;

INSERT INTO `client` (`id`, `version`, `org_id`, `org_name`)
VALUES
	(1,0,'canary-test-123','canary'),
	(2,0,'foc-harvest','harvest');

/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table contact
# ------------------------------------------------------------

DROP TABLE IF EXISTS `contact`;

CREATE TABLE `contact` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `date_created` datetime NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `last_updated` datetime NOT NULL,
  `parent_id` bigint(20) NOT NULL,
  `suffix` varchar(255) NOT NULL,
  `updated_by` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `parent_id` (`parent_id`,`suffix`,`last_name`,`first_name`),
  KEY `FK38B7242015B843E9` (`parent_id`),
  CONSTRAINT `FK38B7242015B843E9` FOREIGN KEY (`parent_id`) REFERENCES `client` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table contact_address
# ------------------------------------------------------------

DROP TABLE IF EXISTS `contact_address`;

CREATE TABLE `contact_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `contact_id` bigint(20) NOT NULL,
  `country` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `zip` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK20846D5323C3430` (`contact_id`),
  CONSTRAINT `FK20846D5323C3430` FOREIGN KEY (`contact_id`) REFERENCES `contact` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table contact_details
# ------------------------------------------------------------

DROP TABLE IF EXISTS `contact_details`;

CREATE TABLE `contact_details` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `additional_info` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `contact_id` bigint(20) NOT NULL,
  `contact_type` varchar(7) NOT NULL,
  `contact_value` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKA3499D23323C3430` (`contact_id`),
  CONSTRAINT `FKA3499D23323C3430` FOREIGN KEY (`contact_id`) REFERENCES `contact` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table emp
# ------------------------------------------------------------

DROP TABLE IF EXISTS `emp`;

CREATE TABLE `emp` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `date_created` datetime NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `last_updated` datetime NOT NULL,
  `my_full_name` varchar(255) NOT NULL,
  `suffix` varchar(255) NOT NULL,
  `updated_by` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table foo
# ------------------------------------------------------------

DROP TABLE IF EXISTS `foo`;

CREATE TABLE `foo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `bar` int(11) NOT NULL,
  `date_created` datetime NOT NULL,
  `last_updated` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table foo2
# ------------------------------------------------------------

DROP TABLE IF EXISTS `foo2`;

CREATE TABLE `foo2` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `org_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table groups
# ------------------------------------------------------------

DROP TABLE IF EXISTS `groups`;

CREATE TABLE `groups` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `grp_label` varchar(255) NOT NULL,
  `grp_name` varchar(255) NOT NULL,
  `parent_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `parent_id` (`parent_id`,`grp_name`),
  KEY `FKB63DD9D415B843E9` (`parent_id`),
  CONSTRAINT `FKB63DD9D415B843E9` FOREIGN KEY (`parent_id`) REFERENCES `client` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `groups` WRITE;
/*!40000 ALTER TABLE `groups` DISABLE KEYS */;

INSERT INTO `groups` (`id`, `version`, `grp_label`, `grp_name`, `parent_id`)
VALUES
	(1,1,'canary admin','admin',1),
	(2,1,'harvest admin','admin',2);

/*!40000 ALTER TABLE `groups` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table groups_users
# ------------------------------------------------------------

DROP TABLE IF EXISTS `groups_users`;

CREATE TABLE `groups_users` (
  `groups_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`groups_id`,`user_id`),
  KEY `FKF4231BDD4925D088` (`groups_id`),
  KEY `FKF4231BDDD0CBF4A8` (`user_id`),
  CONSTRAINT `FKF4231BDD4925D088` FOREIGN KEY (`groups_id`) REFERENCES `groups` (`id`),
  CONSTRAINT `FKF4231BDDD0CBF4A8` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `groups_users` WRITE;
/*!40000 ALTER TABLE `groups_users` DISABLE KEYS */;

INSERT INTO `groups_users` (`groups_id`, `user_id`)
VALUES
	(1,1),
	(2,2);

/*!40000 ALTER TABLE `groups_users` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `label` varchar(255) NOT NULL,
  `parent_id` bigint(20) NOT NULL,
  `role_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `parent_id` (`parent_id`,`role_name`),
  KEY `FK35807615B843E9` (`parent_id`),
  CONSTRAINT `FK35807615B843E9` FOREIGN KEY (`parent_id`) REFERENCES `client` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;

INSERT INTO `role` (`id`, `version`, `label`, `parent_id`, `role_name`)
VALUES
	(1,0,'sample Desc',1,'admin'),
	(2,0,'sample desc',2,'admin');

/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table services
# ------------------------------------------------------------

DROP TABLE IF EXISTS `services`;

CREATE TABLE `services` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `is_access` bit(1) NOT NULL,
  `is_create` bit(1) NOT NULL,
  `is_delete` bit(1) NOT NULL,
  `is_self_group` bit(1) NOT NULL,
  `is_update` bit(1) NOT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  `service_name` varchar(9) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_id` (`role_id`,`service_name`),
  KEY `FK5235105E2BA130C8` (`role_id`),
  CONSTRAINT `FK5235105E2BA130C8` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `services` WRITE;
/*!40000 ALTER TABLE `services` DISABLE KEYS */;

INSERT INTO `services` (`id`, `version`, `is_access`, `is_create`, `is_delete`, `is_self_group`, `is_update`, `role_id`, `service_name`)
VALUES
	(1,0,b'1',b'1',b'1',b'0',b'1',1,'candidate'),
	(2,0,b'1',b'1',b'1',b'1',b'1',2,'candidate'),
	(3,0,b'1',b'1',b'1',b'0',b'1',NULL,'candidate'),
	(4,0,b'1',b'1',b'1',b'1',b'1',NULL,'candidate');

/*!40000 ALTER TABLE `services` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `account_expired` bit(1) NOT NULL,
  `account_locked` bit(1) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `parent_id` bigint(20) NOT NULL,
  `password` varchar(255) NOT NULL,
  `password_expired` bit(1) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `FK36EBCB15B843E9` (`parent_id`),
  CONSTRAINT `FK36EBCB15B843E9` FOREIGN KEY (`parent_id`) REFERENCES `client` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;

INSERT INTO `user` (`id`, `version`, `account_expired`, `account_locked`, `enabled`, `parent_id`, `password`, `password_expired`, `username`)
VALUES
	(1,1,b'0',b'0',b'1',1,'abcd',b'0','mark@canary.com'),
	(2,1,b'0',b'0',b'1',2,'abcd',b'0','mark@harvest.com');

/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `role_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`user_id`),
  KEY `FK143BF46A2BA130C8` (`role_id`),
  KEY `FK143BF46AD0CBF4A8` (`user_id`),
  CONSTRAINT `FK143BF46A2BA130C8` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FK143BF46AD0CBF4A8` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;

INSERT INTO `user_role` (`role_id`, `user_id`)
VALUES
	(2,2);

/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table vendors_mo
# ------------------------------------------------------------

DROP TABLE IF EXISTS `vendors_mo`;

CREATE TABLE `vendors_mo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `c1` varchar(255) DEFAULT NULL,
  `c10` varchar(255) DEFAULT NULL,
  `c11` varchar(255) DEFAULT NULL,
  `c12` varchar(255) DEFAULT NULL,
  `c2` varchar(255) DEFAULT NULL,
  `c3` varchar(255) DEFAULT NULL,
  `c4` varchar(255) DEFAULT NULL,
  `c5` varchar(255) DEFAULT NULL,
  `c6` varchar(255) DEFAULT NULL,
  `c7` varchar(255) DEFAULT NULL,
  `c8` varchar(255) DEFAULT NULL,
  `c9` varchar(255) DEFAULT NULL,
  `date_created` datetime NOT NULL,
  `last_updated` datetime NOT NULL,
  `parent_id` bigint(20) NOT NULL,
  `pkey` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `parent_id` (`parent_id`,`pkey`),
  KEY `FK29707A3615B843E9` (`parent_id`),
  CONSTRAINT `FK29707A3615B843E9` FOREIGN KEY (`parent_id`) REFERENCES `client` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `vendors_mo` WRITE;
/*!40000 ALTER TABLE `vendors_mo` DISABLE KEYS */;

INSERT INTO `vendors_mo` (`id`, `version`, `c1`, `c10`, `c11`, `c12`, `c2`, `c3`, `c4`, `c5`, `c6`, `c7`, `c8`, `c9`, `date_created`, `last_updated`, `parent_id`, `pkey`)
VALUES
	(1,0,'myfirstVendorName1','true2',NULL,NULL,'mylastVendorName','true','false','alpharetta','ga','30022','java','has a strong foothold in atlanta','2011-09-03 10:26:41','2011-09-03 10:26:41',1,'1315060001239company1'),
	(2,0,'myfirstVendorName2','true2',NULL,NULL,'mylastVendorName','true','false','alpharetta','ga','30022','java','has a strong foothold in atlanta','2011-09-03 10:26:41','2011-09-03 10:26:41',1,'1315060001588company2'),
	(3,0,'myfirstVendorName3','true2',NULL,NULL,'mylastVendorName','true','false','alpharetta','ga','30022','java','has a strong foothold in atlanta','2011-09-03 10:26:42','2011-09-03 10:26:42',1,'1315060002147company3'),
	(4,0,'myfirstVendorName4','true2',NULL,NULL,'mylastVendorName','true','false','alpharetta','ga','30022','java','has a strong foothold in atlanta','2011-09-03 10:26:42','2011-09-03 10:26:42',1,'1315060002364company4'),
	(5,0,'myfirstVendorName5','true2',NULL,NULL,'mylastVendorName','true','false','alpharetta','ga','30022','java','has a strong foothold in atlanta','2011-09-03 10:26:42','2011-09-03 10:26:42',1,'1315060002581company5'),
	(6,0,'myfirstVendorName6','true2',NULL,NULL,'mylastVendorName','true','false','alpharetta','ga','30022','java','has a strong foothold in atlanta','2011-09-03 10:26:43','2011-09-03 10:26:43',1,'1315060003153company6'),
	(7,0,'myfirstVendorName7','true2',NULL,NULL,'mylastVendorName','true','false','alpharetta','ga','30022','java','has a strong foothold in atlanta','2011-09-03 10:26:43','2011-09-03 10:26:43',1,'1315060003823company7'),
	(8,0,'myfirstVendorName8','true2',NULL,NULL,'mylastVendorName','true','false','alpharetta','ga','30022','java','has a strong foothold in atlanta','2011-09-03 10:26:44','2011-09-03 10:26:44',1,'1315060004394company8'),
	(9,0,'myfirstVendorName9','true2',NULL,NULL,'mylastVendorName','true','false','alpharetta','ga','30022','java','has a strong foothold in atlanta','2011-09-03 10:26:44','2011-09-03 10:26:44',1,'1315060004897company9'),
	(10,0,'myfirstVendorName10','true2',NULL,NULL,'mylastVendorName','true','false','alpharetta','ga','30022','java','has a strong foothold in atlanta','2011-09-03 10:26:45','2011-09-03 10:26:45',1,'1315060005150company10'),
	(13,0,'myfirstVendorName1','true2',NULL,NULL,'mylastVendorName','true','false','alpharetta','ga','30022','java','has a strong foothold in atlanta','2011-09-18 00:44:51','2011-09-18 00:44:51',1,'1316321091398company1'),
	(14,0,'myfirstVendorName2','true2',NULL,NULL,'mylastVendorName','true','false','alpharetta','ga','30022','java','has a strong foothold in atlanta','2011-09-18 00:44:51','2011-09-18 00:44:51',1,'1316321091882company2'),
	(15,0,'myfirstVendorName3','true2',NULL,NULL,'mylastVendorName','true','false','alpharetta','ga','30022','java','has a strong foothold in atlanta','2011-09-18 00:44:52','2011-09-18 00:44:52',1,'1316321092202company3'),
	(16,0,'myfirstVendorName4','true2',NULL,NULL,'mylastVendorName','true','false','alpharetta','ga','30022','java','has a strong foothold in atlanta','2011-09-18 00:44:52','2011-09-18 00:44:52',1,'1316321092910company4'),
	(17,0,'myfirstVendorName5','true2',NULL,NULL,'mylastVendorName','true','false','alpharetta','ga','30022','java','has a strong foothold in atlanta','2011-09-18 00:44:53','2011-09-18 00:44:53',1,'1316321093255company5'),
	(18,0,'myfirstVendorName6','true2',NULL,NULL,'mylastVendorName','true','false','alpharetta','ga','30022','java','has a strong foothold in atlanta','2011-09-18 00:44:53','2011-09-18 00:44:53',1,'1316321093575company6'),
	(19,0,'myfirstVendorName7','true2',NULL,NULL,'mylastVendorName','true','false','alpharetta','ga','30022','java','has a strong foothold in atlanta','2011-09-18 00:44:53','2011-09-18 00:44:53',1,'1316321093900company7'),
	(20,0,'myfirstVendorName8','true2',NULL,NULL,'mylastVendorName','true','false','alpharetta','ga','30022','java','has a strong foothold in atlanta','2011-09-18 00:44:54','2011-09-18 00:44:54',1,'1316321094223company8'),
	(21,0,'myfirstVendorName9','true2',NULL,NULL,'mylastVendorName','true','false','alpharetta','ga','30022','java','has a strong foothold in atlanta','2011-09-18 00:44:54','2011-09-18 00:44:54',1,'1316321094558company9'),
	(22,0,'myfirstVendorName10','true2',NULL,NULL,'mylastVendorName','true','false','alpharetta','ga','30022','java','has a strong foothold in atlanta','2011-09-18 00:44:54','2011-09-18 00:44:54',1,'1316321094880company10');

/*!40000 ALTER TABLE `vendors_mo` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
