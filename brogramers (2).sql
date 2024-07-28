-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 13, 2024 at 01:30 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `brogramers`
--

-- --------------------------------------------------------

--
-- Table structure for table `achat`
--

CREATE TABLE `achat` (
  `idc` int(11) NOT NULL,
  `id_produit` int(11) NOT NULL,
  `qte` int(11) NOT NULL,
  `mont_tot` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `bibliotheque`
--

CREATE TABLE `bibliotheque` (
  `idBib` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `mail` varchar(255) NOT NULL,
  `nbrLivre` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bibliotheque`
--

INSERT INTO `bibliotheque` (`idBib`, `nom`, `mail`, `nbrLivre`) VALUES
(16, 'zaea', 'ezae', 12),
(18, 'dsdvs', 'bfv', 100);

-- --------------------------------------------------------

--
-- Table structure for table `commande`
--

CREATE TABLE `commande` (
  `idc` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `date` date NOT NULL,
  `adresse` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `commande`
--

INSERT INTO `commande` (`idc`, `id_user`, `date`, `adresse`) VALUES
(3, 4, '2024-03-08', 'soussa');

-- --------------------------------------------------------

--
-- Table structure for table `cours`
--

CREATE TABLE `cours` (
  `idCours` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `ID_Module` int(11) NOT NULL,
  `fichierPath` varchar(255) NOT NULL,
  `tempsC` int(11) NOT NULL,
  `audioPath` varchar(255) NOT NULL,
  `videoPath` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `cours`
--

INSERT INTO `cours` (`idCours`, `nom`, `description`, `ID_Module`, `fichierPath`, `tempsC`, `audioPath`, `videoPath`) VALUES
(83, 'a', 'a', 57, 'a', 2, '', ''),
(84, 'JAVA', 'AAAAAASASAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA', 56, 'C:/xampp/htdocs/files/correction exerice .pdf', 3, 'C:/xampp/htdocs/files/correctionexerice', 'C:/xampp/htdocs/files/Javain100Seconds.mp4'),
(85, 'test', 'ihzihz', 56, 'C:/xampp/htdocs/files/Tests d\'hypothèses_version corrigée.pdf', 20, 'C:/xampp/htdocs/files/Testsd\'hypothèses_versioncorrigée', 'C:/xampp/htdocs/files/MAH03562.MP4'),
(86, 'zefiuzefzeh', 'fguizefuzifzge', 55, 'C:/xampp/htdocs/files/Test d\'hypothèses de la proportion.pdf', 20, 'C:/xampp/htdocs/files/Testd\'hypothèsesdelaproportion', 'C:/xampp/htdocs/files/MAH03569.MP4'),
(87, 'klljklkj', 'kljhoijkl', 56, 'C:/xampp/htdocs/files/correction exercice 4.pdf', 26, 'C:/xampp/htdocs/files/correctionexercice4', 'C:/xampp/htdocs/files/MAH03560.MP4'),
(88, 'zdazz', 'zefzef', 56, 'C:/xampp/htdocs/files/DS estimation version finale2019.pdf', 20, 'C:/xampp/htdocs/files/DSestimationversionfinale2019', 'C:/xampp/htdocs/files/MAH03575.MP4'),
(89, 'zaedza', 'eazeza', 55, 'C:/xampp/htdocs/files/Correction DS 19-20.pdf', 20, 'C:/xampp/htdocs/files/CorrectionDS19-20', 'C:/xampp/htdocs/files/MAH03595.MP4');

-- --------------------------------------------------------

--
-- Table structure for table `dossier`
--

CREATE TABLE `dossier` (
  `idOffre` int(11) NOT NULL,
  `idUser` int(11) NOT NULL,
  `status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `dossier`
--

INSERT INTO `dossier` (`idOffre`, `idUser`, `status`) VALUES
(7, 4, 'Refusé'),
(8, 4, 'Accepté');

-- --------------------------------------------------------

--
-- Table structure for table `exams`
--

CREATE TABLE `exams` (
  `idExam` int(11) NOT NULL,
  `module` varchar(255) NOT NULL,
  `date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `exams`
--

INSERT INTO `exams` (`idExam`, `module`, `date`) VALUES
(2, 'SSS', '2024-03-07'),
(8, 'Adada', '2024-03-01'),
(10, 'SSS', '2024-03-13'),
(11, 'Adada', '2024-03-31'),
(12, 'Adada', '2024-03-17'),
(13, 'Adada', '2024-03-08'),
(14, 'Adada', '2024-03-31'),
(15, 'Adada', '2024-03-19'),
(16, 'Adada', '2024-03-29'),
(17, 'Adada', '2024-03-31');

-- --------------------------------------------------------

--
-- Table structure for table `googleoauth`
--

CREATE TABLE `googleoauth` (
  `id` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `verified_email` tinyint(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `given_name` varchar(255) NOT NULL,
  `familly_name` varchar(255) NOT NULL,
  `picture` varchar(255) NOT NULL,
  `locale` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `googleoauth`
--

INSERT INTO `googleoauth` (`id`, `email`, `verified_email`, `name`, `given_name`, `familly_name`, `picture`, `locale`) VALUES
('108703346998401561498', 'amin.warghi@gmail.com', 1, 'Mohamed Amine Ouerghi', 'Mohamed Amine', 'Ouerghi', 'https://lh3.googleusercontent.com/a/ACg8ocKLFvmudTGpsnnlivajjpVXlpLinRdScHXKJKZTvMsx3Ec=s96-c', 'fr');

-- --------------------------------------------------------

--
-- Table structure for table `livre`
--

CREATE TABLE `livre` (
  `idLivre` int(11) NOT NULL,
  `type` varchar(255) NOT NULL,
  `titre` varchar(255) NOT NULL,
  `auteur` varchar(255) NOT NULL,
  `langue` varchar(255) NOT NULL,
  `idBib` int(11) NOT NULL,
  `image` varchar(255) NOT NULL,
  `pathPDF` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `logs`
--

CREATE TABLE `logs` (
  `idLog` int(11) NOT NULL,
  `idUser` int(11) NOT NULL,
  `description1` varchar(255) NOT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `action1` varchar(255) NOT NULL,
  `action2` varchar(255) NOT NULL,
  `description2` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `logs`
--

INSERT INTO `logs` (`idLog`, `idUser`, `description1`, `date`, `action1`, `action2`, `description2`) VALUES
(1, 1, 'a ajouté', '2024-02-29 23:00:00', 'Wael Sboui', 'Coach', 'en tand que nouvel'),
(2, 1, 'a ajouté', '2024-02-29 23:00:00', 'Wael Sboui', 'Coach', 'en tand que nouvel'),
(3, 1, 'a ajouté', '2024-02-29 23:00:00', 'LiwaEddine Ayari', 'Coach', 'en tand que nouvel'),
(4, 1, 'a ajouté', '2024-02-29 23:00:00', 'Hamadi Abid', 'Coach', 'en tand que nouvel'),
(5, 1, 'a ajouté', '2024-02-29 23:00:00', 'Mohamed Ali Ben Romdhann', 'Coach', 'en tand que nouvel'),
(6, 1, 'a ajouté', '2024-02-29 23:00:00', 'Goujaa Salah', 'Coach', 'en tand que nouvel'),
(18, 1, 'a ajouté', '2024-03-03 23:00:00', 'Abid Mahmoud', 'Coach', 'en tand que nouvel'),
(20, 11, 'a ajouté Examen ', '2024-03-06 23:00:00', 'Adada', 'Exam', 'dans la liste '),
(21, 11, 'a modifié Examen ', '2024-03-06 23:00:00', 'Adada', 'Exam', 'dans la liste '),
(22, 11, 'a ajouté Examen ', '2024-03-06 23:00:00', 'Adada', 'Exam', 'dans la liste '),
(23, 11, 'a ajouté Examen ', '2024-03-06 23:00:00', 'Adada', 'Exam', 'dans la liste '),
(24, 11, 'a ajouté Examen ', '2024-03-06 23:00:00', 'Adada', 'Exam', 'dans la liste '),
(25, 11, 'a ajouté Examen ', '2024-03-06 23:00:00', 'Adada', 'Exam', 'dans la liste '),
(26, 11, 'a ajouté Examen ', '2024-03-06 23:00:00', 'Adada', 'Exam', 'dans la liste '),
(27, 11, 'a ajouté Examen ', '2024-03-06 23:00:00', 'Adada', 'Exam', 'dans la liste '),
(28, 11, 'a ajouté Examen ', '2024-03-06 23:00:00', 'Adada', 'Exam', 'dans la liste ');

-- --------------------------------------------------------

--
-- Table structure for table `modules`
--

CREATE TABLE `modules` (
  `idModule` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `imgPath` varchar(255) NOT NULL,
  `tempsT` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `modules`
--

INSERT INTO `modules` (`idModule`, `nom`, `description`, `imgPath`, `tempsT`) VALUES
(55, 'AZDSADA', 'SDQSDSQdd', 'CCCC', 40),
(56, 'Adada', 'dadaddadadadaddadadaada', 'C:/xampp/htdocs/img/5938045.jpg', 69),
(57, 'SSS', 'suiiii', 'C:/xampp/htdocs/img/5938045.jpg', 3);

-- --------------------------------------------------------

--
-- Table structure for table `notes`
--

CREATE TABLE `notes` (
  `idUser` int(11) NOT NULL,
  `idexam` int(11) NOT NULL,
  `note` int(11) NOT NULL,
  `module` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `offre`
--

CREATE TABLE `offre` (
  `idOffre` int(11) NOT NULL,
  `description` varchar(100) NOT NULL,
  `niveau` int(11) NOT NULL,
  `dateDebut` date NOT NULL,
  `dateFin` date NOT NULL,
  `idSociete` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `offre`
--

INSERT INTO `offre` (`idOffre`, `description`, `niveau`, `dateDebut`, `dateFin`, `idSociete`) VALUES
(7, 'SDSQDQ', 50, '2024-03-14', '2024-03-29', 6),
(8, 'AAAAAAAAA', 2, '2024-02-26', '2024-03-05', 4),
(9, 'SQDSQD', 50, '2019-01-01', '2027-01-01', 1);

-- --------------------------------------------------------

--
-- Table structure for table `produit`
--

CREATE TABLE `produit` (
  `id_produit` int(11) NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `prix_init` float DEFAULT NULL,
  `marge` float DEFAULT NULL,
  `video` varchar(255) DEFAULT NULL,
  `audio` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `descrip` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `profile`
--

CREATE TABLE `profile` (
  `idProfile` int(11) NOT NULL,
  `idUser` int(11) DEFAULT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `phoneNumber` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `profile`
--

INSERT INTO `profile` (`idProfile`, `idUser`, `firstName`, `lastName`, `picture`, `address`, `location`, `phoneNumber`) VALUES
(1, 1, 'Mahmoud', 'Abid', 'http://127.0.0.1/img/Wicked.png', 'Tnuis', 'Ariana', NULL),
(2, 3, 'KKK', 'Sboui', 'http://127.0.0.1/img/380009749_854927022567056_2584231981793520668_n.jpg', 'Tunis', 'Tunis', 12345678),
(3, 4, 'Wicked', '', 'file:C:/Users/Wicked/Desktop/JavaFxProject/JavaFxProjectUserTask/src/main/resources/Images/adminIconDefault.png', '', '', 25361478),
(4, 5, 'LiwaEddine', 'Ayari', 'file:C:/Users/Wicked/Desktop/JavaFxProject/JavaFxProjectUserTask/src/main/resources/Images/adminIconDefault.png', 'Morneguiya', 'Esprit', 93625147),
(5, 6, 'Hamadi', 'Abid', 'file:C:/Users/Wicked/Desktop/JavaFxProject/JavaFxProjectUserTask/src/main/resources/Images/adminIconDefault.png', 'Tounis', 'Tounis', 65893214),
(6, 7, 'Mohamed Ali', 'Ben Romdhann', 'file:C:/Users/Wicked/Desktop/JavaFxProject/JavaFxProjectUserTask/src/main/resources/Images/adminIconDefault.png', 'Tunis', 'Tunis', 25361478),
(10, 11, 'Abid', 'Mahmoud', 'file:C:/Users/Wicked/Desktop/JavaFxProject/JavaFxProjectUserTask/src/main/resources/Images/adminIconDefault.png', 'Tinos', 'Tnis', 26381661),
(17, 18, 'Mahmoud', 'Abid', 'https://lh3.googleusercontent.com/a/ACg8ocLsmoV5w7o7M1oqUfmJwkK7nLqau73XZgBNY4J66XOdLg=s96-c', 'TUNIS', 'fr', 369258),
(18, 19, 'Mohamed Amine', 'Ouerghi', 'https://lh3.googleusercontent.com/a/ACg8ocKLFvmudTGpsnnlivajjpVXlpLinRdScHXKJKZTvMsx3Ec=s96-c', '', 'fr', 0);

-- --------------------------------------------------------

--
-- Table structure for table `profileadmin`
--

CREATE TABLE `profileadmin` (
  `idProfile` int(11) NOT NULL,
  `reponsabilty` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `profileadmin`
--

INSERT INTO `profileadmin` (`idProfile`, `reponsabilty`, `title`) VALUES
(1, 'Gestion Utilisateur', 'FullStackDeveloper');

-- --------------------------------------------------------

--
-- Table structure for table `profilecoach`
--

CREATE TABLE `profilecoach` (
  `idProfile` int(11) NOT NULL,
  `idModule` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `profilecoach`
--

INSERT INTO `profilecoach` (`idProfile`, `idModule`) VALUES
(2, 55),
(5, 55),
(6, 55),
(4, 56),
(7, 56),
(10, 56);

-- --------------------------------------------------------

--
-- Table structure for table `questions`
--

CREATE TABLE `questions` (
  `idquestion` int(11) NOT NULL,
  `question` varchar(255) NOT NULL,
  `choix1` varchar(255) NOT NULL,
  `choix2` varchar(255) NOT NULL,
  `choix3` varchar(255) NOT NULL,
  `choixcorrect` varchar(255) NOT NULL,
  `idExam` int(11) NOT NULL,
  `duree` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `questions`
--

INSERT INTO `questions` (`idquestion`, `question`, `choix1`, `choix2`, `choix3`, `choixcorrect`, `idExam`, `duree`) VALUES
(3, 'FFF', 'FFFF', 'FFFF', 'SSS', 'SSS', 2, 15),
(4, 'AAA', 'DD', 'CC', 'XX', 'XX', 2, 20),
(5, 'FQSFSQF', 'FF', 'SS', 'CC', 'CC', 2, 50),
(6, 'ezae', 'ezae', 'ezae', 'eazez', 'ezae', 2, 20),
(7, 'ezae', 'ezae', 'ezae', 'eazez', 'ezae', 2, 20),
(8, 'ezae', 'ezae', 'ezae', 'eazez', 'ezae', 2, 20),
(9, 'ezae', 'ezae', 'ezae', 'eazez', 'ezae', 2, 20),
(10, 'ezae', 'ezae', 'ezae', 'eazez', 'ezae', 2, 20),
(11, 'ezae', 'ezae', 'ezae', 'eazez', 'ezae', 2, 20),
(12, 'ezae', 'ezae', 'ezae', 'eazez', 'ezae', 2, 20),
(13, 'ezae', 'ezae', 'ezae', 'eazez', 'ezae', 2, 20),
(14, 'ezae', 'ezae', 'ezae', 'eazez', 'ezae', 2, 20),
(15, 'ezae', 'ezae', 'ezae', 'eazez', 'ezae', 2, 20),
(16, 'ezae', 'ezae', 'ezae', 'eazez', 'ezae', 2, 20),
(17, 'ezae', 'ezae', 'ezae', 'eazez', 'ezae', 2, 20),
(18, 'ezae', 'ezae', 'ezae', 'eazez', 'ezae', 2, 20),
(19, 'Tryy', 'try', 'tryyy', 'tryyyyy', 'tryyy', 8, 20);

-- --------------------------------------------------------

--
-- Table structure for table `societe`
--

CREATE TABLE `societe` (
  `idSociete` int(11) NOT NULL,
  `nomSociete` varchar(30) NOT NULL,
  `description` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `societe`
--

INSERT INTO `societe` (`idSociete`, `nomSociete`, `description`) VALUES
(1, 'fzefez', 'fzefefezf'),
(3, 'SDQ', 'FFFFF'),
(4, 'eza', 'ezae'),
(6, 'rgr', 'gregree'),
(7, 'test', 'sssqq');

-- --------------------------------------------------------

--
-- Table structure for table `suivi`
--

CREATE TABLE `suivi` (
  `idSuivi` int(11) NOT NULL,
  `idUser` int(11) NOT NULL,
  `idModule` int(11) NOT NULL,
  `dateConsultation` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `suivi`
--

INSERT INTO `suivi` (`idSuivi`, `idUser`, `idModule`, `dateConsultation`) VALUES
(7, 4, 57, '2024-03-07');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `IdUser` int(11) NOT NULL,
  `UserName` varchar(255) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Status` varchar(255) NOT NULL,
  `State` varchar(255) NOT NULL DEFAULT 'Active'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`IdUser`, `UserName`, `Email`, `Password`, `Status`, `State`) VALUES
(1, 'Mahmoud Abid', 'test@gmail.com', '$2a$12$JxqbD7S/gtE25tln.3u9Uu8aV6YdYZzQxM.ogwTTDDt8xvL9WZN.q', 'Admin', 'Active'),
(4, 'Wicked', 'wickedlemauvais@gmail.com', '$2a$10$oXci61JIIkZFgoJAfpvuWe2JaumOx0xMUvSKY19xWGNFG2iBoJRVO', 'User', 'Active'),
(5, 'LiwaEddine', 'ayri.liwaEddine@gmail.com', '$2a$10$1PFRG3qs.5E3G2/DvEigkOHxMMt0Hiy15rEzo2/KER.pa8Id4Rvji', 'Coach', 'Active'),
(6, 'Hamadi', 'hamadi.abid@gmail.com', '$2a$10$G1lxNi5bzJJS9uG.7fq4OeKc9blxtZGrarVlEaomOaA9r9cLoMD7e', 'Coach', 'Active'),
(7, 'Mohamed Ali', 'medali.BenRomdhan@gmail.com', '$2a$10$aj5C0XgZ5z5yThx5wJTyDOCP23BITdmbSv8YGj84v.F/mpb7YsSqO', 'Coach', 'Active'),
(11, 'Abid', 'apiger@gmail.com', '$2a$10$IOJaMHi8QOTc3DQe7qGhMOjM67oswnG9AtoRf20gn/WwPjLbvCNmC', 'Coach', 'Active'),
(18, 'Mahmoud Abid', 'abidmahmoud13.18@gmail.com', '$2a$10$L7x/POeFM8NsvvHuBUs1tuddTiOR2YCGByucPVXroLDDQijcw7gSm', 'User', 'Active'),
(19, 'Mohamed Amine Ouerghi', 'amin.warghi@gmail.com', '$2a$10$/8G0gc3SRyPaeGP.GuNye.f2jHbCReEh004j5R7IW9SQA28rSmGXm', 'User', 'Active');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `achat`
--
ALTER TABLE `achat`
  ADD PRIMARY KEY (`idc`,`id_produit`),
  ADD KEY `Fk_idProduit_Achat` (`id_produit`);

--
-- Indexes for table `bibliotheque`
--
ALTER TABLE `bibliotheque`
  ADD PRIMARY KEY (`idBib`);

--
-- Indexes for table `commande`
--
ALTER TABLE `commande`
  ADD PRIMARY KEY (`idc`),
  ADD KEY `FK_idUser_Commande` (`id_user`);

--
-- Indexes for table `cours`
--
ALTER TABLE `cours`
  ADD PRIMARY KEY (`idCours`),
  ADD KEY `fk_cours_modules` (`ID_Module`);

--
-- Indexes for table `dossier`
--
ALTER TABLE `dossier`
  ADD PRIMARY KEY (`idOffre`,`idUser`),
  ADD KEY `FK_Dossier_idUser` (`idUser`);

--
-- Indexes for table `exams`
--
ALTER TABLE `exams`
  ADD PRIMARY KEY (`idExam`);

--
-- Indexes for table `googleoauth`
--
ALTER TABLE `googleoauth`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `livre`
--
ALTER TABLE `livre`
  ADD PRIMARY KEY (`idLivre`),
  ADD KEY `idBib` (`idBib`);

--
-- Indexes for table `logs`
--
ALTER TABLE `logs`
  ADD PRIMARY KEY (`idLog`),
  ADD KEY `fk_User` (`idUser`);

--
-- Indexes for table `modules`
--
ALTER TABLE `modules`
  ADD PRIMARY KEY (`idModule`);

--
-- Indexes for table `notes`
--
ALTER TABLE `notes`
  ADD PRIMARY KEY (`idUser`,`idexam`),
  ADD KEY `fk_user` (`idUser`),
  ADD KEY `fk_exam` (`idexam`);

--
-- Indexes for table `offre`
--
ALTER TABLE `offre`
  ADD PRIMARY KEY (`idOffre`),
  ADD KEY `FK_Offre_idSociete` (`idSociete`);

--
-- Indexes for table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`id_produit`);

--
-- Indexes for table `profile`
--
ALTER TABLE `profile`
  ADD PRIMARY KEY (`idProfile`),
  ADD KEY `fk_User` (`idUser`);

--
-- Indexes for table `profileadmin`
--
ALTER TABLE `profileadmin`
  ADD KEY `fk_Profile` (`idProfile`);

--
-- Indexes for table `profilecoach`
--
ALTER TABLE `profilecoach`
  ADD PRIMARY KEY (`idProfile`),
  ADD KEY `fk_ProfileCoach` (`idModule`);

--
-- Indexes for table `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`idquestion`),
  ADD KEY `fk_questions_exam` (`idExam`);

--
-- Indexes for table `societe`
--
ALTER TABLE `societe`
  ADD PRIMARY KEY (`idSociete`);

--
-- Indexes for table `suivi`
--
ALTER TABLE `suivi`
  ADD PRIMARY KEY (`idSuivi`),
  ADD KEY `fk_user` (`idUser`),
  ADD KEY `fk_module` (`idModule`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`IdUser`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bibliotheque`
--
ALTER TABLE `bibliotheque`
  MODIFY `idBib` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `commande`
--
ALTER TABLE `commande`
  MODIFY `idc` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `cours`
--
ALTER TABLE `cours`
  MODIFY `idCours` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=90;

--
-- AUTO_INCREMENT for table `exams`
--
ALTER TABLE `exams`
  MODIFY `idExam` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `livre`
--
ALTER TABLE `livre`
  MODIFY `idLivre` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `logs`
--
ALTER TABLE `logs`
  MODIFY `idLog` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT for table `modules`
--
ALTER TABLE `modules`
  MODIFY `idModule` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=58;

--
-- AUTO_INCREMENT for table `offre`
--
ALTER TABLE `offre`
  MODIFY `idOffre` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `produit`
--
ALTER TABLE `produit`
  MODIFY `id_produit` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `profile`
--
ALTER TABLE `profile`
  MODIFY `idProfile` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `questions`
--
ALTER TABLE `questions`
  MODIFY `idquestion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `societe`
--
ALTER TABLE `societe`
  MODIFY `idSociete` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `suivi`
--
ALTER TABLE `suivi`
  MODIFY `idSuivi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `IdUser` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `achat`
--
ALTER TABLE `achat`
  ADD CONSTRAINT `FK_idCommande_achat` FOREIGN KEY (`idc`) REFERENCES `commande` (`idc`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `Fk_idProduit_Achat` FOREIGN KEY (`id_produit`) REFERENCES `produit` (`id_produit`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `commande`
--
ALTER TABLE `commande`
  ADD CONSTRAINT `FK_idUser_Commande` FOREIGN KEY (`id_user`) REFERENCES `user` (`IdUser`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `cours`
--
ALTER TABLE `cours`
  ADD CONSTRAINT `fk_cours_modules` FOREIGN KEY (`ID_Module`) REFERENCES `modules` (`idModule`);

--
-- Constraints for table `dossier`
--
ALTER TABLE `dossier`
  ADD CONSTRAINT `FK_idOffre_Dossier` FOREIGN KEY (`idOffre`) REFERENCES `offre` (`idOffre`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_idUser_Dossier` FOREIGN KEY (`idUser`) REFERENCES `user` (`IdUser`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `livre`
--
ALTER TABLE `livre`
  ADD CONSTRAINT `idBib` FOREIGN KEY (`idBib`) REFERENCES `bibliotheque` (`idBib`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `logs`
--
ALTER TABLE `logs`
  ADD CONSTRAINT `FK_idUser_Logs` FOREIGN KEY (`idUser`) REFERENCES `user` (`IdUser`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `notes`
--
ALTER TABLE `notes`
  ADD CONSTRAINT `FK_idExam_Note` FOREIGN KEY (`idexam`) REFERENCES `exams` (`idExam`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `Fk_idUser_Note` FOREIGN KEY (`idUser`) REFERENCES `user` (`IdUser`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `offre`
--
ALTER TABLE `offre`
  ADD CONSTRAINT `FK_idSociete` FOREIGN KEY (`idSociete`) REFERENCES `societe` (`idSociete`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `profilecoach`
--
ALTER TABLE `profilecoach`
  ADD CONSTRAINT `fk_coach` FOREIGN KEY (`idModule`) REFERENCES `modules` (`idModule`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `questions`
--
ALTER TABLE `questions`
  ADD CONSTRAINT `FK_idExam` FOREIGN KEY (`idExam`) REFERENCES `exams` (`idExam`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `suivi`
--
ALTER TABLE `suivi`
  ADD CONSTRAINT `fk_module` FOREIGN KEY (`idModule`) REFERENCES `modules` (`idModule`),
  ADD CONSTRAINT `fk_user` FOREIGN KEY (`idUser`) REFERENCES `user` (`IdUser`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
