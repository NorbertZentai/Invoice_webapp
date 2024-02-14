-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: Feb 14, 2024 at 04:47 PM
-- Server version: 5.7.39
-- PHP Version: 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `invoiceHandler`
--

-- --------------------------------------------------------

--
-- Table structure for table `INVOICE`
--

CREATE TABLE `INVOICE` (
  `Id` int(11) NOT NULL,
  `Customer_Name` varchar(24) NOT NULL,
  `Date_Of_Invoice` varchar(24) NOT NULL,
  `Due_Date` varchar(24) NOT NULL,
  `Title` varchar(24) NOT NULL,
  `Comment` varchar(200) NOT NULL,
  `Price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `INVOICE`
--

INSERT INTO `INVOICE` (`Id`, `Customer_Name`, `Date_Of_Invoice`, `Due_Date`, `Title`, `Comment`, `Price`) VALUES
(16, 'Akos', '2024-02-18', '2024-03-02', 'COD', 'Koltes puff-puff-ra ', 30002),
(21, 'ABEEL', '2024-02-04', '2024-02-15', 'Valorant', 'Megerte', 10000),
(31, 'Erik', '2024-02-04', '2024-02-16', 'Fallguys', 'Not just the guys are falling.', 4000),
(33, 'Game', '2024-02-21', '2024-02-23', 'Game Of The Year', 'Bought it', 26999),
(34, 'Zsófi', '2024-02-15', '2024-02-23', 'Konyha felújítás', 'Szintpénz', 2000);

-- --------------------------------------------------------

--
-- Table structure for table `ROLE`
--

CREATE TABLE `ROLE` (
  `Id` int(11) NOT NULL,
  `Role_Name` varchar(16) NOT NULL,
  `Description` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `ROLE`
--

INSERT INTO `ROLE` (`Id`, `Role_Name`, `Description`) VALUES
(1, 'admin', 'Has access to all the pages and the Admin page.'),
(2, 'accountant', 'Can create invoices, see the invoice list, and read the invoices. Does not have permission to the admin page.'),
(3, 'user', 'Can see the invoices page and read the invoices.');

-- --------------------------------------------------------

--
-- Table structure for table `SPRING_SESSION`
--

CREATE TABLE `SPRING_SESSION` (
  `PRIMARY_ID` char(36) NOT NULL,
  `SESSION_ID` char(36) NOT NULL,
  `CREATION_TIME` bigint(20) NOT NULL,
  `LAST_ACCESS_TIME` bigint(20) NOT NULL,
  `MAX_INACTIVE_INTERVAL` int(11) NOT NULL,
  `EXPIRY_TIME` bigint(20) NOT NULL,
  `PRINCIPAL_NAME` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `SPRING_SESSION_ATTRIBUTES`
--

CREATE TABLE `SPRING_SESSION_ATTRIBUTES` (
  `SESSION_PRIMARY_ID` char(36) NOT NULL,
  `ATTRIBUTE_NAME` varchar(200) NOT NULL,
  `ATTRIBUTE_BYTES` blob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `USER`
--

CREATE TABLE `USER` (
  `Id` int(11) NOT NULL,
  `Username` varchar(24) NOT NULL,
  `Password` varchar(120) NOT NULL,
  `Role` varchar(255) DEFAULT NULL,
  `Last_Login` varchar(24) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `USER`
--

INSERT INTO `USER` (`Id`, `Username`, `Password`, `Role`, `Last_Login`) VALUES
(13, 'Balazs', '$2a$12$HDxD7oip58ge/vGnA1dxzOv.MXW0ptLBigu8QTSoDu7lS68XsRDxK', 'accountant', '2024-02-07'),
(18, 'Aron', '$2a$12$pHjW08le0zB27KrujOJPPOLjmVfxChf4an.0Or0v4Ykb1kLbQeQ.a', 'user', '2024-02-13'),
(44, 'Erik', '$2a$12$eZcZM4vnBfcsIKsp3Wc8xeiPNdCkE95V0Zbe1cKxWCqsbMY9.Yvmi', 'admin', '2024-02-14');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `INVOICE`
--
ALTER TABLE `INVOICE`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `ROLE`
--
ALTER TABLE `ROLE`
  ADD PRIMARY KEY (`Id`),
  ADD UNIQUE KEY `Role_Name` (`Role_Name`),
  ADD KEY `Id` (`Id`);

--
-- Indexes for table `SPRING_SESSION`
--
ALTER TABLE `SPRING_SESSION`
  ADD PRIMARY KEY (`PRIMARY_ID`),
  ADD KEY `SPRING_SESSION_IX1` (`SESSION_ID`);

--
-- Indexes for table `SPRING_SESSION_ATTRIBUTES`
--
ALTER TABLE `SPRING_SESSION_ATTRIBUTES`
  ADD PRIMARY KEY (`SESSION_PRIMARY_ID`,`ATTRIBUTE_NAME`);

--
-- Indexes for table `USER`
--
ALTER TABLE `USER`
  ADD PRIMARY KEY (`Id`),
  ADD UNIQUE KEY `Username` (`Username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `INVOICE`
--
ALTER TABLE `INVOICE`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;

--
-- AUTO_INCREMENT for table `ROLE`
--
ALTER TABLE `ROLE`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `USER`
--
ALTER TABLE `USER`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `SPRING_SESSION_ATTRIBUTES`
--
ALTER TABLE `SPRING_SESSION_ATTRIBUTES`
  ADD CONSTRAINT `SPRING_SESSION_ATTRIBUTES_FK` FOREIGN KEY (`SESSION_PRIMARY_ID`) REFERENCES `SPRING_SESSION` (`PRIMARY_ID`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
