-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: controle_matriculas
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.32-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `disciplina`
--

DROP TABLE IF EXISTS `disciplina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `disciplina` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nomeDisciplina` varchar(100) NOT NULL,
  `cargaHoraria` int(11) NOT NULL,
  `limiteAlunos` int(11) NOT NULL,
  `idProfessor` int(11) NOT NULL,
  `totalMatriculas` int(11) NOT NULL,
  `vagasRestantes` int(11) NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `fk_professor` (`idProfessor`),
  CONSTRAINT `fk_professor` FOREIGN KEY (`idProfessor`) REFERENCES `pessoa` (`idPessoa`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `disciplina`
--

LOCK TABLES `disciplina` WRITE;
/*!40000 ALTER TABLE `disciplina` DISABLE KEYS */;
INSERT INTO `disciplina` VALUES (1,'TADAW',60,20,30,0,20),(2,'TADEW',60,20,32,0,20),(5,'Matemática',35,15,35,1,14),(7,'Algoritmos',40,20,36,1,19),(8,'Sistema Operacional',32,25,31,0,25),(9,'Redes',28,18,38,0,18),(10,'Segurança e Auditoria',30,22,32,0,22),(11,'Banco de Dados',45,30,33,0,30),(12,'Inglês',160,35,38,0,0);
/*!40000 ALTER TABLE `disciplina` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `matricula`
--

DROP TABLE IF EXISTS `matricula`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `matricula` (
  `idMatricula` int(11) NOT NULL AUTO_INCREMENT,
  `idDisciplina` int(11) NOT NULL,
  `idAluno` int(11) NOT NULL,
  `dataMatricula` date NOT NULL,
  `valorPago` decimal(10,2) DEFAULT NULL,
  `periodo` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`idMatricula`),
  KEY `idDisciplina` (`idDisciplina`),
  KEY `idAluno` (`idAluno`),
  CONSTRAINT `matricula_ibfk_1` FOREIGN KEY (`idDisciplina`) REFERENCES `disciplina` (`codigo`),
  CONSTRAINT `matricula_ibfk_2` FOREIGN KEY (`idAluno`) REFERENCES `pessoa` (`idPessoa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `matricula`
--

LOCK TABLES `matricula` WRITE;
/*!40000 ALTER TABLE `matricula` DISABLE KEYS */;
/*!40000 ALTER TABLE `matricula` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `matriculas`
--

DROP TABLE IF EXISTS `matriculas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `matriculas` (
  `idMatricula` int(11) NOT NULL AUTO_INCREMENT,
  `idAluno` int(11) NOT NULL,
  `codigoDisciplina` int(11) NOT NULL,
  `valorPago` decimal(10,2) DEFAULT NULL,
  `periodo` varchar(20) DEFAULT NULL,
  `dataMatricula` date NOT NULL,
  PRIMARY KEY (`idMatricula`),
  KEY `fk_disciplina` (`codigoDisciplina`),
  KEY `fk_aluno` (`idAluno`),
  CONSTRAINT `fk_aluno` FOREIGN KEY (`idAluno`) REFERENCES `pessoa` (`idPessoa`),
  CONSTRAINT `fk_disciplina` FOREIGN KEY (`codigoDisciplina`) REFERENCES `disciplina` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `matriculas`
--

LOCK TABLES `matriculas` WRITE;
/*!40000 ALTER TABLE `matriculas` DISABLE KEYS */;
INSERT INTO `matriculas` VALUES (11,1,7,250.00,'1','2026-01-06'),(12,2,5,600.00,'1','2026-02-02'),(13,44,2,200.00,'2','2026-04-26');
/*!40000 ALTER TABLE `matriculas` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER trg_matricula_insert
AFTER INSERT ON matriculas
FOR EACH ROW
BEGIN
    UPDATE disciplina d
    SET d.totalMatriculas = (
        SELECT COUNT(*) FROM matriculas m WHERE m.codigoDisciplina = NEW.codigoDisciplina
    ),
    d.vagasRestantes = d.limiteAlunos - (
        SELECT COUNT(*) FROM matriculas m WHERE m.codigoDisciplina = NEW.codigoDisciplina
    )
    WHERE d.codigo = NEW.codigoDisciplina;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER trg_matricula_update
AFTER UPDATE ON matriculas
FOR EACH ROW
BEGIN
    UPDATE disciplina d
    SET d.totalMatriculas = (
        SELECT COUNT(*) FROM matriculas m WHERE m.codigoDisciplina = OLD.codigoDisciplina
    ),
    d.vagasRestantes = d.limiteAlunos - (
        SELECT COUNT(*) FROM matriculas m WHERE m.codigoDisciplina = OLD.codigoDisciplina
    )
    WHERE d.codigo = OLD.codigoDisciplina;

    UPDATE disciplina d
    SET d.totalMatriculas = (
        SELECT COUNT(*) FROM matriculas m WHERE m.codigoDisciplina = NEW.codigoDisciplina
    ),
    d.vagasRestantes = d.limiteAlunos - (
        SELECT COUNT(*) FROM matriculas m WHERE m.codigoDisciplina = NEW.codigoDisciplina
    )
    WHERE d.codigo = NEW.codigoDisciplina;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER trg_matricula_delete
AFTER DELETE ON matriculas
FOR EACH ROW
BEGIN
    UPDATE disciplina d
    SET d.totalMatriculas = (
        SELECT COUNT(*) FROM matriculas m WHERE m.codigoDisciplina = OLD.codigoDisciplina
    ),
    d.vagasRestantes = d.limiteAlunos - (
        SELECT COUNT(*) FROM matriculas m WHERE m.codigoDisciplina = OLD.codigoDisciplina
    )
    WHERE d.codigo = OLD.codigoDisciplina;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `pessoa`
--

DROP TABLE IF EXISTS `pessoa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pessoa` (
  `idPessoa` int(11) NOT NULL AUTO_INCREMENT,
  `nomePessoa` varchar(100) NOT NULL,
  `endereco` varchar(150) DEFAULT NULL,
  `uf` char(2) DEFAULT NULL,
  `telefone` varchar(20) DEFAULT NULL,
  `cpf` varchar(14) NOT NULL,
  `email` varchar(100) NOT NULL,
  `tipo` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`idPessoa`),
  UNIQUE KEY `cpf` (`cpf`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pessoa`
--

LOCK TABLES `pessoa` WRITE;
/*!40000 ALTER TABLE `pessoa` DISABLE KEYS */;
INSERT INTO `pessoa` VALUES (1,'Leandro','aas','ds','(22) 32323-2323','323.243.243-43','l@email','ALUNO'),(2,'Yane','Rua C','Ba','(71) 81818-1818','837.328.337-22','y@email.com','ALUNO'),(30,'Andre Portugal','Rua Fvc','BA','(71) 98989-8989','093.843.209-43','a@email.com','PROFESSOR'),(31,'Arleys','Rua TTT','BA','(71) 82827-2827','621.618.817-32','ay@email.com','PROFESSOR'),(32,'Afonso','Rua DDD','BA','(71) 87628-7326','907.389.473-28','af@email.com','PROFESSOR'),(33,'Aristoteles','Rua EYEU','BA','(71) 98789-7897','363.536.536-53','arsl@email.com','PROFESSOR'),(34,'Wendel','Rua R','BA','(71) 89282-7282','373.883.992-82','w@email.com','ALUNO'),(35,'Gilson','Rua WWW','BA','(71) 98762-7364','734.883.727-27','g@email.com','PROFESSOR'),(36,'Leandro Gonzalez','Rua AHAHA','BA','(71) 92837-2893','392.873.218-93','lg@email.com','PROFESSOR'),(37,'José Raimundo','Rua QQQQ','BA','(71) 93738-4767','732.823.723-82','jr@email.com','ALUNO'),(38,'Fábio','Rua BCBCB','BA','(71) 89938-2900','328.763.876-87','f@email.com','PROFESSOR'),(42,'Cauã','Rua QTR','BA','(71) 98327-4329','928.379.132-72','c@email.com','ALUNO'),(43,'Klayven','','','','321.564.845-16','','ALUNO'),(44,'Teste2','wyydgwiehbf','BA','(71) 99988-5588','465.414.688-79','teste2@email.com','ALUNO');
/*!40000 ALTER TABLE `pessoa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `idUsuario` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `senha` varchar(100) NOT NULL,
  `perfil` varchar(20) NOT NULL,
  `cargo` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `nome` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idUsuario`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'teste.1','$2a$10$suFN8JzeExmUKuQEJ63jKeBKREWQFCyD.St3QoyZhFWSK5N0hm.Km','ADMIN','Professor','teste@email.com','Teste 01');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'controle_matriculas'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-26 12:37:17
