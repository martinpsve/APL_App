-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Värd: 127.0.0.1
-- Tid vid skapande: 25 apr 2018 kl 10:21
-- Serverversion: 10.1.19-MariaDB
-- PHP-version: 7.0.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Databas: `apl`
--

-- --------------------------------------------------------

--
-- Tabellstruktur `anvandare`
--

CREATE TABLE `anvandare` (
  `AnvandarID` int(10) NOT NULL,
  `Anvandarnamn` varchar(10) NOT NULL,
  `Losenord` varchar(15) NOT NULL,
  `Fnamn` varchar(15) DEFAULT NULL,
  `Enamn` varchar(15) NOT NULL,
  `Telefonnummer` int(10) DEFAULT NULL,
  `Email` varchar(50) DEFAULT NULL,
  `Onskan` varchar(50) DEFAULT NULL,
  `matchning` varchar(50) DEFAULT NULL,
  `AdminID` tinyint(1) NOT NULL,
  `HandledareID` tinyint(1) NOT NULL,
  `ArbetsplatsID` int(11) DEFAULT NULL,
  `ElevID` tinyint(1) NOT NULL,
  `KlassID` int(11) DEFAULT NULL,
  `LarareID` tinyint(1) NOT NULL,
  `Undervisar` int(11) DEFAULT NULL,
  `KansliID` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumpning av Data i tabell `anvandare`
--

INSERT INTO `anvandare` (`AnvandarID`, `Anvandarnamn`, `Losenord`, `Fnamn`, `Enamn`, `Telefonnummer`, `Email`, `Onskan`, `matchning`, `AdminID`, `HandledareID`, `ArbetsplatsID`, `ElevID`, `KlassID`, `LarareID`, `Undervisar`, `KansliID`) VALUES
(1, 'KajBo', '123', 'Kaj', 'Boo', 123456789, 'Kurt@live.se', NULL, NULL, 1, 0, NULL, 0, NULL, 0, NULL, 0),
(2, '', '123', 'Erik', 'Eriknsson', 748640111, 'Erik@live.se', NULL, NULL, 0, 0, NULL, 1, NULL, 0, NULL, 0),
(3, '', '123', 'Kalle', 'Kallesson', 748640222, 'Kalle@live.se', NULL, NULL, 0, 0, NULL, 1, NULL, 0, NULL, 0),
(4, '', '123', 'Kurst', 'Rubensson', 748640333, 'Karl@live.se', 'null', 'null', 0, 0, NULL, 1, 2, 0, NULL, 0),
(5, '', '123', 'johan', 'johansson', 748640444, 'johan@live.se', NULL, NULL, 0, 0, NULL, 1, 2, 0, NULL, 0),
(6, '', '123', 'niklas', 'niklassson', 748640555, 'niklas@live.se', NULL, NULL, 0, 0, NULL, 1, NULL, 0, NULL, 0),
(7, '', '123', 'ruben', 'rubensson', 748640666, 'ruben@live.se', NULL, NULL, 0, 0, NULL, 1, NULL, 0, NULL, 0),
(8, '', '123', 'rasmus', 'rasmussson', 748640777, 'rasmus@live.se', NULL, NULL, 0, 0, NULL, 1, NULL, 0, NULL, 0),
(9, '', '123', 'simon', 'simonsson', 748640888, 'simon@live.se', NULL, NULL, 0, 0, NULL, 1, NULL, 0, NULL, 0),
(10, '', '123', 'stina', 'stinasson', 748640999, 'stina@live.se', NULL, NULL, 0, 0, NULL, 1, NULL, 0, NULL, 0),
(11, '', '123', 'lina', 'linasson', 748640000, 'lina@live.se', NULL, NULL, 0, 0, NULL, 1, NULL, 0, NULL, 0),
(12, 'asd', '123', 'kent', 'kentsson', 744592111, 'kent@live.se', NULL, NULL, 0, 1, 2, 0, NULL, 0, NULL, 0),
(13, '', '123', 'lena', 'lenasson', 744592222, 'lena@live.se', NULL, NULL, 0, 1, NULL, 0, NULL, 0, NULL, 0),
(14, '', '123', 'elvira', 'elvirasson', 744592333, 'elvira@live.se', NULL, NULL, 0, 1, NULL, 0, NULL, 0, NULL, 0),
(15, '', '123', 'sture', 'sturesson', 744592555, 'sture@live.se', NULL, NULL, 0, 1, NULL, 0, NULL, 0, NULL, 0),
(47, 'KurKur1', '123', 'Kur', 'Kur', 123, '123', NULL, NULL, 1, 0, NULL, 0, NULL, 0, NULL, 0);

-- --------------------------------------------------------

--
-- Tabellstruktur `aplperiod`
--

CREATE TABLE `aplperiod` (
  `PeriodID` int(10) NOT NULL,
  `Arskurs` int(2) DEFAULT NULL,
  `Termin` int(2) DEFAULT NULL,
  `startdag` datetime DEFAULT NULL,
  `slutdag` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumpning av Data i tabell `aplperiod`
--

INSERT INTO `aplperiod` (`PeriodID`, `Arskurs`, `Termin`, `startdag`, `slutdag`) VALUES
(1, 2, 1, '2018-04-16 00:00:00', '2018-05-30 00:00:00'),
(2, 2, 2, NULL, NULL),
(3, 3, 1, NULL, NULL),
(4, 3, 2, NULL, NULL);

-- --------------------------------------------------------

--
-- Tabellstruktur `arbetsplats`
--

CREATE TABLE `arbetsplats` (
  `ArbetsplatsID` int(10) NOT NULL,
  `Namn` varchar(15) DEFAULT NULL,
  `Telefonnummer` int(10) DEFAULT NULL,
  `Mailadress` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumpning av Data i tabell `arbetsplats`
--

INSERT INTO `arbetsplats` (`ArbetsplatsID`, `Namn`, `Telefonnummer`, `Mailadress`) VALUES
(1, 'Blocket', 752574111, 'Blocket@live.se'),
(2, 'Mediamarkt', 752574222, 'Mediamarkt@live.se'),
(3, 'Elgiganten', 752574333, 'Elgiganten@live.se'),
(4, 'tele2', 752574444, 'tele2@live.se'),
(5, 'Phonehouse', 752574555, 'Phonehouse@live.se');

-- --------------------------------------------------------

--
-- Tabellstruktur `dag`
--

CREATE TABLE `dag` (
  `DagarID` int(10) NOT NULL,
  `Datum` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumpning av Data i tabell `dag`
--

INSERT INTO `dag` (`DagarID`, `Datum`) VALUES
(1, '2018-04-16 00:00:00'),
(2, '2018-04-17 00:00:00'),
(3, '2018-04-18 00:00:00'),
(4, '2018-04-19 00:00:00'),
(5, '2018-04-20 00:00:00'),
(6, '2018-04-23 00:00:00'),
(7, '2018-04-24 00:00:00'),
(8, '2018-04-25 00:00:00'),
(9, '2018-04-26 00:00:00'),
(10, '2018-04-27 00:00:00'),
(11, '2018-04-30 00:00:00'),
(12, '2018-05-01 00:00:00'),
(13, '2018-05-02 00:00:00'),
(14, '2018-05-03 00:00:00'),
(15, '2018-05-04 00:00:00'),
(16, '2018-05-07 00:00:00'),
(17, '2018-05-08 00:00:00'),
(18, '2018-05-09 00:00:00'),
(19, '2018-05-10 00:00:00'),
(20, '2018-05-11 00:00:00'),
(21, '2018-05-14 00:00:00'),
(22, '2018-05-15 00:00:00'),
(23, '2018-05-16 00:00:00'),
(24, '2018-05-17 00:00:00'),
(25, '2018-05-18 00:00:00'),
(26, '2018-05-21 00:00:00'),
(27, '2018-05-22 00:00:00'),
(28, '2018-05-23 00:00:00'),
(29, '2018-05-24 00:00:00'),
(30, '2018-05-25 00:00:00'),
(31, '2018-05-28 00:00:00'),
(32, '2018-05-29 00:00:00'),
(33, '2018-05-30 00:00:00');

-- --------------------------------------------------------

--
-- Tabellstruktur `klass`
--

CREATE TABLE `klass` (
  `KlassID` int(10) NOT NULL,
  `Namn` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumpning av Data i tabell `klass`
--

INSERT INTO `klass` (`KlassID`, `Namn`) VALUES
(1, 'El2'),
(2, 'El3'),
(3, 'TE3'),
(4, 'TE4');

-- --------------------------------------------------------

--
-- Tabellstruktur `mappar`
--

CREATE TABLE `mappar` (
  `MappRaknare` int(10) NOT NULL,
  `AnvandarID` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellstruktur `narvaro`
--

CREATE TABLE `narvaro` (
  `NarvaroRaknare` int(10) NOT NULL,
  `Narvarande` tinyint(1) DEFAULT NULL,
  `AnvandarID` int(10) DEFAULT NULL,
  `DagarID` int(10) DEFAULT NULL,
  `PeriodID` int(10) DEFAULT NULL,
  `ArbetsplatsID` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumpning av Data i tabell `narvaro`
--

INSERT INTO `narvaro` (`NarvaroRaknare`, `Narvarande`, `AnvandarID`, `DagarID`, `PeriodID`, `ArbetsplatsID`) VALUES
(1, 1, 4, 1, 1, 2),
(2, 0, 4, 2, 1, 2),
(3, 1, 4, 3, 1, 2),
(4, 1, 4, 4, 1, 2),
(5, 0, 4, 5, 1, 2),
(6, 1, 4, 6, 1, 2),
(7, 0, 4, 7, 1, 2),
(8, 0, 4, 8, 1, 2),
(9, 1, 4, 9, 1, 2),
(10, 0, 4, 10, 1, 2),
(11, 1, 4, 11, 1, 2),
(12, 0, 4, 12, 1, 2),
(13, 1, 4, 13, 1, 2),
(14, 0, 4, 14, 1, 2),
(15, 0, 4, 15, 1, 2),
(16, NULL, 4, 16, 1, 2),
(17, NULL, 4, 17, 1, 2),
(18, NULL, 4, 18, 1, 2),
(19, 1, 4, 19, 1, 2),
(20, 0, 4, 20, 1, 2),
(21, 1, 4, 21, 1, 2),
(22, 1, 4, 22, 1, 2),
(23, 0, 4, 23, 1, 2),
(24, 1, 4, 24, 1, 2),
(25, 0, 4, 25, 1, 2),
(26, NULL, 4, 26, 1, 2),
(27, NULL, 4, 27, 1, 2),
(28, 0, 4, 28, 1, 2),
(29, 1, 4, 29, 1, 2),
(30, 0, 4, 30, 1, 2),
(31, 0, 4, 31, 1, 2),
(32, 1, 4, 32, 1, 2),
(33, 1, 4, 33, 1, 2),
(34, 0, 5, 1, 1, 2),
(35, 1, 5, 2, 1, 2),
(36, 0, 5, 3, 1, 2),
(37, 0, 5, 4, 1, 2),
(38, 0, 5, 5, 1, 2),
(39, 1, 5, 6, 1, 2),
(40, 1, 5, 7, 1, 2),
(41, 0, 5, 8, 1, 2),
(42, 1, 5, 9, 1, 2),
(43, 1, 5, 10, 1, 2),
(44, 1, 5, 11, 1, 2),
(45, 0, 5, 12, 1, 2),
(46, 1, 5, 13, 1, 2),
(47, NULL, 5, 14, 1, 2),
(48, 1, 5, 15, 1, 2),
(49, NULL, 5, 16, 1, 2),
(50, NULL, 5, 17, 1, 2),
(51, NULL, 5, 18, 1, 2),
(52, NULL, 5, 19, 1, 2),
(53, NULL, 5, 20, 1, 2),
(54, NULL, 5, 21, 1, 2),
(55, NULL, 5, 22, 1, 2),
(56, NULL, 5, 23, 1, 2),
(57, 0, 5, 24, 1, 2),
(58, 1, 5, 25, 1, 2),
(59, NULL, 5, 26, 1, 2),
(60, NULL, 5, 27, 1, 2),
(61, NULL, 5, 28, 1, 2),
(62, NULL, 5, 29, 1, 2),
(63, NULL, 5, 30, 1, 2),
(64, 1, 5, 31, 1, 2),
(65, 0, 5, 32, 1, 2),
(66, 1, 5, 33, 1, 2);

--
-- Index för dumpade tabeller
--

--
-- Index för tabell `anvandare`
--
ALTER TABLE `anvandare`
  ADD PRIMARY KEY (`AnvandarID`);

--
-- Index för tabell `aplperiod`
--
ALTER TABLE `aplperiod`
  ADD PRIMARY KEY (`PeriodID`);

--
-- Index för tabell `arbetsplats`
--
ALTER TABLE `arbetsplats`
  ADD PRIMARY KEY (`ArbetsplatsID`);

--
-- Index för tabell `dag`
--
ALTER TABLE `dag`
  ADD PRIMARY KEY (`DagarID`);

--
-- Index för tabell `klass`
--
ALTER TABLE `klass`
  ADD PRIMARY KEY (`KlassID`);

--
-- Index för tabell `mappar`
--
ALTER TABLE `mappar`
  ADD PRIMARY KEY (`MappRaknare`),
  ADD KEY `AnvandarID` (`AnvandarID`);

--
-- Index för tabell `narvaro`
--
ALTER TABLE `narvaro`
  ADD PRIMARY KEY (`NarvaroRaknare`),
  ADD KEY `AnvandarID` (`AnvandarID`),
  ADD KEY `DagarID` (`DagarID`),
  ADD KEY `PeriodID` (`PeriodID`),
  ADD KEY `ArbetsplatsID` (`ArbetsplatsID`);

--
-- AUTO_INCREMENT för dumpade tabeller
--

--
-- AUTO_INCREMENT för tabell `anvandare`
--
ALTER TABLE `anvandare`
  MODIFY `AnvandarID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;
--
-- AUTO_INCREMENT för tabell `aplperiod`
--
ALTER TABLE `aplperiod`
  MODIFY `PeriodID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT för tabell `arbetsplats`
--
ALTER TABLE `arbetsplats`
  MODIFY `ArbetsplatsID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT för tabell `dag`
--
ALTER TABLE `dag`
  MODIFY `DagarID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;
--
-- AUTO_INCREMENT för tabell `klass`
--
ALTER TABLE `klass`
  MODIFY `KlassID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT för tabell `mappar`
--
ALTER TABLE `mappar`
  MODIFY `MappRaknare` int(10) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT för tabell `narvaro`
--
ALTER TABLE `narvaro`
  MODIFY `NarvaroRaknare` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=67;
--
-- Restriktioner för dumpade tabeller
--

--
-- Restriktioner för tabell `mappar`
--
ALTER TABLE `mappar`
  ADD CONSTRAINT `mappar_ibfk_1` FOREIGN KEY (`AnvandarID`) REFERENCES `anvandare` (`AnvandarID`);

--
-- Restriktioner för tabell `narvaro`
--
ALTER TABLE `narvaro`
  ADD CONSTRAINT `narvaro_ibfk_1` FOREIGN KEY (`AnvandarID`) REFERENCES `anvandare` (`AnvandarID`),
  ADD CONSTRAINT `narvaro_ibfk_2` FOREIGN KEY (`DagarID`) REFERENCES `dag` (`DagarID`),
  ADD CONSTRAINT `narvaro_ibfk_3` FOREIGN KEY (`PeriodID`) REFERENCES `aplperiod` (`PeriodID`),
  ADD CONSTRAINT `narvaro_ibfk_4` FOREIGN KEY (`ArbetsplatsID`) REFERENCES `arbetsplats` (`ArbetsplatsID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
