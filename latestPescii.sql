-- MySQL dump 10.13  Distrib 5.5.46, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: PESCII
-- ------------------------------------------------------
-- Server version	5.5.46-0+deb8u1

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
-- Table structure for table `Account`
--

DROP TABLE IF EXISTS `Account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Account` (
  `accountID` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(30) NOT NULL DEFAULT 'DefaultAccount',
  `password` varchar(255) DEFAULT NULL,
  `accountStatus` int(11) NOT NULL,
  `accountType` int(11) NOT NULL,
  `dateCreated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`accountID`),
  UNIQUE KEY `userName` (`userName`),
  KEY `accountStatus` (`accountStatus`),
  KEY `accountType` (`accountType`),
  CONSTRAINT `Account_ibfk_1` FOREIGN KEY (`accountStatus`) REFERENCES `AccountStatus` (`accountStatusID`),
  CONSTRAINT `Account_ibfk_2` FOREIGN KEY (`accountType`) REFERENCES `AccountType` (`accountTypeID`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Account`
--

LOCK TABLES `Account` WRITE;
/*!40000 ALTER TABLE `Account` DISABLE KEYS */;
INSERT INTO `Account` VALUES (9,'admin','0f6f77a5e687662f44616161918db5e6a443743517f23b3dfd0543f536e24574',1,1,'2016-10-26 01:55:55'),(23,'accountant','85c08ae9d978f60861f77042e3ba14ad3c2f3dc1220e2007265532ed7a7e6caf',1,3,'2016-10-31 03:38:40'),(25,'Secretary','ac0277d913206efbf7e04cdb7552c2e23b2602e9cedebf8e0d4867782d61eb28',1,2,'2016-10-31 03:39:17'),(29,'inventory','898783d036f1416b63c2742827574b961b440a563ef57422d441a040f6ef1033',1,4,'2016-10-31 03:39:56'),(30,'auditor','42ef4104db95710e7bf4139ddd5bc9753a8643c47046b1c22b8b6284dd8686f8',1,5,'2016-10-31 03:40:08'),(31,'test1','',1,1,'2016-11-01 08:52:04'),(32,'test2','qwer',1,3,'2016-11-01 08:41:00'),(33,'test3','',1,3,'2016-11-01 08:53:52'),(34,'tested again','0ac6a6ff0ac40d7324318273f6abb51d530773c1ce338d4789fd9696ff5f4652',2,2,'2016-11-01 09:05:20'),(35,'test5','ccca3b3a534d9d2cd4ae5baafe2184fd92137dfdadfed26da31f838e8fe6fe6c',1,4,'2016-12-24 07:12:05');
/*!40000 ALTER TABLE `Account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AccountStatus`
--

DROP TABLE IF EXISTS `AccountStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AccountStatus` (
  `accountStatusID` int(11) NOT NULL AUTO_INCREMENT,
  `accountStatusName` varchar(255) NOT NULL,
  PRIMARY KEY (`accountStatusID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AccountStatus`
--

LOCK TABLES `AccountStatus` WRITE;
/*!40000 ALTER TABLE `AccountStatus` DISABLE KEYS */;
INSERT INTO `AccountStatus` VALUES (1,'Activated'),(2,'Deactivated');
/*!40000 ALTER TABLE `AccountStatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AccountType`
--

DROP TABLE IF EXISTS `AccountType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AccountType` (
  `accountTypeID` int(11) NOT NULL AUTO_INCREMENT,
  `accountTypeName` varchar(255) NOT NULL,
  PRIMARY KEY (`accountTypeID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AccountType`
--

LOCK TABLES `AccountType` WRITE;
/*!40000 ALTER TABLE `AccountType` DISABLE KEYS */;
INSERT INTO `AccountType` VALUES (1,'CEO'),(2,'Secretary'),(3,'Accountant'),(4,'Inventory Manager'),(5,'Auditor');
/*!40000 ALTER TABLE `AccountType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Clinic`
--

DROP TABLE IF EXISTS `Clinic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Clinic` (
  `clinicID` int(11) NOT NULL AUTO_INCREMENT,
  `customerID` int(11) DEFAULT NULL,
  `clinicAddress` varchar(255) DEFAULT NULL,
  `clinicPhoneNumber` varchar(15) DEFAULT NULL,
  `clinicName` varchar(255) DEFAULT NULL,
  `dateCreated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `provinceID` int(11) DEFAULT NULL,
  `lastEdittedBy` varchar(50) NOT NULL,
  PRIMARY KEY (`clinicID`),
  KEY `customerID` (`customerID`),
  KEY `provinceID` (`provinceID`),
  CONSTRAINT `Clinic_ibfk_1` FOREIGN KEY (`customerID`) REFERENCES `Customer` (`customerID`),
  CONSTRAINT `Clinic_ibfk_2` FOREIGN KEY (`provinceID`) REFERENCES `Province` (`provinceID`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Clinic`
--

LOCK TABLES `Clinic` WRITE;
/*!40000 ALTER TABLE `Clinic` DISABLE KEYS */;
INSERT INTO `Clinic` VALUES (12,3461,'3K Rojumar Corp. Bldg., Grace Park','3237094','GC Tejada Dental Clinic','2016-10-17 07:46:50',84,'admin'),(13,3462,'192 Nadurata st. 8th avenue, West Grace Park','7826967','MJCR Ortho Dental Clinic','2016-10-17 07:49:27',84,'admin'),(14,3463,'363 Rizal ave., Extension Caloocan','3665537','Dr. Tung Dental Clinic','2016-10-17 07:51:03',84,'admin'),(15,3464,'3/F Victory Central Mall Monumento','3321066','Toothlink Dental Clinic','2016-10-17 07:52:42',84,'admin'),(16,3465,'31 Gov. Pascual Avenue, Tinajeros','4349549','Dental Clinic','2016-10-17 07:54:44',84,'admin'),(17,3461,'217 M.H. Del Pilar St., 11th Ave., Grace Park','4849883','Mouth Fresh Dental Clinic','2016-10-17 07:56:17',84,'admin'),(18,3466,'Ermita, Manila','7392330','Physician\'s Diagnostic Services Center','2016-11-17 09:54:41',84,'admin'),(20,3467,'1234','1234a','1234','2017-01-07 11:30:16',36,'admin');
/*!40000 ALTER TABLE `Clinic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Customer`
--

DROP TABLE IF EXISTS `Customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Customer` (
  `PRCID` varchar(50) NOT NULL DEFAULT '0',
  `customerFirstName` varchar(100) DEFAULT NULL,
  `customerMobileNumber` varchar(20) DEFAULT NULL,
  `customerTelephoneNumber` varchar(15) DEFAULT NULL,
  `dateCreated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `salesRepID` int(11) DEFAULT NULL,
  `customerID` int(11) NOT NULL AUTO_INCREMENT,
  `customerLastName` varchar(100) DEFAULT NULL,
  `lastEdittedBy` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`customerID`),
  KEY `salesRepID` (`salesRepID`),
  CONSTRAINT `salesRepID` FOREIGN KEY (`salesRepID`) REFERENCES `SalesRep` (`salesRepID`)
) ENGINE=InnoDB AUTO_INCREMENT=3469 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Customer`
--

LOCK TABLES `Customer` WRITE;
/*!40000 ALTER TABLE `Customer` DISABLE KEYS */;
INSERT INTO `Customer` VALUES ('7925','Bert','09178570467','7822127','2016-10-17 07:46:50',1,3461,'Tejada',NULL),('7736','Glenda','09174133290','3669314','2016-10-17 07:49:26',2,3462,'Rusuello',NULL),('5480','Eugene','09067133582','3665537','2016-10-17 07:51:03',3,3463,'Tung',NULL),('9935','Leah','09173231026','3321066','2016-10-17 07:52:42',5,3464,'Cruz',NULL),('8239','Rommel','09167827495','4349549','2016-10-17 07:54:44',6,3465,'Caponpon',NULL),('2643','Mazzini','09232911209','7392330','2016-11-17 09:54:41',6,3466,'Giuseppe',NULL),('123','123','1234','1234','2017-01-07 11:30:15',1,3467,'444','admin'),('123','123','123','1234','2017-01-07 11:43:22',5,3468,'123','admin');
/*!40000 ALTER TABLE `Customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Invoice`
--

DROP TABLE IF EXISTS `Invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Invoice` (
  `invoiceID` int(11) NOT NULL AUTO_INCREMENT,
  `customerID` int(11) DEFAULT NULL,
  `clinicID` int(11) DEFAULT NULL,
  `invoiceDate` date DEFAULT NULL,
  `deliveryDate` date DEFAULT NULL,
  `termsOfPayment` varchar(20) DEFAULT NULL,
  `paymentDueDate` date DEFAULT NULL,
  `datePaid` date DEFAULT '0000-00-01',
  `dateClosed` date DEFAULT '0000-00-01',
  `statusID` int(11) DEFAULT NULL,
  `overdueFee` float DEFAULT NULL,
  `dateCreated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `invoiceName` varchar(255) DEFAULT NULL,
  `amountPaid` float NOT NULL DEFAULT '0',
  `discount` float DEFAULT '0',
  `lastEdittedBy` varchar(50) DEFAULT NULL,
  `amountDue` float NOT NULL DEFAULT '0',
  `dateDelivered` date DEFAULT NULL,
  PRIMARY KEY (`invoiceID`),
  KEY `PRCID` (`customerID`),
  KEY `clinicID` (`clinicID`),
  KEY `statusID` (`statusID`),
  CONSTRAINT `Invoice_ibfk_2` FOREIGN KEY (`clinicID`) REFERENCES `Clinic` (`clinicID`),
  CONSTRAINT `Invoice_ibfk_3` FOREIGN KEY (`customerID`) REFERENCES `Customer` (`customerID`),
  CONSTRAINT `Invoice_ibfk_4` FOREIGN KEY (`statusID`) REFERENCES `InvoiceStatus` (`statusID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Invoice`
--

LOCK TABLES `Invoice` WRITE;
/*!40000 ALTER TABLE `Invoice` DISABLE KEYS */;
INSERT INTO `Invoice` VALUES (1,3465,16,'2016-12-26','2016-12-31','Cash','2016-12-30','2016-12-26','2016-12-26',1,0,'2016-12-26 09:34:53','Dental Chair - Caponpon',18000,2000,'admin',18000,'2016-12-26'),(2,3464,15,'2016-12-29','2016-12-31','Cash','2016-12-30','2016-12-29','2016-12-29',1,0,'2016-12-29 02:52:59','Hose - Cruz Leah',1700,300,'admin',1700,'2016-12-31'),(3,3462,13,'2017-01-23','2017-01-26','Cash','2017-01-25',NULL,NULL,2,0,'2017-01-23 08:14:23','Glenda - Dental Chair',0,50000,'admin',150000,NULL),(4,3466,18,'2017-03-08','2017-03-09','Cash','2017-03-09',NULL,NULL,2,0,'2017-03-08 08:45:46','TestInvoice',0,0,'admin',20000,NULL),(5,3462,13,'2017-03-08','2017-03-10','Cash','2017-03-10','2017-03-10',NULL,2,0,'2017-03-08 09:01:33','test invoice 2',1500,0,'admin',1500,NULL);
/*!40000 ALTER TABLE `Invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `InvoiceItem`
--

DROP TABLE IF EXISTS `InvoiceItem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `InvoiceItem` (
  `invoiceItemID` int(11) NOT NULL AUTO_INCREMENT,
  `invoiceID` int(11) DEFAULT NULL,
  `productID` int(11) DEFAULT NULL,
  `quantityPurchased` int(11) DEFAULT NULL,
  PRIMARY KEY (`invoiceItemID`),
  KEY `invoiceID` (`invoiceID`),
  KEY `productID` (`productID`),
  CONSTRAINT `InvoiceItem_ibfk_1` FOREIGN KEY (`invoiceID`) REFERENCES `Invoice` (`invoiceID`),
  CONSTRAINT `InvoiceItem_ibfk_2` FOREIGN KEY (`productID`) REFERENCES `Product` (`productID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `InvoiceItem`
--

LOCK TABLES `InvoiceItem` WRITE;
/*!40000 ALTER TABLE `InvoiceItem` DISABLE KEYS */;
INSERT INTO `InvoiceItem` VALUES (1,1,1,1),(2,2,2,2),(3,3,1,1),(4,4,1,1),(5,5,2,1);
/*!40000 ALTER TABLE `InvoiceItem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `InvoiceStatus`
--

DROP TABLE IF EXISTS `InvoiceStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `InvoiceStatus` (
  `statusID` int(11) NOT NULL AUTO_INCREMENT,
  `statusName` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`statusID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `InvoiceStatus`
--

LOCK TABLES `InvoiceStatus` WRITE;
/*!40000 ALTER TABLE `InvoiceStatus` DISABLE KEYS */;
INSERT INTO `InvoiceStatus` VALUES (1,'Completed'),(2,'In Progress'),(3,'Cancelled');
/*!40000 ALTER TABLE `InvoiceStatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Product`
--

DROP TABLE IF EXISTS `Product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Product` (
  `productID` int(11) NOT NULL AUTO_INCREMENT,
  `productName` varchar(255) DEFAULT NULL,
  `productDescription` text,
  `productPrice` float DEFAULT NULL,
  `restockPrice` float DEFAULT NULL,
  `stocksRemaining` int(11) DEFAULT NULL,
  `lowStock` int(11) DEFAULT NULL,
  `brand` varchar(50) DEFAULT NULL,
  `productClassID` int(11) DEFAULT NULL,
  `color` varchar(20) DEFAULT NULL,
  `dateCreated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastEdittedBy` varchar(50) DEFAULT NULL,
  `supplierID` int(11) NOT NULL,
  PRIMARY KEY (`productID`),
  KEY `productClassID` (`productClassID`),
  KEY `supplierID` (`supplierID`),
  CONSTRAINT `Product_ibfk_1` FOREIGN KEY (`productClassID`) REFERENCES `ProductClass` (`productClassID`),
  CONSTRAINT `Product_ibfk_2` FOREIGN KEY (`supplierID`) REFERENCES `Supplier` (`supplierID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Product`
--

LOCK TABLES `Product` WRITE;
/*!40000 ALTER TABLE `Product` DISABLE KEYS */;
INSERT INTO `Product` VALUES (1,'Dental Chair','Standard dental chair',200000,175000,6,6,'Zhermack',1,'White','2016-12-23 05:28:39','admin',1),(2,'Hose','attached to Dental Chairs to spray water',2000,1500,5,5,'Denta',3,'White','2016-12-29 02:38:55','admin',1);
/*!40000 ALTER TABLE `Product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ProductClass`
--

DROP TABLE IF EXISTS `ProductClass`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProductClass` (
  `productClassID` int(11) NOT NULL AUTO_INCREMENT,
  `productClassName` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`productClassID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProductClass`
--

LOCK TABLES `ProductClass` WRITE;
/*!40000 ALTER TABLE `ProductClass` DISABLE KEYS */;
INSERT INTO `ProductClass` VALUES (1,'Dental Unit'),(2,'Impression Materials'),(3,'Attachment');
/*!40000 ALTER TABLE `ProductClass` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Province`
--

DROP TABLE IF EXISTS `Province`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Province` (
  `provinceID` int(11) NOT NULL AUTO_INCREMENT,
  `provinceName` varchar(50) NOT NULL,
  `provinceDivision` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`provinceID`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Province`
--

LOCK TABLES `Province` WRITE;
/*!40000 ALTER TABLE `Province` DISABLE KEYS */;
INSERT INTO `Province` VALUES (36,'Abra','Luzon'),(37,'Agusan del Norte','Mindanao'),(38,'Agusan del Sur','Mindanao'),(39,'Aklan','Visayas'),(40,'Albay','Luzon'),(41,'Antique','Visayas'),(42,'Apayao','Luzon'),(43,'Aurora','Luzon'),(44,'Basilan','Mindanao'),(45,'Bataan','Luzon'),(46,'Batanes','Luzon'),(47,'Batangas','Luzon'),(48,'Benguet','Luzon'),(49,'Biliran','Visayas'),(50,'Bohol','Visayas'),(51,'Bukidnon','Mindanao'),(52,'Bulacan','Luzon'),(53,'Cagayan','Luzon'),(54,'Camarines Norte','Luzon'),(55,'Camarines Sur','Luzon'),(56,'Camiguin','Mindanao'),(57,'Capiz','Visayas'),(58,'Catanduanes','Luzon'),(59,'Cavite','Luzon'),(60,'Cebu','Visayas'),(61,'Compostela Valley','Mindanao'),(62,'Cotabato','Mindanao'),(63,'Davao del Norte','Mindanao'),(64,'Davao del Sur','Mindanao'),(65,'Davao Occidental','Mindanao'),(66,'Davao Oriental','Mindanao'),(67,'Dinagat Islands','Mindanao'),(68,'Eastern Samar','Visayas'),(69,'Guimaras','Visayas'),(70,'Ifugao','Luzon'),(71,'Ilocos Norte','Luzon'),(72,'Ilocos Sur','Luzon'),(73,'Iloilo','Visayas'),(74,'Isabela','Luzon'),(75,'Kalinga','Luzon'),(76,'La Union','Luzon'),(77,'Laguna','Luzon'),(78,'Lanao del Norte','Mindanao'),(79,'Lanao del Sur','Mindanao'),(80,'Leyte','Visayas'),(81,'Maguindanao','Mindanao'),(82,'Marinduque','Luzon'),(83,'Masbate','Luzon'),(84,'Metro Manila','Luzon');
/*!40000 ALTER TABLE `Province` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RestockOrder`
--

DROP TABLE IF EXISTS `RestockOrder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RestockOrder` (
  `restockOrderID` int(11) NOT NULL AUTO_INCREMENT,
  `productID` int(11) DEFAULT NULL,
  `ROName` varchar(255) DEFAULT NULL,
  `numberOfPiecesOrdered` int(11) NOT NULL DEFAULT '0',
  `numberOfPiecesReceived` int(11) NOT NULL DEFAULT '0',
  `supplierID` int(11) DEFAULT NULL,
  `purpose` text,
  `RODateDue` date NOT NULL,
  `RODateDelivered` date DEFAULT NULL,
  `amountPaid` float NOT NULL DEFAULT '0',
  `discount` float NOT NULL DEFAULT '0',
  `dateCreated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastEdittedBy` varchar(50) NOT NULL,
  `datePaid` date DEFAULT NULL,
  PRIMARY KEY (`restockOrderID`),
  KEY `productID` (`productID`),
  KEY `supplierID` (`supplierID`),
  CONSTRAINT `RestockOrder_ibfk_1` FOREIGN KEY (`productID`) REFERENCES `Product` (`productID`),
  CONSTRAINT `RestockOrder_ibfk_2` FOREIGN KEY (`supplierID`) REFERENCES `Supplier` (`supplierID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RestockOrder`
--

LOCK TABLES `RestockOrder` WRITE;
/*!40000 ALTER TABLE `RestockOrder` DISABLE KEYS */;
INSERT INTO `RestockOrder` VALUES (1,1,'zhermack dental chair RO',10,10,1,'Replenish stocks','2016-12-31','2016-10-31',17500000,0,'2016-12-24 07:42:57','admin',NULL),(2,2,'Hose 2016-12-29',10,10,1,'Replenish stocks','2016-12-31','2016-12-29',15000,0,'2016-12-29 02:40:36','admin',NULL),(3,1,'zhermack dental chair RO 2',1,0,1,'another chair RO','2017-03-30',NULL,0,0,'2017-03-28 11:04:11','admin',NULL);
/*!40000 ALTER TABLE `RestockOrder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SalesRep`
--

DROP TABLE IF EXISTS `SalesRep`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SalesRep` (
  `salesRepID` int(11) NOT NULL AUTO_INCREMENT,
  `salesRepFirstName` varchar(100) NOT NULL,
  `salesRepMobileNumber` varchar(12) DEFAULT NULL,
  `salesRepAddress` varchar(255) DEFAULT NULL,
  `salesRepLastName` varchar(100) NOT NULL,
  `lastEdittedBy` varchar(50) DEFAULT NULL,
  `dateCreated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`salesRepID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SalesRep`
--

LOCK TABLES `SalesRep` WRITE;
/*!40000 ALTER TABLE `SalesRep` DISABLE KEYS */;
INSERT INTO `SalesRep` VALUES (1,'Jasteen','09174831392','','Reyes',NULL,'0000-00-00 00:00:00'),(2,'Reyes','09072983477','','Jasteen',NULL,'0000-00-00 00:00:00'),(3,'Chlyde','09064920399','','Ranada',NULL,'0000-00-00 00:00:00'),(4,'Kyle','09231948832','','Danao',NULL,'0000-00-00 00:00:00'),(5,'Josh','09183933321','','Cruz',NULL,'0000-00-00 00:00:00'),(6,'Justine','09876543211','','Reyes',NULL,'0000-00-00 00:00:00');
/*!40000 ALTER TABLE `SalesRep` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Supplier`
--

DROP TABLE IF EXISTS `Supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Supplier` (
  `supplierID` int(11) NOT NULL AUTO_INCREMENT,
  `supplierName` varchar(100) NOT NULL,
  `supplierAddress` varchar(255) NOT NULL,
  `supplierContactNumber` varchar(12) NOT NULL,
  `productClassID` int(11) NOT NULL,
  `dateCreated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastEdittedBy` varchar(50) NOT NULL,
  PRIMARY KEY (`supplierID`),
  KEY `productClassID` (`productClassID`),
  CONSTRAINT `Supplier_ibfk_1` FOREIGN KEY (`productClassID`) REFERENCES `ProductClass` (`productClassID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Supplier`
--

LOCK TABLES `Supplier` WRITE;
/*!40000 ALTER TABLE `Supplier` DISABLE KEYS */;
INSERT INTO `Supplier` VALUES (1,'Zhermack','123456 there','5432',1,'2016-12-23 05:13:43','admin'),(2,'Colgate','3421 elsewhere','3125',2,'2016-12-23 05:14:26','admin');
/*!40000 ALTER TABLE `Supplier` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-04-05 17:48:39
