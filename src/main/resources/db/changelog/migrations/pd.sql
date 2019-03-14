-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Oct 04, 2018 at 10:43 AM
-- Server version: 5.6.38
-- PHP Version: 7.2.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `pd_db`
--


-- --------------------------------------------------------

--
-- Table structure for table `note`
--

CREATE TABLE `note` (
  `note_id` int(11) NOT NULL,
  `test_session_id` int(11) NOT NULL,
  `note` longtext NOT NULL,
  `med_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `note`
--

INSERT INTO `note` (`note_id`, `test_session_id`, `note`, `med_id`) VALUES
  (1, 1, 'Well this is interesting.', 2),
  (2, 1, 'This seams a bit weird.', 1);

-- --------------------------------------------------------

--
-- Table structure for table `organization`
--

CREATE TABLE `organization` (
  `organization_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `organization`
--

INSERT INTO `organization` (`organization_id`, `name`) VALUES
  (1, 'Hospital'),
  (2, 'LNU University');

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `role_id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`role_id`, `name`, `type`) VALUES
  (1, 'patient', '1'),
  (2, 'physician', '2');

-- --------------------------------------------------------

--
-- Table structure for table `test_session`
--

CREATE TABLE `test_session` (
  `test_session_id` int(11) NOT NULL,
  `test_type` int(11) NOT NULL,
  `therapy_id` int(11) NOT NULL,
  `start_time` datetime,
  `end_time` datetime,
  `requested_hours` int(11)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `test_session`
--

INSERT INTO `test_session` (`test_session_id`, `test_type`, `therapy_id`, `requested_hours`, `start_time`, `end_time`) VALUES
  (1, 1, 1, 2, '2019-04-11 18:00:00', '2019-04-11 20:00:00'),
  (2, 2, 1, 1, '2019-01-11 18:00:00', '2019-01-11 21:00:00'),
  (3, 1, 1, 3, '2019-02-01 18:00:00', '2019-02-01 21:00:00'),
  (4, 2, 1, 2, '2018-12-01 18:00:00', '2018-12-01 21:00:00'),
  (5, 1, 2, 3, '2018-11-01 18:00:00', '2018-11-01 21:00:00'),
  (6, 2, 2, 1, NULL, NULL);


-- --------------------------------------------------------

--
-- Table structure for table `therapy`
--

CREATE TABLE `therapy` (
  `therapy_id` int(11) NOT NULL,
  `patient_id` int(11) NOT NULL,
  `med_id` int(11) NOT NULL,
  `organization_id` int(11) NOT NULL,
  `start_time` datetime,
  `end_time` datetime
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `therapy`
--

INSERT INTO `therapy` (`therapy_id`, `patient_id`, `organization_id`, `med_id`,  `start_time`) VALUES
  (1, 3, 1, 2, '2018-12-01 18:00:00'),
  (2, 4, 1, 1, '2018-12-01 18:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `User`
--

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `username` varchar(45) NOT NULL,
  `email` varchar(255) NOT NULL,
  `role_id` int(11) DEFAULT NULL,
  `lat` float DEFAULT NULL,
  `long` float DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `password_confirm` varchar(255) DEFAULT NULL,
  `provider` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `working_hours_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `User`
--

INSERT INTO `user` (`user_id`, `username`, `email`, `role_id`, `lat`, `long`) VALUES
  (1, 'doc', 'doc@hospital.com', 2, 55.6567, 13.7109),
  (2, 'researcher', 'res@uni.se', 2, 49.6567, 10.5912),
  (3, 'patient1', 'x@gmail.com', 1, 59.6567, 16.6709),
  (4, 'patient2', 'y@happyemail.com', 1, 57.3365, 12.5164),
  (5, 'doc1', 'doc1@hospital.com', 2, 25.6722, 7.1509),
  (6, 'doc2', 'doc2@hospital.com', 2, 35.3367, 9.1209);




-- --------------------------------------------------------

--
-- Table structure for table `User_Organization`
--

CREATE TABLE `user_organization` (
  `user_id` int(11) NOT NULL,
  `organization_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Dumping data for table `user_organization`
--

INSERT INTO `user_organization` (`user_id`, `organization_id`) VALUES
  (1, 1),
  (1, 2),
  (2, 2),
  (3, 1),
  (4, 1),
  (5, 2),
  (6, 1),
  (6, 2);



-- --------------------------------------------------------

--
-- Table structure for table `working_hours`
--

CREATE TABLE `working_hours` (
  `working_hours_id` int(11) NOT NULL,
  `from` TIME DEFAULT NULL ,
  `to` TIME DEFAULT NULL ,
  `weekly_days_off` varchar(22) DEFAULT  NULL,
  `other_days_off` DATE  DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



--
-- Indexes for dumped tables
--


--
-- Indexes for table `note`
--
ALTER TABLE `note`
  ADD PRIMARY KEY (`note_id`),
  ADD KEY `fk_Test_SessionID_idx` (`test_session_id`),
  ADD KEY `fk_UserID_idx` (`med_id`);

--
-- Indexes for table `organization`
--
ALTER TABLE `organization`
  ADD PRIMARY KEY (`organization_id`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`role_id`);


--
-- Indexes for table `test_session`
--
ALTER TABLE `test_session`
  ADD PRIMARY KEY (`test_session_id`),
  ADD KEY `fk_TherapyID_idx` (`therapy_id`);

--
-- Indexes for table `therapy`
--
ALTER TABLE `therapy`
  ADD PRIMARY KEY (`therapy_id`),
  ADD KEY `fk_UserID_Patient_idx` (`patient_id`),
  ADD KEY `fk_UserID_medic_idx` (`med_id`),
  ADD KEY `fk_UserID_organization_idx` (`organization_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `username_UNIQUE` (`username`),
  ADD KEY `roleID_idx` (`role_id`),
   ADD KEY `workingHoursId_idx` (`working_hours_id`);


--
-- Indexes for table `working_hours`
--
ALTER TABLE `working_hours`
  ADD PRIMARY KEY (`working_hours_id`);


--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `note`
--
ALTER TABLE `note`
  MODIFY `note_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `organization`
--
ALTER TABLE `organization`
  MODIFY `organization_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `role_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `test_session`
--
ALTER TABLE `test_session`
  MODIFY `test_session_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `therapy`
--
ALTER TABLE `therapy`
  MODIFY `therapy_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;


--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `user_organization`
--
ALTER TABLE `working_hours`
  MODIFY `working_hours_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `note`
--
ALTER TABLE `note`
  ADD CONSTRAINT `fk_Test_SessionID` FOREIGN KEY (`test_session_id`) REFERENCES `test_session` (`test_session_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_UserID` FOREIGN KEY (`med_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `test_session`
--
ALTER TABLE `test_session`
  ADD CONSTRAINT `fk_TherapyID` FOREIGN KEY (`therapy_id`) REFERENCES `therapy` (`therapy_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `therapy`
--
ALTER TABLE `therapy`
  ADD CONSTRAINT `fk_UserID_Patient` FOREIGN KEY (`patient_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_UserID_medic` FOREIGN KEY (`med_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_UserID_org` FOREIGN KEY (`organization_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;



--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `fk_User_Role` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_User_Working_Hours` FOREIGN KEY (`working_hours_id`) REFERENCES `working_hours` (`working_hours_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `user_organization`
--
ALTER TABLE `user_organization`
  ADD CONSTRAINT `fk_User_Organization_UserID` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_User_Organization_OrganizationId` FOREIGN KEY (`organization_id`) REFERENCES `organization` (`organization_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;