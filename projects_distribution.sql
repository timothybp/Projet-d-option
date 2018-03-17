-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 17, 2018 at 03:24 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `projects_distribution`
--

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

CREATE TABLE IF NOT EXISTS `course` (
  `idCourse` varchar(255) NOT NULL,
  `beginDate` datetime DEFAULT NULL,
  `choosingDeadline` datetime DEFAULT NULL,
  `department` varchar(255) DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `hours` int(11) NOT NULL,
  `membreAmount` int(11) NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `schoolYear` varchar(255) DEFAULT NULL,
  `semester` int(11) NOT NULL,
  `weights` float NOT NULL,
  `idResponsable` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`idCourse`),
  KEY `FKgsrma6012yudbk60rq2vx8jd6` (`idResponsable`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `course`
--

INSERT INTO `course` (`idCourse`, `beginDate`, `choosingDeadline`, `department`, `endDate`, `hours`, `membreAmount`, `nom`, `schoolYear`, `semester`, `weights`, `idResponsable`) VALUES
('201617DI4S7P1', '2016-09-19 00:00:00', '2016-09-16 23:59:59', 'DI', '2017-01-20 23:59:59', 64, 2, 'Projet de programmation et génie logiciel', '2016-2017', 7, 4, 21304592),
('201617DI4S8P1', '2017-01-23 00:00:00', '2017-01-20 23:59:59', 'DI', '2017-06-02 23:59:59', 64, 6, 'Projet collectif', '2016-2017', 8, 4, 21505961),
('201718DI5S10P1', '2018-01-22 00:00:00', '2018-01-19 23:59:59', 'DI', '2018-04-06 23:59:59', 32, 2, 'Projet d''option', '2017-2018', 10, 5, 21505961),
('201718DI5S9P1', '2017-09-25 00:00:00', '2017-09-22 23:59:59', 'DI', '2018-01-19 23:59:59', 64, 6, 'Projet SI', '2017-2018', 9, 4, 21505961),
('201718DI5S9P2', '2017-09-25 00:00:00', '2017-09-22 23:59:59', 'DI', '2018-01-19 23:59:59', 64, 2, 'Projet ASR', '2017-2018', 9, 4, 21505961),
('201718DI5S9P3', '2018-01-07 00:00:00', '2017-12-22 23:59:59', 'DAE', '2018-04-06 00:00:00', 32, 4, 'Projet P&D', '2017-2018', 10, 5, 21304592);

-- --------------------------------------------------------

--
-- Table structure for table `project`
--

CREATE TABLE IF NOT EXISTS `project` (
  `idProject` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `enterprise` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `idCourse` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idProject`),
  KEY `FKarqm3nuqwnq0ikyv7sjhvlboy` (`idCourse`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `project`
--

INSERT INTO `project` (`idProject`, `description`, `enterprise`, `subject`, `idCourse`) VALUES
(1, 'description1', 'AA', '12345678', '201718DI5S10P1'),
(2, 'description2', 'BB', 'qwertyui', '201718DI5S10P1'),
(3, 'description3', 'CC', 'TF678GHN', '201718DI5S10P1'),
(4, 'description4', 'DD', 'bn73gvml', '201718DI5S10P1'),
(5, 'description5', 'CVB', 'hjiy7856', '201718DI5S9P1'),
(6, 'description6', 'BNM', 'bnk8901w', '201718DI5S9P1');

-- --------------------------------------------------------

--
-- Table structure for table `project_student`
--

CREATE TABLE IF NOT EXISTS `project_student` (
  `idProject` int(11) NOT NULL,
  `idStudent` bigint(20) NOT NULL,
  KEY `FKixqroawh03799mv3xk6sqwuqb` (`idStudent`),
  KEY `FKjxrlx78gjnsbrf9p0q42vapet` (`idProject`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `project_student`
--

INSERT INTO `project_student` (`idProject`, `idStudent`) VALUES
(2, 21356948),
(1, 21406729),
(4, 21204964),
(3, 21506022);

-- --------------------------------------------------------

--
-- Table structure for table `project_supervisor`
--

CREATE TABLE IF NOT EXISTS `project_supervisor` (
  `idProject` int(11) NOT NULL,
  `idTeacher` bigint(20) NOT NULL,
  KEY `FKo7jhn7qon4c9fmh7b9yi3emeg` (`idTeacher`),
  KEY `FKlt4xpd4n0qmkc4hgnddyc0axp` (`idProject`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `project_supervisor`
--

INSERT INTO `project_supervisor` (`idProject`, `idTeacher`) VALUES
(6, 21505961),
(6, 21304592),
(2, 21304592),
(2, 21505961),
(1, 21505961),
(4, 21304592),
(3, 21505961);

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE IF NOT EXISTS `student` (
  `idStudent` bigint(20) NOT NULL,
  `birthday` datetime DEFAULT NULL,
  `department` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `grade` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `photoPath` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idStudent`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`idStudent`, `birthday`, `department`, `email`, `grade`, `name`, `password`, `photoPath`, `sex`, `surname`) VALUES
(21204964, '1989-06-18 00:00:00', 'DEE', 'antoine.wang@etu.univ-tours.fr', 4, 'Antoine', '19890618', NULL, 'Homme', 'WANG'),
(21356948, '1995-05-02 00:00:00', 'DI', 'pierre.zhao@etu.univ-tours.fr', 5, 'Pierre', '19950502', NULL, 'Homme', 'ZHAO'),
(21406729, '1990-12-23 00:00:00', 'DMS', 'marie.li@etu.univ-tours.fr', 4, 'Marie', '19901223', NULL, 'Femme', 'LI'),
(21506022, '2018-03-15 19:00:03', 'DI', 'peng.bi@etu.univ-tours.fr', 5, 'Peng', '19931002', 'images/photos/student/peng-bi.jpg', 'Homme', 'BI');

-- --------------------------------------------------------

--
-- Table structure for table `teacher`
--

CREATE TABLE IF NOT EXISTS `teacher` (
  `idTeacher` bigint(20) NOT NULL,
  `birthday` datetime DEFAULT NULL,
  `department` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `photoPath` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idTeacher`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `teacher`
--

INSERT INTO `teacher` (`idTeacher`, `birthday`, `department`, `email`, `name`, `password`, `photoPath`, `role`, `sex`, `surname`) VALUES
(21304592, '1990-03-14 00:00:00', 'DI', 'nicolas.wang@univ-tours.fr', 'Nicolas', '19900314', 'images/photos/teacher/nicolas-guillaume.jpg', 'admin', 'Homme', 'GUILLAUME'),
(21505961, '1992-11-28 00:00:00', 'DI', 'kai.zeng@univ-tours.fr', 'Kai', '19921128', 'images/photos/teacher/kai-zeng.jpg', '', 'Femme', 'ZENG');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `course`
--
ALTER TABLE `course`
  ADD CONSTRAINT `FKgsrma6012yudbk60rq2vx8jd6` FOREIGN KEY (`idResponsable`) REFERENCES `teacher` (`idTeacher`);

--
-- Constraints for table `project`
--
ALTER TABLE `project`
  ADD CONSTRAINT `FKarqm3nuqwnq0ikyv7sjhvlboy` FOREIGN KEY (`idCourse`) REFERENCES `course` (`idCourse`);

--
-- Constraints for table `project_student`
--
ALTER TABLE `project_student`
  ADD CONSTRAINT `FKjxrlx78gjnsbrf9p0q42vapet` FOREIGN KEY (`idProject`) REFERENCES `project` (`idProject`),
  ADD CONSTRAINT `FKixqroawh03799mv3xk6sqwuqb` FOREIGN KEY (`idStudent`) REFERENCES `student` (`idStudent`);

--
-- Constraints for table `project_supervisor`
--
ALTER TABLE `project_supervisor`
  ADD CONSTRAINT `FKlt4xpd4n0qmkc4hgnddyc0axp` FOREIGN KEY (`idProject`) REFERENCES `project` (`idProject`),
  ADD CONSTRAINT `FKo7jhn7qon4c9fmh7b9yi3emeg` FOREIGN KEY (`idTeacher`) REFERENCES `teacher` (`idTeacher`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
