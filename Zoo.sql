-- MySQL dump 10.13  Distrib 5.7.16, for Win64 (x86_64)
--
-- Host: localhost    Database: zoo
-- ------------------------------------------------------
-- Server version	5.7.16-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `gatunki`
--
DROP TABLE IF EXISTS `gatunki`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gatunki` (
  `id_gatunku`      VARCHAR(12) NOT NULL,
  `pelna_nazwa`     VARCHAR(30) NOT NULL,
  `id_pracownika`   INT(12),
  PRIMARY KEY (id_gatunku),
    FOREIGN KEY (id_pracownika) REFERENCES `pracownicy`( `id_pracownika`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gatunki`
--

LOAD DATA LOCAL INFILE 'C:/Program Files/mysql/gatunki.csv' INTO TABLE `gatunki` FIELDS TERMINATED BY ';' ENCLOSED BY '"'
LINES TERMINATED BY '\r\n' 
IGNORE 1 LINES;
--
-- Table structure for table `zwierzeta`
--

DROP TABLE IF EXISTS `zwierzeta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zwierzeta` (
    `nr_zw`         INT(11) NOT NULL AUTO_INCREMENT,
    `identyfikator` VARCHAR(12) NOT NULL,
    `id_gatunku`    VARCHAR(12) NOT NULL,
    `plec`          VARCHAR(6) NOT NULL,
	`id_obiektu`	INT(12) NOT NULL,
    PRIMARY KEY (nr_zw),
    FOREIGN KEY (id_gatunku) REFERENCES gatunki(id_gatunku)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zwierzeta`
--

LOAD DATA LOCAL INFILE 'C:/Program Files/mysql/zwierzeta.csv' INTO TABLE `zwierzeta` FIELDS TERMINATED BY ';'ENCLOSED BY '"'
LINES TERMINATED BY '\r\n' 
IGNORE 1 LINES;

--
-- Table structure for table `pawilony`
--

DROP TABLE IF EXISTS `pawilony`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pawilony` (
    `id_pawilonu`     VARCHAR(12) NOT NULL,
	`temat`	          VARCHAR(30) NOT NULL,
    PRIMARY KEY (id_pawilonu)   
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pawilony`
--

LOAD DATA LOCAL INFILE 'C:/Program Files/mysql/pawilony.csv' INTO TABLE `pawilony` FIELDS TERMINATED BY ';' ENCLOSED BY '"'
LINES TERMINATED BY '\r\n' 
IGNORE 1 LINES;

--
-- Table structure for table `budynki`
--

DROP TABLE IF EXISTS `budynki`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `budynki` (
   `id_budynku`     VARCHAR(12) NOT NULL,
	`uwagi`	        VARCHAR(30) NOT NULL,
    PRIMARY KEY (id_budynku)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `budynki`
--

LOAD DATA LOCAL INFILE 'C:/Program Files/mysql/budynki.csv' INTO TABLE `budynki` FIELDS TERMINATED BY ';' ENCLOSED BY '"'
LINES TERMINATED BY '\r\n' 
IGNORE 1 LINES;

--
-- Table structure for table `obiekty`
--

DROP TABLE IF EXISTS `obiekty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `obiekty` (
    `id_obiektu` 	  INT(12) NOT NULL,
    `rodzaj`          VARCHAR(12) NOT NULL,
    `id_budynku`	  VARCHAR(12) NOT NULL,
    `id_pawilonu`     VARCHAR(12) NOT NULL,
    PRIMARY KEY (id_obiektu),
    FOREIGN KEY (id_budynku) REFERENCES budynki(id_budynku),
    FOREIGN KEY (id_pawilonu) REFERENCES pawilony(id_pawilonu)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `obiekty`
--

LOAD DATA LOCAL INFILE 'C:/Program Files/mysql/obiekty.csv' INTO TABLE `obiekty` FIELDS TERMINATED BY ';' ENCLOSED BY '"'
LINES TERMINATED BY '\r\n' 
IGNORE 1 LINES;

--
-- Table structure for table `pracownicy`
--

DROP TABLE IF EXISTS `pracownicy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pracownicy` (
   `id_pracownika` 	INT(12) NOT NULL,
   `imie`           VARCHAR(30) NOT NULL,
    `nazwisko`	    VARCHAR(30) NOT NULL,
    `dzial`         VARCHAR(12) NOT NULL,
    PRIMARY KEY (id_pracownika)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pracownicy`
--
LOAD DATA LOCAL INFILE 'C:/Program Files/mysql/pracownicy.csv' INTO TABLE `pracownicy` FIELDS TERMINATED BY ';' ENCLOSED BY '"'
LINES TERMINATED BY '\r\n' 
IGNORE 1 LINES;

--
-- Table structure for table `przydzialy`
--

DROP TABLE IF EXISTS `przydzialy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `przydzialy` (
  `id_pracownika`	    INT(12) NOT NULL,
	`id_obiektu` 	    INT(12) NOT NULL,
	`od` 	            DATE,
	`do` 	            DATE,
    FOREIGN KEY (id_pracownika) REFERENCES pracownicy(id_pracownika),
    FOREIGN KEY (id_obiektu) REFERENCES obiekty(id_obiektu)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `przydzialy`
--
LOAD DATA LOCAL INFILE 'C:/Program Files/mysql/przydzialy.csv' INTO TABLE `przydzialy` FIELDS TERMINATED BY ';' ENCLOSED BY '"'
LINES TERMINATED BY '\r\n' 
IGNORE 1 LINES;

--
-- Table structure for table `czynnosci`
--

DROP TABLE IF EXISTS `czynnosci`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `czynnosci` (
   `id_czynnosci`  	INT(12) NOT NULL,
	`rodzaj`	    VARCHAR(30) NOT NULL,
	`miejsce` 	    INT(12) NOT NULL,
	`od`	        DATE,
	`do`	        DATE,
	`id_pracownika`	INT(12) NOT NULL,
    PRIMARY KEY(id_czynnosci),
    FOREIGN KEY (id_pracownika) REFERENCES pracownicy(id_pracownika)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `czynnosci`
--
LOAD DATA LOCAL INFILE 'C:/Program Files/mysql/czynnosci.csv' INTO TABLE `czynnosci` FIELDS TERMINATED BY ';' ENCLOSED BY '"'
LINES TERMINATED BY '\r\n' 
IGNORE 1 LINES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `Username` varchar(20) NOT NULL,
  `Password` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('admin','admin'),('Janina','haslo123'),('Asia','zoo');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;