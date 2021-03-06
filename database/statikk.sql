-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Nov 03, 2017 at 06:02 AM
-- Server version: 10.1.13-MariaDB
-- PHP Version: 5.6.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `statikk`
--

-- --------------------------------------------------------

--
-- Table structure for table `champ_ban`
--

CREATE TABLE `champ_ban` (
  `champ_spec_id` bigint(20) NOT NULL,
  `ban_order` int(11) NOT NULL,
  `ban_count` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `champ_final_build`
--

CREATE TABLE `champ_final_build` (
  `champ_spec_id` bigint(20) NOT NULL,
  `final_build_order_id` bigint(20) NOT NULL,
  `played_count` bigint(20) NOT NULL,
  `win_count` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `champ_matchup`
--

CREATE TABLE `champ_matchup` (
  `champ_spec_id_a` bigint(20) NOT NULL,
  `champ_spec_id_b` bigint(20) NOT NULL,
  `win_count` bigint(20) NOT NULL DEFAULT '0',
  `played_count` bigint(20) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `champ_spec`
--

CREATE TABLE `champ_spec` (
  `champ_spec_id` bigint(20) NOT NULL,
  `champion_id` int(11) NOT NULL,
  `match_type` int(11) NOT NULL,
  `lol_version_id` int(11) NOT NULL,
  `lane` int(11) NOT NULL DEFAULT '-1',
  `role` int(11) NOT NULL DEFAULT '-1',
  `rank` int(11) NOT NULL DEFAULT '-1',
  `region` int(11) NOT NULL DEFAULT '-1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `champ_spec_win_rate`
--

CREATE TABLE `champ_spec_win_rate` (
  `champ_spec_id` bigint(20) NOT NULL,
  `win_count` bigint(20) NOT NULL,
  `played_count` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `champ_summoner_spells`
--

CREATE TABLE `champ_summoner_spells` (
  `champ_spec_id` bigint(20) NOT NULL,
  `spell_a` int(11) NOT NULL,
  `spell_b` int(11) NOT NULL,
  `win_count` bigint(20) NOT NULL,
  `played_count` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `champ_teamup`
--

CREATE TABLE `champ_teamup` (
  `champ_spec_id_a` bigint(20) NOT NULL,
  `champ_spec_id_b` bigint(20) NOT NULL,
  `win_count` bigint(20) NOT NULL DEFAULT '0',
  `played_count` bigint(20) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `data_cache_item`
--

CREATE TABLE `data_cache_item` (
  `id` VARCHAR(500) NOT NULL,
  `value` LONGTEXT NULL,
  `stored_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `final_build_order`
--

CREATE TABLE `final_build_order` (
  `final_build_order_id` bigint(20) NOT NULL,
  `build_order` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `lol_match`
--

CREATE TABLE `lol_match` (
  `lol_match_id` bigint(20) NOT NULL,
  `match_id` bigint(20) NOT NULL,
  `region` int(11) NOT NULL,
  `match_type` int(11) NOT NULL,
  `status` tinyint(4) NOT NULL,
  `begin_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `insert_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `processed_time` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `lol_summoner`
--

CREATE TABLE `lol_summoner` (
  `account_id` bigint(20) NOT NULL,
  `summoner_id` bigint(20) NOT NULL,
  `highest_rank` int(11) NOT NULL,
  `region` int(11) NOT NULL,
  `last_mined_date` timestamp NULL DEFAULT NULL,
  `last_played_date` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `lol_version`
--

CREATE TABLE `lol_version` (
  `lol_version_id` int(11) NOT NULL,
  `major_version` int(11) NOT NULL,
  `minor_version` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `team_comp`
--

CREATE TABLE `team_comp` (
  `role` int(11) NOT NULL,
  `lane` int(11) NOT NULL,
  `ally_team_comp` varchar(255) NOT NULL,
  `enemy_team_comp` varchar(255) NOT NULL,
  `match_type` int(11) NOT NULL,
  `lol_version_id` int(11) NOT NULL,
  `region` int(11) NOT NULL,
  `win_count` bigint(20) NOT NULL,
  `played_count` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `champ_ban`
--
ALTER TABLE `champ_ban`
  ADD PRIMARY KEY (`champ_spec_id`,`ban_order`);

--
-- Indexes for table `champ_final_build`
--
ALTER TABLE `champ_final_build`
  ADD PRIMARY KEY (`champ_spec_id`,`final_build_order_id`),
  ADD KEY `champ_spec_idx` (`champ_spec_id`),
  ADD KEY `final_build_idx` (`final_build_order_id`);

--
-- Indexes for table `champ_matchup`
--
ALTER TABLE `champ_matchup`
  ADD PRIMARY KEY (`champ_spec_id_a`,`champ_spec_id_b`),
  ADD KEY `matchup_b_fk_idx` (`champ_spec_id_b`),
  ADD KEY `matchup_a_fk_idx` (`champ_spec_id_a`);

--
-- Indexes for table `champ_spec`
--
ALTER TABLE `champ_spec`
  ADD PRIMARY KEY (`champ_spec_id`),
  ADD UNIQUE KEY `unique_champ_spec` (`lol_version_id`,`match_type`,`champion_id`,`lane`,`role`,`rank`, `region`);

--
-- Indexes for table `champ_spec_win_rate`
--
ALTER TABLE `champ_spec_win_rate`
  ADD PRIMARY KEY (`champ_spec_id`);

--
-- Indexes for table `champ_summoner_spells`
--
ALTER TABLE `champ_summoner_spells`
  ADD PRIMARY KEY (`champ_spec_id`,`spell_a`,`spell_b`),
  ADD KEY `champ_spec_id_fk_idx` (`champ_spec_id`);

--
-- Indexes for table `champ_teamup`
--
ALTER TABLE `champ_teamup`
  ADD PRIMARY KEY (`champ_spec_id_a`,`champ_spec_id_b`),
  ADD KEY `teamup_b_fk` (`champ_spec_id_b`),
  ADD KEY `teamup_a_fk` (`champ_spec_id_a`);

--
-- Indexes for table `data_cache_item`
--
ALTER TABLE `data_cache_item`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `final_build_order`
--
ALTER TABLE `final_build_order`
  ADD PRIMARY KEY (`final_build_order_id`),
  ADD UNIQUE KEY `build_order_UNIQUE` (`build_order`);

--
-- Indexes for table `lol_match`
--
ALTER TABLE `lol_match`
  ADD PRIMARY KEY (`lol_match_id`),
  ADD UNIQUE KEY `lol_match_unique` (`match_id`, `region`),
  ADD KEY `status` (`status`);

--
-- Indexes for table `lol_summoner`
--
ALTER TABLE `lol_summoner`
  ADD PRIMARY KEY (`account_id`),
  ADD KEY `last_mined_date` (`last_mined_date`),
  ADD KEY `last_played_date` (`last_played_date`);
--
-- Indexes for table `lol_version`
--
ALTER TABLE `lol_version`
  ADD PRIMARY KEY (`lol_version_id`),
  ADD UNIQUE KEY `unique_version` (`major_version`,`minor_version`);

  
--
-- Indexes for table `team_comp`
--
ALTER TABLE `team_comp`
  ADD PRIMARY KEY (`role`, `lane`, `ally_team_comp`,`enemy_team_comp`,`match_type`,`lol_version_id`,`region`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `champ_spec`
--
ALTER TABLE `champ_spec`
  MODIFY `champ_spec_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7623;
--
-- AUTO_INCREMENT for table `final_build_order`
--
ALTER TABLE `final_build_order`
  MODIFY `final_build_order_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14479;
--
-- AUTO_INCREMENT for table `lol_version`
--
ALTER TABLE `lol_version`
  MODIFY `lol_version_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT for table `lol_match`
--
ALTER TABLE `lol_match`
  MODIFY `lol_match_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `champ_ban`
--
ALTER TABLE `champ_ban`
  ADD CONSTRAINT `champ_ban_champ_spec_fk` FOREIGN KEY (`champ_spec_id`) REFERENCES `champ_spec` (`champ_spec_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `champ_final_build`
--
ALTER TABLE `champ_final_build`
  ADD CONSTRAINT `champ_final_build_champ_spec_fk` FOREIGN KEY (`champ_spec_id`) REFERENCES `champ_spec` (`champ_spec_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `champ_final_build_final_build_fk` FOREIGN KEY (`final_build_order_id`) REFERENCES `final_build_order` (`final_build_order_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `champ_matchup`
--
ALTER TABLE `champ_matchup`
  ADD CONSTRAINT `matchup_a_fk` FOREIGN KEY (`champ_spec_id_a`) REFERENCES `champ_spec` (`champ_spec_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `matchup_b_fk` FOREIGN KEY (`champ_spec_id_b`) REFERENCES `champ_spec` (`champ_spec_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `champ_spec`
--
ALTER TABLE `champ_spec`
  ADD CONSTRAINT `champ_spec_version_fk` FOREIGN KEY (`lol_version_id`) REFERENCES `lol_version` (`lol_version_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `champ_spec_win_rate`
--
ALTER TABLE `champ_spec_win_rate`
  ADD CONSTRAINT `champ_spec_id_fk` FOREIGN KEY (`champ_spec_id`) REFERENCES `champ_spec` (`champ_spec_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `champ_summoner_spells`
--
ALTER TABLE `champ_summoner_spells`
  ADD CONSTRAINT `champ_summoner_spell_champ_spec_fk` FOREIGN KEY (`champ_spec_id`) REFERENCES `champ_spec` (`champ_spec_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `champ_teamup`
--
ALTER TABLE `champ_teamup`
  ADD CONSTRAINT `teamup_a_fk` FOREIGN KEY (`champ_spec_id_a`) REFERENCES `champ_spec` (`champ_spec_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `teamup_b_fk` FOREIGN KEY (`champ_spec_id_b`) REFERENCES `champ_spec` (`champ_spec_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `team_comp`
--
ALTER TABLE `team_comp`
  ADD CONSTRAINT `team_comp_version_fk` FOREIGN KEY (`lol_version_id`) REFERENCES `lol_version` (`lol_version_id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
