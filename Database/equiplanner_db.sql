-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 21, 2026 at 10:22 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `equiplanner_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `address`
--

CREATE TABLE `address` (
  `AddressID` int(11) NOT NULL,
  `ZipCode` varchar(64) NOT NULL,
  `HouseNumber` int(11) NOT NULL,
  `Suffix` char(1) DEFAULT NULL,
  `Street` varchar(64) NOT NULL,
  `City` varchar(64) NOT NULL,
  `Country` varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `address`
--

INSERT INTO `address` (`AddressID`, `ZipCode`, `HouseNumber`, `Suffix`, `Street`, `City`, `Country`) VALUES
(1, '4611GV', 66, NULL, 'Noordzijde haven', 'Bergen op Zoom', 'Nederland'),
(2, '4611GV', 66, NULL, 'Noordzijde haven', 'Bergen op Zoom', 'Nederland'),
(3, '4631HV', 13, NULL, 'Blauwehandstraat', 'Ossendrecht', 'Nederland'),
(4, '4631HV', 13, NULL, 'Blauwehandstraat', 'Ossendrecht', 'Nederland'),
(5, '4615', 58, NULL, 'Noordzijde Zoom', 'Bergen op Zoom', 'Nederland'),
(6, '4623JK', 39, NULL, 'Molenzicht', 'Bergen op Zoom', 'Nederland');

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `AdminID` int(11) NOT NULL,
  `Username` varchar(100) NOT NULL,
  `Password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`AdminID`, `Username`, `Password`) VALUES
(1, 'EquiAdmin', '$2a$10$PzW3rEfAkrYMXLR3otLlluENksmpZY40MMI8ZCv5V2oYbv3IJAEdq');

-- --------------------------------------------------------

--
-- Table structure for table `combination`
--

CREATE TABLE `combination` (
  `CombinationID` int(11) NOT NULL,
  `LessonID` int(11) NOT NULL,
  `RiderID` int(11) NOT NULL,
  `HorseID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `combination`
--

INSERT INTO `combination` (`CombinationID`, `LessonID`, `RiderID`, `HorseID`) VALUES
(1, 5, 2, 3),
(2, 5, 4, 2),
(3, 6, 4, 3);

-- --------------------------------------------------------

--
-- Table structure for table `horse`
--

CREATE TABLE `horse` (
  `HorseId` int(11) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Age` int(11) NOT NULL,
  `IsLame` tinyint(1) NOT NULL,
  `MaxHoursOfWork` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `horse`
--

INSERT INTO `horse` (`HorseId`, `Name`, `Age`, `IsLame`, `MaxHoursOfWork`) VALUES
(2, 'Enjoy', 17, 0, 1),
(3, 'ZZ', 21, 0, 2);

-- --------------------------------------------------------

--
-- Table structure for table `instructor`
--

CREATE TABLE `instructor` (
  `PersonID` int(11) NOT NULL,
  `DoesTrailRides` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `instructor`
--

INSERT INTO `instructor` (`PersonID`, `DoesTrailRides`) VALUES
(3, 0),
(5, 1);

-- --------------------------------------------------------

--
-- Table structure for table `lesson`
--

CREATE TABLE `lesson` (
  `LessonID` int(11) NOT NULL,
  `LessonDate` date NOT NULL,
  `StartTime` time NOT NULL,
  `Duration` double NOT NULL,
  `Location` varchar(100) NOT NULL,
  `InstructorID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `lesson`
--

INSERT INTO `lesson` (`LessonID`, `LessonDate`, `StartTime`, `Duration`, `Location`, `InstructorID`) VALUES
(5, '2026-02-23', '20:00:00', 60, 'Binnenbak', 3),
(6, '2026-02-24', '14:00:00', 60, 'Buitenbak', 5);

-- --------------------------------------------------------

--
-- Table structure for table `person`
--

CREATE TABLE `person` (
  `PersonID` int(11) NOT NULL,
  `FirstName` varchar(64) NOT NULL,
  `LastName` varchar(64) NOT NULL,
  `DateOfBirth` date NOT NULL,
  `Email` varchar(128) NOT NULL,
  `PhoneNumber` varchar(32) NOT NULL,
  `AddressID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `person`
--

INSERT INTO `person` (`PersonID`, `FirstName`, `LastName`, `DateOfBirth`, `Email`, `PhoneNumber`, `AddressID`) VALUES
(2, 'Lisa', 'Tyem', '1998-10-18', 'lisatyem@gmail.com', '0611227271', 2),
(3, 'Marjo', 'van Dalen', '1987-07-12', 'marjovD@gmail.com', '0633726841', 4),
(4, 'Nicky', 'Hermes', '1995-04-23', 'nHermes@gmail.com', '0677493828', 5),
(5, 'Loes', 'Breijber', '2001-05-21', 'lBreijber@gmail.com', '0626383762', 6);

-- --------------------------------------------------------

--
-- Table structure for table `rider`
--

CREATE TABLE `rider` (
  `PersonID` int(11) NOT NULL,
  `HasLeaseHorse` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rider`
--

INSERT INTO `rider` (`PersonID`, `HasLeaseHorse`) VALUES
(2, 1),
(4, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `address`
--
ALTER TABLE `address`
  ADD PRIMARY KEY (`AddressID`),
  ADD UNIQUE KEY `UA_Address` (`ZipCode`,`HouseNumber`,`Suffix`);

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`AdminID`),
  ADD UNIQUE KEY `Username` (`Username`);

--
-- Indexes for table `combination`
--
ALTER TABLE `combination`
  ADD PRIMARY KEY (`CombinationID`),
  ADD UNIQUE KEY `UQ_Lesson_Rider` (`LessonID`,`RiderID`),
  ADD UNIQUE KEY `UQ_Lesson_Horse` (`LessonID`,`HorseID`),
  ADD KEY `RiderID` (`RiderID`),
  ADD KEY `HorseID` (`HorseID`);

--
-- Indexes for table `horse`
--
ALTER TABLE `horse`
  ADD PRIMARY KEY (`HorseId`);

--
-- Indexes for table `instructor`
--
ALTER TABLE `instructor`
  ADD PRIMARY KEY (`PersonID`);

--
-- Indexes for table `lesson`
--
ALTER TABLE `lesson`
  ADD PRIMARY KEY (`LessonID`),
  ADD KEY `fk_lesson_instructor` (`InstructorID`);

--
-- Indexes for table `person`
--
ALTER TABLE `person`
  ADD PRIMARY KEY (`PersonID`),
  ADD KEY `AddressID` (`AddressID`);

--
-- Indexes for table `rider`
--
ALTER TABLE `rider`
  ADD PRIMARY KEY (`PersonID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `address`
--
ALTER TABLE `address`
  MODIFY `AddressID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `AdminID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `combination`
--
ALTER TABLE `combination`
  MODIFY `CombinationID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `horse`
--
ALTER TABLE `horse`
  MODIFY `HorseId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `lesson`
--
ALTER TABLE `lesson`
  MODIFY `LessonID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `person`
--
ALTER TABLE `person`
  MODIFY `PersonID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `combination`
--
ALTER TABLE `combination`
  ADD CONSTRAINT `combination_ibfk_1` FOREIGN KEY (`LessonID`) REFERENCES `lesson` (`LessonID`),
  ADD CONSTRAINT `combination_ibfk_2` FOREIGN KEY (`RiderID`) REFERENCES `rider` (`PersonID`),
  ADD CONSTRAINT `combination_ibfk_3` FOREIGN KEY (`HorseID`) REFERENCES `horse` (`HorseId`);

--
-- Constraints for table `instructor`
--
ALTER TABLE `instructor`
  ADD CONSTRAINT `instructor_ibfk_1` FOREIGN KEY (`PersonID`) REFERENCES `person` (`PersonID`);

--
-- Constraints for table `lesson`
--
ALTER TABLE `lesson`
  ADD CONSTRAINT `fk_lesson_instructor` FOREIGN KEY (`InstructorID`) REFERENCES `instructor` (`PersonID`);

--
-- Constraints for table `person`
--
ALTER TABLE `person`
  ADD CONSTRAINT `person_ibfk_1` FOREIGN KEY (`AddressID`) REFERENCES `address` (`AddressID`);

--
-- Constraints for table `rider`
--
ALTER TABLE `rider`
  ADD CONSTRAINT `rider_ibfk_1` FOREIGN KEY (`PersonID`) REFERENCES `person` (`PersonID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
