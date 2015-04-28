-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 26, 2015 at 11:07 PM
-- Server version: 5.6.21
-- PHP Version: 5.6.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `dmsdatabase`
--

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE IF NOT EXISTS `login` (
`UserId` int(11) NOT NULL,
  `FirstName` varchar(50) NOT NULL,
  `LastName` varchar(50) NOT NULL,
  `MavEmail` varchar(50) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `DeviceId` varchar(50) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`UserId`, `FirstName`, `LastName`, `MavEmail`, `Password`, `DeviceId`) VALUES
(1, 'Arun', 'Gopinathan', 'arun.gopinathan@mavs.uta.edu', '1234', ''),
(2, 'Venkataprabha', 'Varadharajan', 'venkataprab.varadharajan@mavs.uta.edu', '123', '');

-- --------------------------------------------------------

--
-- Table structure for table `meetingdates`
--

CREATE TABLE IF NOT EXISTS `meetingdates` (
`MeetingDateId` int(11) NOT NULL,
  `MeetingId` int(11) NOT NULL,
  `MeetingDate` date NOT NULL,
  `MeetingStartTime` time NOT NULL,
  `MeetingEndTime` time NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `meetingdates`
--

INSERT INTO `meetingdates` (`MeetingDateId`, `MeetingId`, `MeetingDate`, `MeetingStartTime`, `MeetingEndTime`) VALUES
(7, 5, '2015-04-27', '11:00:00', '11:30:00'),
(8, 5, '2015-04-27', '12:00:00', '12:30:00');

-- --------------------------------------------------------

--
-- Table structure for table `participants`
--

CREATE TABLE IF NOT EXISTS `participants` (
`ParticipantId` int(11) NOT NULL,
  `MeetingId` int(11) NOT NULL,
  `UserEmailId` varchar(50) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `participants`
--

INSERT INTO `participants` (`ParticipantId`, `MeetingId`, `UserEmailId`) VALUES
(3, 5, 'Venkataprab.Varadharajan@mavs.uta.edu');

-- --------------------------------------------------------

--
-- Table structure for table `proposedmeeting`
--

CREATE TABLE IF NOT EXISTS `proposedmeeting` (
`MeetingId` int(11) NOT NULL,
  `UserId` int(11) NOT NULL,
  `MeetingName` varchar(20) NOT NULL,
  `Agenda` varchar(100) NOT NULL,
  `Location` varchar(20) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `proposedmeeting`
--

INSERT INTO `proposedmeeting` (`MeetingId`, `UserId`, `MeetingName`, `Agenda`, `Location`) VALUES
(5, 1, 'Testing Meeting', 'To check whether the XML request is parsed properly', 'Vintage Pads #102');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `login`
--
ALTER TABLE `login`
 ADD PRIMARY KEY (`UserId`);

--
-- Indexes for table `meetingdates`
--
ALTER TABLE `meetingdates`
 ADD PRIMARY KEY (`MeetingDateId`);

--
-- Indexes for table `participants`
--
ALTER TABLE `participants`
 ADD PRIMARY KEY (`ParticipantId`);

--
-- Indexes for table `proposedmeeting`
--
ALTER TABLE `proposedmeeting`
 ADD PRIMARY KEY (`MeetingId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `login`
--
ALTER TABLE `login`
MODIFY `UserId` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `meetingdates`
--
ALTER TABLE `meetingdates`
MODIFY `MeetingDateId` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `participants`
--
ALTER TABLE `participants`
MODIFY `ParticipantId` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `proposedmeeting`
--
ALTER TABLE `proposedmeeting`
MODIFY `MeetingId` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
