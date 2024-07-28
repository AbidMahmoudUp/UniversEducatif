<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20240327234752 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE google_oauth2 (id INT AUTO_INCREMENT NOT NULL, email VARCHAR(255) DEFAULT NULL, roles JSON DEFAULT NULL COMMENT \'(DC2Type:json)\', password VARCHAR(255) DEFAULT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('DROP TABLE googleoauth');
        $this->addSql('ALTER TABLE commande ADD CONSTRAINT FK_6EEAA67D6B3CA4B FOREIGN KEY (id_user) REFERENCES user (IdUser)');
        $this->addSql('CREATE INDEX IDX_6EEAA67D6B3CA4B ON commande (id_user)');
        $this->addSql('ALTER TABLE cours DROP FOREIGN KEY fk_cours_modules');
        $this->addSql('DROP INDEX fk_cours_modules ON cours');
        $this->addSql('CREATE INDEX IDX_FDCA8C9C56387606 ON cours (ID_Module)');
        $this->addSql('ALTER TABLE cours ADD CONSTRAINT fk_cours_modules FOREIGN KEY (ID_Module) REFERENCES modules (idModule)');
        $this->addSql('ALTER TABLE dossier DROP FOREIGN KEY FK_idOffre_Dossier');
        $this->addSql('ALTER TABLE dossier DROP FOREIGN KEY FK_idUser_Dossier');
        $this->addSql('DROP INDEX `primary` ON dossier');
        $this->addSql('ALTER TABLE dossier DROP FOREIGN KEY FK_idUser_Dossier');
        $this->addSql('ALTER TABLE dossier ADD CONSTRAINT FK_3D48E037FE6E88D7 FOREIGN KEY (idUser) REFERENCES user (IdUser)');
        $this->addSql('ALTER TABLE dossier ADD CONSTRAINT FK_3D48E037B842C572 FOREIGN KEY (idOffre) REFERENCES offre (idOffre)');
        $this->addSql('ALTER TABLE dossier ADD PRIMARY KEY (idUser, idOffre)');
        $this->addSql('DROP INDEX fk_dossier_iduser ON dossier');
        $this->addSql('CREATE INDEX IDX_3D48E037FE6E88D7 ON dossier (idUser)');
        $this->addSql('ALTER TABLE dossier ADD CONSTRAINT FK_idUser_Dossier FOREIGN KEY (idUser) REFERENCES user (IdUser) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE exams CHANGE date date DATE NOT NULL');
        $this->addSql('ALTER TABLE livre DROP FOREIGN KEY idBib');
        $this->addSql('ALTER TABLE livre DROP FOREIGN KEY idBib');
        $this->addSql('ALTER TABLE livre ADD pathPDF VARCHAR(255) NOT NULL, DROP lienPDF, CHANGE image image VARCHAR(255) NOT NULL');
        $this->addSql('ALTER TABLE livre ADD CONSTRAINT FK_AC634F9982D90B41 FOREIGN KEY (idBib) REFERENCES bibliotheque (idBib)');
        $this->addSql('DROP INDEX idbib ON livre');
        $this->addSql('CREATE INDEX IDX_AC634F9982D90B41 ON livre (idBib)');
        $this->addSql('ALTER TABLE livre ADD CONSTRAINT idBib FOREIGN KEY (idBib) REFERENCES bibliotheque (idBib) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE logs DROP FOREIGN KEY FK_idUser_Logs');
        $this->addSql('ALTER TABLE logs DROP FOREIGN KEY FK_idUser_Logs');
        $this->addSql('ALTER TABLE logs CHANGE date date DATETIME NOT NULL');
        $this->addSql('ALTER TABLE logs ADD CONSTRAINT FK_F08FC65CFE6E88D7 FOREIGN KEY (idUser) REFERENCES user (IdUser)');
        $this->addSql('DROP INDEX fk_user ON logs');
        $this->addSql('CREATE INDEX IDX_F08FC65CFE6E88D7 ON logs (idUser)');
        $this->addSql('ALTER TABLE logs ADD CONSTRAINT FK_idUser_Logs FOREIGN KEY (idUser) REFERENCES user (IdUser) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE notes DROP FOREIGN KEY FK_idExam_Note');
        $this->addSql('ALTER TABLE notes DROP FOREIGN KEY Fk_idUser_Note');
        $this->addSql('DROP INDEX `primary` ON notes');
        $this->addSql('ALTER TABLE notes DROP FOREIGN KEY FK_idExam_Note');
        $this->addSql('ALTER TABLE notes DROP FOREIGN KEY Fk_idUser_Note');
        $this->addSql('ALTER TABLE notes ADD CONSTRAINT FK_11BA68CEB745766 FOREIGN KEY (idexam) REFERENCES exams (idExam)');
        $this->addSql('ALTER TABLE notes ADD CONSTRAINT FK_11BA68CFE6E88D7 FOREIGN KEY (idUser) REFERENCES user (IdUser)');
        $this->addSql('ALTER TABLE notes ADD PRIMARY KEY (idexam, idUser)');
        $this->addSql('DROP INDEX fk_exam ON notes');
        $this->addSql('CREATE INDEX IDX_11BA68CEB745766 ON notes (idexam)');
        $this->addSql('DROP INDEX fk_user ON notes');
        $this->addSql('CREATE INDEX IDX_11BA68CFE6E88D7 ON notes (idUser)');
        $this->addSql('ALTER TABLE notes ADD CONSTRAINT FK_idExam_Note FOREIGN KEY (idexam) REFERENCES exams (idExam) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE notes ADD CONSTRAINT Fk_idUser_Note FOREIGN KEY (idUser) REFERENCES user (IdUser) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE offre DROP FOREIGN KEY FK_idSociete');
        $this->addSql('ALTER TABLE offre DROP FOREIGN KEY FK_idSociete');
        $this->addSql('ALTER TABLE offre ADD CONSTRAINT FK_AF86866F9DC564F FOREIGN KEY (idSociete) REFERENCES societe (idSociete)');
        $this->addSql('DROP INDEX fk_offre_idsociete ON offre');
        $this->addSql('CREATE INDEX IDX_AF86866F9DC564F ON offre (idSociete)');
        $this->addSql('ALTER TABLE offre ADD CONSTRAINT FK_idSociete FOREIGN KEY (idSociete) REFERENCES societe (idSociete) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE produit CHANGE nom nom VARCHAR(255) NOT NULL, CHANGE type type VARCHAR(255) NOT NULL, CHANGE prix_init prix_init DOUBLE PRECISION NOT NULL, CHANGE marge marge DOUBLE PRECISION NOT NULL, CHANGE video video VARCHAR(255) NOT NULL, CHANGE audio audio VARCHAR(255) NOT NULL, CHANGE image image VARCHAR(255) NOT NULL, CHANGE descrip descrip VARCHAR(255) NOT NULL');
        $this->addSql('ALTER TABLE profile DROP INDEX fk_User, ADD UNIQUE INDEX UNIQ_8157AA0FFE6E88D7 (idUser)');
        $this->addSql('ALTER TABLE profile DROP FOREIGN KEY FK_Userid_profile');
        $this->addSql('ALTER TABLE profile DROP CreationDate, CHANGE idUser idUser INT NOT NULL, CHANGE firstName firstName VARCHAR(255) NOT NULL, CHANGE lastName lastName VARCHAR(255) NOT NULL, CHANGE picture picture VARCHAR(255) NOT NULL, CHANGE address address VARCHAR(255) NOT NULL, CHANGE location location VARCHAR(255) NOT NULL, CHANGE phoneNumber phoneNumber INT NOT NULL');
        $this->addSql('ALTER TABLE profile ADD CONSTRAINT FK_8157AA0FFE6E88D7 FOREIGN KEY (idUser) REFERENCES user (IdUser)');
        $this->addSql('ALTER TABLE questions DROP FOREIGN KEY FK_idExam');
        $this->addSql('ALTER TABLE questions DROP FOREIGN KEY FK_idExam');
        $this->addSql('ALTER TABLE questions ADD CONSTRAINT FK_8ADC54D54B46F858 FOREIGN KEY (idExam) REFERENCES exams (idExam)');
        $this->addSql('DROP INDEX fk_questions_exam ON questions');
        $this->addSql('CREATE INDEX IDX_8ADC54D54B46F858 ON questions (idExam)');
        $this->addSql('ALTER TABLE questions ADD CONSTRAINT FK_idExam FOREIGN KEY (idExam) REFERENCES exams (idExam) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE suivi DROP FOREIGN KEY fk_module');
        $this->addSql('ALTER TABLE suivi DROP FOREIGN KEY fk_user');
        $this->addSql('DROP INDEX fk_module ON suivi');
        $this->addSql('CREATE INDEX IDX_2EBCCA8F6F358EB2 ON suivi (idModule)');
        $this->addSql('DROP INDEX fk_user ON suivi');
        $this->addSql('CREATE INDEX IDX_2EBCCA8FFE6E88D7 ON suivi (idUser)');
        $this->addSql('ALTER TABLE suivi ADD CONSTRAINT fk_module FOREIGN KEY (idModule) REFERENCES modules (idModule)');
        $this->addSql('ALTER TABLE suivi ADD CONSTRAINT fk_user FOREIGN KEY (idUser) REFERENCES user (IdUser)');
        $this->addSql('ALTER TABLE user CHANGE State State VARCHAR(255) NOT NULL');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE googleoauth (id VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, email VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, verified_email TINYINT(1) NOT NULL, name VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, given_name VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, familly_name VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, picture VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, locale VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_general_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('DROP TABLE google_oauth2');
        $this->addSql('ALTER TABLE commande DROP FOREIGN KEY FK_6EEAA67D6B3CA4B');
        $this->addSql('DROP INDEX IDX_6EEAA67D6B3CA4B ON commande');
        $this->addSql('ALTER TABLE cours DROP FOREIGN KEY FK_FDCA8C9C56387606');
        $this->addSql('DROP INDEX idx_fdca8c9c56387606 ON cours');
        $this->addSql('CREATE INDEX fk_cours_modules ON cours (ID_Module)');
        $this->addSql('ALTER TABLE cours ADD CONSTRAINT FK_FDCA8C9C56387606 FOREIGN KEY (ID_Module) REFERENCES modules (idModule)');
        $this->addSql('ALTER TABLE dossier DROP FOREIGN KEY FK_3D48E037FE6E88D7');
        $this->addSql('ALTER TABLE dossier DROP FOREIGN KEY FK_3D48E037B842C572');
        $this->addSql('DROP INDEX `PRIMARY` ON dossier');
        $this->addSql('ALTER TABLE dossier DROP FOREIGN KEY FK_3D48E037FE6E88D7');
        $this->addSql('ALTER TABLE dossier ADD CONSTRAINT FK_idOffre_Dossier FOREIGN KEY (idOffre) REFERENCES offre (idOffre) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE dossier ADD CONSTRAINT FK_idUser_Dossier FOREIGN KEY (idUser) REFERENCES user (IdUser) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE dossier ADD PRIMARY KEY (idOffre, idUser)');
        $this->addSql('DROP INDEX idx_3d48e037fe6e88d7 ON dossier');
        $this->addSql('CREATE INDEX FK_Dossier_idUser ON dossier (idUser)');
        $this->addSql('ALTER TABLE dossier ADD CONSTRAINT FK_3D48E037FE6E88D7 FOREIGN KEY (idUser) REFERENCES user (IdUser)');
        $this->addSql('ALTER TABLE exams CHANGE date date DATE DEFAULT NULL');
        $this->addSql('ALTER TABLE livre DROP FOREIGN KEY FK_AC634F9982D90B41');
        $this->addSql('ALTER TABLE livre DROP FOREIGN KEY FK_AC634F9982D90B41');
        $this->addSql('ALTER TABLE livre ADD lienPDF VARCHAR(255) DEFAULT NULL, DROP pathPDF, CHANGE image image VARCHAR(255) DEFAULT NULL');
        $this->addSql('ALTER TABLE livre ADD CONSTRAINT idBib FOREIGN KEY (idBib) REFERENCES bibliotheque (idBib) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('DROP INDEX idx_ac634f9982d90b41 ON livre');
        $this->addSql('CREATE INDEX idBib ON livre (idBib)');
        $this->addSql('ALTER TABLE livre ADD CONSTRAINT FK_AC634F9982D90B41 FOREIGN KEY (idBib) REFERENCES bibliotheque (idBib)');
        $this->addSql('ALTER TABLE logs DROP FOREIGN KEY FK_F08FC65CFE6E88D7');
        $this->addSql('ALTER TABLE logs DROP FOREIGN KEY FK_F08FC65CFE6E88D7');
        $this->addSql('ALTER TABLE logs CHANGE date date DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL');
        $this->addSql('ALTER TABLE logs ADD CONSTRAINT FK_idUser_Logs FOREIGN KEY (idUser) REFERENCES user (IdUser) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('DROP INDEX idx_f08fc65cfe6e88d7 ON logs');
        $this->addSql('CREATE INDEX fk_User ON logs (idUser)');
        $this->addSql('ALTER TABLE logs ADD CONSTRAINT FK_F08FC65CFE6E88D7 FOREIGN KEY (idUser) REFERENCES user (IdUser)');
        $this->addSql('ALTER TABLE notes DROP FOREIGN KEY FK_11BA68CEB745766');
        $this->addSql('ALTER TABLE notes DROP FOREIGN KEY FK_11BA68CFE6E88D7');
        $this->addSql('DROP INDEX `PRIMARY` ON notes');
        $this->addSql('ALTER TABLE notes DROP FOREIGN KEY FK_11BA68CEB745766');
        $this->addSql('ALTER TABLE notes DROP FOREIGN KEY FK_11BA68CFE6E88D7');
        $this->addSql('ALTER TABLE notes ADD CONSTRAINT FK_idExam_Note FOREIGN KEY (idexam) REFERENCES exams (idExam) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE notes ADD CONSTRAINT Fk_idUser_Note FOREIGN KEY (idUser) REFERENCES user (IdUser) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE notes ADD PRIMARY KEY (idUser, idexam)');
        $this->addSql('DROP INDEX idx_11ba68ceb745766 ON notes');
        $this->addSql('CREATE INDEX fk_exam ON notes (idexam)');
        $this->addSql('DROP INDEX idx_11ba68cfe6e88d7 ON notes');
        $this->addSql('CREATE INDEX fk_user ON notes (idUser)');
        $this->addSql('ALTER TABLE notes ADD CONSTRAINT FK_11BA68CEB745766 FOREIGN KEY (idexam) REFERENCES exams (idExam)');
        $this->addSql('ALTER TABLE notes ADD CONSTRAINT FK_11BA68CFE6E88D7 FOREIGN KEY (idUser) REFERENCES user (IdUser)');
        $this->addSql('ALTER TABLE offre DROP FOREIGN KEY FK_AF86866F9DC564F');
        $this->addSql('ALTER TABLE offre DROP FOREIGN KEY FK_AF86866F9DC564F');
        $this->addSql('ALTER TABLE offre ADD CONSTRAINT FK_idSociete FOREIGN KEY (idSociete) REFERENCES societe (idSociete) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('DROP INDEX idx_af86866f9dc564f ON offre');
        $this->addSql('CREATE INDEX FK_Offre_idSociete ON offre (idSociete)');
        $this->addSql('ALTER TABLE offre ADD CONSTRAINT FK_AF86866F9DC564F FOREIGN KEY (idSociete) REFERENCES societe (idSociete)');
        $this->addSql('ALTER TABLE produit CHANGE nom nom VARCHAR(255) DEFAULT NULL, CHANGE type type VARCHAR(255) DEFAULT NULL, CHANGE prix_init prix_init DOUBLE PRECISION DEFAULT NULL, CHANGE marge marge DOUBLE PRECISION DEFAULT NULL, CHANGE video video VARCHAR(255) DEFAULT NULL, CHANGE audio audio VARCHAR(255) DEFAULT NULL, CHANGE image image VARCHAR(255) DEFAULT NULL, CHANGE descrip descrip VARCHAR(255) DEFAULT NULL');
        $this->addSql('ALTER TABLE profile DROP INDEX UNIQ_8157AA0FFE6E88D7, ADD INDEX fk_User (idUser)');
        $this->addSql('ALTER TABLE profile DROP FOREIGN KEY FK_8157AA0FFE6E88D7');
        $this->addSql('ALTER TABLE profile ADD CreationDate DATETIME DEFAULT NULL, CHANGE firstName firstName VARCHAR(255) DEFAULT NULL, CHANGE lastName lastName VARCHAR(255) DEFAULT NULL, CHANGE picture picture VARCHAR(255) DEFAULT NULL, CHANGE address address VARCHAR(255) DEFAULT NULL, CHANGE location location VARCHAR(255) DEFAULT NULL, CHANGE phoneNumber phoneNumber INT DEFAULT NULL, CHANGE idUser idUser INT DEFAULT NULL');
        $this->addSql('ALTER TABLE profile ADD CONSTRAINT FK_Userid_profile FOREIGN KEY (idUser) REFERENCES user (IdUser) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE questions DROP FOREIGN KEY FK_8ADC54D54B46F858');
        $this->addSql('ALTER TABLE questions DROP FOREIGN KEY FK_8ADC54D54B46F858');
        $this->addSql('ALTER TABLE questions ADD CONSTRAINT FK_idExam FOREIGN KEY (idExam) REFERENCES exams (idExam) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('DROP INDEX idx_8adc54d54b46f858 ON questions');
        $this->addSql('CREATE INDEX fk_questions_exam ON questions (idExam)');
        $this->addSql('ALTER TABLE questions ADD CONSTRAINT FK_8ADC54D54B46F858 FOREIGN KEY (idExam) REFERENCES exams (idExam)');
        $this->addSql('ALTER TABLE suivi DROP FOREIGN KEY FK_2EBCCA8F6F358EB2');
        $this->addSql('ALTER TABLE suivi DROP FOREIGN KEY FK_2EBCCA8FFE6E88D7');
        $this->addSql('DROP INDEX idx_2ebcca8ffe6e88d7 ON suivi');
        $this->addSql('CREATE INDEX fk_user ON suivi (idUser)');
        $this->addSql('DROP INDEX idx_2ebcca8f6f358eb2 ON suivi');
        $this->addSql('CREATE INDEX fk_module ON suivi (idModule)');
        $this->addSql('ALTER TABLE suivi ADD CONSTRAINT FK_2EBCCA8F6F358EB2 FOREIGN KEY (idModule) REFERENCES modules (idModule)');
        $this->addSql('ALTER TABLE suivi ADD CONSTRAINT FK_2EBCCA8FFE6E88D7 FOREIGN KEY (idUser) REFERENCES user (IdUser)');
        $this->addSql('ALTER TABLE user CHANGE State State VARCHAR(255) DEFAULT \'Active\' NOT NULL');
    }
}
