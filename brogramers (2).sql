-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : jeu. 07 mars 2024 à 22:26
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.1.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `brogramers`
--

-- --------------------------------------------------------

--
-- Structure de la table `bibliotheque`
--

CREATE TABLE `bibliotheque` (
  `idBib` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `mail` varchar(255) NOT NULL,
  `nbrLivre` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `bibliotheque`
--

INSERT INTO `bibliotheque` (`idBib`, `nom`, `mail`, `nbrLivre`) VALUES
(16, 'zaea', 'ezae', 12),
(18, 'dsdvs', 'bfv', 100);

-- --------------------------------------------------------

--
-- Structure de la table `cours`
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
-- Déchargement des données de la table `cours`
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
-- Structure de la table `dossier`
--

CREATE TABLE `dossier` (
  `idOffre` int(11) NOT NULL,
  `idUser` int(11) NOT NULL,
  `status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `dossier`
--

INSERT INTO `dossier` (`idOffre`, `idUser`, `status`) VALUES
(6, 4, 'Accepté'),
(7, 4, 'Refusé'),
(8, 4, 'Accepté');

-- --------------------------------------------------------

--
-- Structure de la table `exams`
--

CREATE TABLE `exams` (
  `idExam` int(11) NOT NULL,
  `module` varchar(255) NOT NULL,
  `date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `exams`
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
-- Structure de la table `googleoauth`
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
-- Déchargement des données de la table `googleoauth`
--

INSERT INTO `googleoauth` (`id`, `email`, `verified_email`, `name`, `given_name`, `familly_name`, `picture`, `locale`) VALUES
('108703346998401561498', 'amin.warghi@gmail.com', 1, 'Mohamed Amine Ouerghi', 'Mohamed Amine', 'Ouerghi', 'https://lh3.googleusercontent.com/a/ACg8ocKLFvmudTGpsnnlivajjpVXlpLinRdScHXKJKZTvMsx3Ec=s96-c', 'fr');

-- --------------------------------------------------------

--
-- Structure de la table `livre`
--

CREATE TABLE `livre` (
  `idLivre` int(11) NOT NULL,
  `type` varchar(255) NOT NULL,
  `titre` varchar(255) NOT NULL,
  `auteur` varchar(255) NOT NULL,
  `langue` varchar(255) NOT NULL,
  `idBib` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `livre`
--

INSERT INTO `livre` (`idLivre`, `type`, `titre`, `auteur`, `langue`, `idBib`) VALUES
(6, 'SQDSQ', 'TETEZ', 'CXC', 'SDQDFSQF', 16),
(7, 'SQDSQ', 'TETEZ', 'CXC', 'SDQDFSQF', 16);

-- --------------------------------------------------------

--
-- Structure de la table `logs`
--

CREATE TABLE `logs` (
  `idLog` int(11) NOT NULL,
  `idUser` int(11) NOT NULL,
  `description1` varchar(255) NOT NULL,
  `date` date NOT NULL,
  `action1` varchar(255) NOT NULL,
  `action2` varchar(255) NOT NULL,
  `description2` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `logs`
--

INSERT INTO `logs` (`idLog`, `idUser`, `description1`, `date`, `action1`, `action2`, `description2`) VALUES
(1, 1, 'a ajouté', '2024-03-01', 'Wael Sboui', 'Coach', 'en tand que nouvel'),
(2, 1, 'a ajouté', '2024-03-01', 'Wael Sboui', 'Coach', 'en tand que nouvel'),
(3, 1, 'a ajouté', '2024-03-01', 'LiwaEddine Ayari', 'Coach', 'en tand que nouvel'),
(4, 1, 'a ajouté', '2024-03-01', 'Hamadi Abid', 'Coach', 'en tand que nouvel'),
(5, 1, 'a ajouté', '2024-03-01', 'Mohamed Ali Ben Romdhann', 'Coach', 'en tand que nouvel'),
(6, 1, 'a ajouté', '2024-03-01', 'Goujaa Salah', 'Coach', 'en tand que nouvel'),
(7, 3, 'test', '2024-03-03', 'test', 'test', 'test'),
(8, 3, 'test', '2024-03-03', 'test', 'test', 'test'),
(9, 3, 'test', '2024-03-03', 'test', 'test', 'test'),
(10, 3, 'test', '2024-03-03', 'test', 'test', 'test'),
(11, 3, 'llll', '2024-03-03', 'hhh', 'ggg', 'ff'),
(12, 3, 'jjjj', '2024-03-03', 'hhh', 'ff', 'ff'),
(13, 3, 'a ajouté Examen ', '2024-03-03', 'test exam', 'com.example.javafxprojectusertask.Entities.Exam', 'depuis la liste '),
(14, 3, 'a ajouté Examen ', '2024-03-03', 'KKKK', 'class com.example.javafxprojectusertask.Entities.Exam', 'depuis la liste '),
(15, 3, 'a ajouté Examen ', '2024-03-03', 'MOddd', 'Exam', 'depuis la liste '),
(16, 3, 'a supprimé Examen ', '2024-03-03', 'KKKK', 'Exam', 'depuis la liste '),
(17, 3, 'a modifié Examen ', '2024-03-03', 'wicked', 'Exam', 'dans la liste '),
(18, 1, 'a ajouté', '2024-03-04', 'Abid Mahmoud', 'Coach', 'en tand que nouvel'),
(19, 3, 'a ajouté Examen ', '2024-03-04', 'AZDSADA', 'Exam', 'dans la liste '),
(20, 11, 'a ajouté Examen ', '2024-03-07', 'Adada', 'Exam', 'dans la liste '),
(21, 11, 'a modifié Examen ', '2024-03-07', 'Adada', 'Exam', 'dans la liste '),
(22, 11, 'a ajouté Examen ', '2024-03-07', 'Adada', 'Exam', 'dans la liste '),
(23, 11, 'a ajouté Examen ', '2024-03-07', 'Adada', 'Exam', 'dans la liste '),
(24, 11, 'a ajouté Examen ', '2024-03-07', 'Adada', 'Exam', 'dans la liste '),
(25, 11, 'a ajouté Examen ', '2024-03-07', 'Adada', 'Exam', 'dans la liste '),
(26, 11, 'a ajouté Examen ', '2024-03-07', 'Adada', 'Exam', 'dans la liste '),
(27, 11, 'a ajouté Examen ', '2024-03-07', 'Adada', 'Exam', 'dans la liste '),
(28, 11, 'a ajouté Examen ', '2024-03-07', 'Adada', 'Exam', 'dans la liste ');

-- --------------------------------------------------------

--
-- Structure de la table `modules`
--

CREATE TABLE `modules` (
  `idModule` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `imgPath` varchar(255) NOT NULL,
  `tempsT` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Déchargement des données de la table `modules`
--

INSERT INTO `modules` (`idModule`, `nom`, `description`, `imgPath`, `tempsT`) VALUES
(55, 'AZDSADA', 'SDQSDSQdd', 'CCCC', 40),
(56, 'Adada', 'dadaddadadadaddadadaada', 'C:/xampp/htdocs/img/5938045.jpg', 69),
(57, 'SSS', 'suiiii', 'C:/xampp/htdocs/img/5938045.jpg', 3);

-- --------------------------------------------------------

--
-- Structure de la table `notes`
--

CREATE TABLE `notes` (
  `idUser` int(11) NOT NULL,
  `idexam` int(11) NOT NULL,
  `note` int(11) NOT NULL,
  `module` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `offre`
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
-- Déchargement des données de la table `offre`
--

INSERT INTO `offre` (`idOffre`, `description`, `niveau`, `dateDebut`, `dateFin`, `idSociete`) VALUES
(6, 'SDQDSQDKk', 1000, '2024-03-07', '2024-03-16', 4),
(7, 'SDSQDQ', 50, '2024-03-14', '2024-03-29', 6),
(8, 'AAAAAAAAA', 2, '2024-02-26', '2024-03-05', 4);

-- --------------------------------------------------------

--
-- Structure de la table `produit`
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
-- Structure de la table `profile`
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
-- Déchargement des données de la table `profile`
--

INSERT INTO `profile` (`idProfile`, `idUser`, `firstName`, `lastName`, `picture`, `address`, `location`, `phoneNumber`) VALUES
(1, 1, 'Mahmoud', 'Abid', 'http://127.0.0.1/img/Wicked.png', 'Tnuis', 'Ariana', NULL),
(2, 3, 'KKK', 'Sboui', 'http://127.0.0.1/img/380009749_854927022567056_2584231981793520668_n.jpg', 'Tunis', 'Tunis', 12345678),
(3, 4, 'Wicked', '', 'file:C:/Users/Wicked/Desktop/JavaFxProject/JavaFxProjectUserTask/src/main/resources/Images/adminIconDefault.png', '', '', 25361478),
(4, 5, 'LiwaEddine', 'Ayari', 'file:C:/Users/Wicked/Desktop/JavaFxProject/JavaFxProjectUserTask/src/main/resources/Images/adminIconDefault.png', 'Morneguiya', 'Esprit', 93625147),
(5, 6, 'Hamadi', 'Abid', 'file:C:/Users/Wicked/Desktop/JavaFxProject/JavaFxProjectUserTask/src/main/resources/Images/adminIconDefault.png', 'Tounis', 'Tounis', 65893214),
(6, 7, 'Mohamed Ali', 'Ben Romdhann', 'file:C:/Users/Wicked/Desktop/JavaFxProject/JavaFxProjectUserTask/src/main/resources/Images/adminIconDefault.png', 'Tunis', 'Tunis', 25361478),
(10, 11, 'Abid', 'Mahmoud', 'file:C:/Users/Wicked/Desktop/JavaFxProject/JavaFxProjectUserTask/src/main/resources/Images/adminIconDefault.png', 'Tinos', 'Tnis', 26381661),
(17, 18, 'Mahmoud', 'Abid', 'https://lh3.googleusercontent.com/a/ACg8ocLsmoV5w7o7M1oqUfmJwkK7nLqau73XZgBNY4J66XOdLg=s96-c', '', 'fr', 0),
(18, 19, 'Mohamed Amine', 'Ouerghi', 'https://lh3.googleusercontent.com/a/ACg8ocKLFvmudTGpsnnlivajjpVXlpLinRdScHXKJKZTvMsx3Ec=s96-c', '', 'fr', 0);

-- --------------------------------------------------------

--
-- Structure de la table `profileadmin`
--

CREATE TABLE `profileadmin` (
  `idProfile` int(11) NOT NULL,
  `reponsabilty` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `profileadmin`
--

INSERT INTO `profileadmin` (`idProfile`, `reponsabilty`, `title`) VALUES
(1, 'Gestion Utilisateur', 'FullStackDeveloper');

-- --------------------------------------------------------

--
-- Structure de la table `profilecoach`
--

CREATE TABLE `profilecoach` (
  `idProfile` int(11) NOT NULL,
  `idModule` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `profilecoach`
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
-- Structure de la table `questions`
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
-- Déchargement des données de la table `questions`
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
-- Structure de la table `societe`
--

CREATE TABLE `societe` (
  `idSociete` int(11) NOT NULL,
  `nomSociete` varchar(30) NOT NULL,
  `description` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `societe`
--

INSERT INTO `societe` (`idSociete`, `nomSociete`, `description`) VALUES
(1, 'fzefez', 'fzefefezf'),
(3, 'SDQ', 'FFFFF'),
(4, 'eza', 'ezae'),
(6, 'rgr', 'gregree');

-- --------------------------------------------------------

--
-- Structure de la table `suivi`
--

CREATE TABLE `suivi` (
  `idSuivi` int(11) NOT NULL,
  `idUser` int(11) NOT NULL,
  `idModule` int(11) NOT NULL,
  `dateConsultation` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Déchargement des données de la table `suivi`
--

INSERT INTO `suivi` (`idSuivi`, `idUser`, `idModule`, `dateConsultation`) VALUES
(7, 4, 57, '2024-03-07');

-- --------------------------------------------------------

--
-- Structure de la table `user`
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
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`IdUser`, `UserName`, `Email`, `Password`, `Status`, `State`) VALUES
(1, 'Mahmoud Abid', 'test@gmail.com', '$2a$12$JxqbD7S/gtE25tln.3u9Uu8aV6YdYZzQxM.ogwTTDDt8xvL9WZN.q', 'Admin', 'Active'),
(3, 'Wael', 'wael.sboui@gmail.com', '$2a$10$dFoZGWCzFBrjv51A.dKdQu3lNCe87pXSVIgw1wzE.Vf3SMwb2O2aC', 'Coach', 'Active'),
(4, 'Wicked', 'wickedlemauvais@gmail.com', '$2a$10$oXci61JIIkZFgoJAfpvuWe2JaumOx0xMUvSKY19xWGNFG2iBoJRVO', 'User', 'Active'),
(5, 'LiwaEddine', 'ayri.liwaEddine@gmail.com', '$2a$10$1PFRG3qs.5E3G2/DvEigkOHxMMt0Hiy15rEzo2/KER.pa8Id4Rvji', 'Coach', 'Active'),
(6, 'Hamadi', 'hamadi.abid@gmail.com', '$2a$10$G1lxNi5bzJJS9uG.7fq4OeKc9blxtZGrarVlEaomOaA9r9cLoMD7e', 'Coach', 'Active'),
(7, 'Mohamed Ali', 'medali.BenRomdhan@gmail.com', '$2a$10$aj5C0XgZ5z5yThx5wJTyDOCP23BITdmbSv8YGj84v.F/mpb7YsSqO', 'Coach', 'Active'),
(11, 'Abid', 'apiger@gmail.com', '$2a$10$IOJaMHi8QOTc3DQe7qGhMOjM67oswnG9AtoRf20gn/WwPjLbvCNmC', 'Coach', 'Active'),
(18, 'Mahmoud Abid', 'abidmahmoud13.18@gmail.com', '$2a$10$L7x/POeFM8NsvvHuBUs1tuddTiOR2YCGByucPVXroLDDQijcw7gSm', 'User', 'Active'),
(19, 'Mohamed Amine Ouerghi', 'amin.warghi@gmail.com', '$2a$10$/8G0gc3SRyPaeGP.GuNye.f2jHbCReEh004j5R7IW9SQA28rSmGXm', 'User', 'Active');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `bibliotheque`
--
ALTER TABLE `bibliotheque`
  ADD PRIMARY KEY (`idBib`);

--
-- Index pour la table `cours`
--
ALTER TABLE `cours`
  ADD PRIMARY KEY (`idCours`),
  ADD KEY `fk_cours_modules` (`ID_Module`);

--
-- Index pour la table `dossier`
--
ALTER TABLE `dossier`
  ADD PRIMARY KEY (`idOffre`,`idUser`),
  ADD KEY `FK_Dossier_idUser` (`idUser`);

--
-- Index pour la table `exams`
--
ALTER TABLE `exams`
  ADD PRIMARY KEY (`idExam`);

--
-- Index pour la table `googleoauth`
--
ALTER TABLE `googleoauth`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `livre`
--
ALTER TABLE `livre`
  ADD PRIMARY KEY (`idLivre`),
  ADD KEY `idBib` (`idBib`);

--
-- Index pour la table `logs`
--
ALTER TABLE `logs`
  ADD PRIMARY KEY (`idLog`),
  ADD KEY `fk_User` (`idUser`);

--
-- Index pour la table `modules`
--
ALTER TABLE `modules`
  ADD PRIMARY KEY (`idModule`);

--
-- Index pour la table `notes`
--
ALTER TABLE `notes`
  ADD PRIMARY KEY (`idUser`,`idexam`),
  ADD KEY `fk_user` (`idUser`),
  ADD KEY `fk_exam` (`idexam`);

--
-- Index pour la table `offre`
--
ALTER TABLE `offre`
  ADD PRIMARY KEY (`idOffre`),
  ADD KEY `FK_Offre_idSociete` (`idSociete`);

--
-- Index pour la table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`id_produit`);

--
-- Index pour la table `profile`
--
ALTER TABLE `profile`
  ADD PRIMARY KEY (`idProfile`),
  ADD KEY `fk_User` (`idUser`);

--
-- Index pour la table `profileadmin`
--
ALTER TABLE `profileadmin`
  ADD KEY `fk_Profile` (`idProfile`);

--
-- Index pour la table `profilecoach`
--
ALTER TABLE `profilecoach`
  ADD PRIMARY KEY (`idProfile`),
  ADD KEY `fk_ProfileCoach` (`idModule`);

--
-- Index pour la table `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`idquestion`),
  ADD KEY `fk_questions_exam` (`idExam`);

--
-- Index pour la table `societe`
--
ALTER TABLE `societe`
  ADD PRIMARY KEY (`idSociete`);

--
-- Index pour la table `suivi`
--
ALTER TABLE `suivi`
  ADD PRIMARY KEY (`idSuivi`),
  ADD KEY `fk_user` (`idUser`),
  ADD KEY `fk_module` (`idModule`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`IdUser`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `bibliotheque`
--
ALTER TABLE `bibliotheque`
  MODIFY `idBib` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT pour la table `cours`
--
ALTER TABLE `cours`
  MODIFY `idCours` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=90;

--
-- AUTO_INCREMENT pour la table `exams`
--
ALTER TABLE `exams`
  MODIFY `idExam` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT pour la table `livre`
--
ALTER TABLE `livre`
  MODIFY `idLivre` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT pour la table `logs`
--
ALTER TABLE `logs`
  MODIFY `idLog` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT pour la table `modules`
--
ALTER TABLE `modules`
  MODIFY `idModule` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=58;

--
-- AUTO_INCREMENT pour la table `offre`
--
ALTER TABLE `offre`
  MODIFY `idOffre` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT pour la table `produit`
--
ALTER TABLE `produit`
  MODIFY `id_produit` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `profile`
--
ALTER TABLE `profile`
  MODIFY `idProfile` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT pour la table `questions`
--
ALTER TABLE `questions`
  MODIFY `idquestion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT pour la table `societe`
--
ALTER TABLE `societe`
  MODIFY `idSociete` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT pour la table `suivi`
--
ALTER TABLE `suivi`
  MODIFY `idSuivi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `IdUser` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `cours`
--
ALTER TABLE `cours`
  ADD CONSTRAINT `fk_cours_modules` FOREIGN KEY (`ID_Module`) REFERENCES `modules` (`idModule`);

--
-- Contraintes pour la table `dossier`
--
ALTER TABLE `dossier`
  ADD CONSTRAINT `FK_idOffre_Dossier` FOREIGN KEY (`idOffre`) REFERENCES `offre` (`idOffre`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_idUser_Dossier` FOREIGN KEY (`idUser`) REFERENCES `user` (`IdUser`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `livre`
--
ALTER TABLE `livre`
  ADD CONSTRAINT `idBib` FOREIGN KEY (`idBib`) REFERENCES `bibliotheque` (`idBib`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `logs`
--
ALTER TABLE `logs`
  ADD CONSTRAINT `FK_idUser_Logs` FOREIGN KEY (`idUser`) REFERENCES `user` (`IdUser`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `notes`
--
ALTER TABLE `notes`
  ADD CONSTRAINT `FK_idExam_Note` FOREIGN KEY (`idexam`) REFERENCES `exams` (`idExam`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `Fk_idUser_Note` FOREIGN KEY (`idUser`) REFERENCES `user` (`IdUser`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `offre`
--
ALTER TABLE `offre`
  ADD CONSTRAINT `FK_idSociete` FOREIGN KEY (`idSociete`) REFERENCES `societe` (`idSociete`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `profilecoach`
--
ALTER TABLE `profilecoach`
  ADD CONSTRAINT `fk_coach` FOREIGN KEY (`idModule`) REFERENCES `modules` (`idModule`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `questions`
--
ALTER TABLE `questions`
  ADD CONSTRAINT `FK_idExam` FOREIGN KEY (`idExam`) REFERENCES `exams` (`idExam`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `suivi`
--
ALTER TABLE `suivi`
  ADD CONSTRAINT `fk_module` FOREIGN KEY (`idModule`) REFERENCES `modules` (`idModule`),
  ADD CONSTRAINT `fk_user` FOREIGN KEY (`idUser`) REFERENCES `user` (`IdUser`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
