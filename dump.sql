-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: e-learning
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `Category_id` int NOT NULL AUTO_INCREMENT,
  `Category_name` varchar(45) NOT NULL,
  `logo` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Category_id`),
  UNIQUE KEY `Category_name` (`Category_name`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (23,'Web Designing','web-design.png'),(24,'Development','web-development.png'),(25,'Health And Fitness','heart-health.png'),(26,'Photography','photograph.png'),(27,'Gov. Exam','government-exam.png'),(28,'Finance & Accounting','icons8-money-bag-50.png'),(29,'Office Productivity','icons8-department-50.png'),(30,'Music','icons8-rock-music-50.png');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courses` (
  `course_id` int NOT NULL AUTO_INCREMENT,
  `teacher_id` int DEFAULT NULL,
  `course_name` varchar(45) DEFAULT NULL,
  `course_description` varchar(5000) DEFAULT NULL,
  `course_price` decimal(10,2) DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  `course_thumbnail` varchar(45) DEFAULT NULL,
  `course_difficulty` varchar(45) DEFAULT NULL,
  `course_overview` varchar(5000) DEFAULT NULL,
  `no_of_weeks` int DEFAULT NULL,
  `avg_rating` decimal(10,1) DEFAULT '0.0',
  PRIMARY KEY (`course_id`),
  UNIQUE KEY `course_id_UNIQUE` (`course_id`),
  KEY `teacher-course_idx` (`teacher_id`),
  KEY `category-course_idx` (`category_id`),
  FULLTEXT KEY `full_text_search_idx` (`course_name`,`course_description`),
  CONSTRAINT `category-course` FOREIGN KEY (`category_id`) REFERENCES `categories` (`Category_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `teacher-course` FOREIGN KEY (`teacher_id`) REFERENCES `teachers` (`teacher_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES (1,4,'Learning C++','Beginner Friendly Course.',100.00,NULL,23,'cr-1.jpg','Intermediate','Hello',7,3.3),(4,5,'Beginner To Advance Java Course','<div><br></div>',0.00,'2021-11-01 16:33:31',23,'cr-1.jpg','Beginner','<div><br></div>',10,0.0),(5,4,'Master Dynamic Programming','<div><br></div>',0.00,'2021-11-01 16:34:37',24,'cr-2.jpg','Beginner','<div><br></div>',8,0.0),(6,4,'Master Web Development','<div><br></div>',0.00,'2021-11-01 16:35:03',24,'cr-5.jpg','Beginner','<div><br></div>',8,0.0),(7,4,'Master Web Development','<div>Nice course</div>',0.00,'2021-11-01 20:05:14',24,'cr-1.jpg','Beginner','<div>You will learn everything.</div>',8,0.0),(8,4,'Master Web Development','<div><br></div>',0.00,'2021-11-01 20:19:38',24,'cr-1.jpg','Beginner','<div><br></div>',11,4.5),(9,4,'Master Web Development','<div><br></div>',0.00,'2021-11-02 15:16:47',24,'cr-2.jpg','Beginner','<div><br></div>',9,0.0),(10,4,'Master Web Development','<div><br></div>',0.00,'2021-11-02 15:16:53',24,'cr-3.jpg','Beginner','<div><br></div>',10,0.0),(11,4,'Master Web Development','<div><br></div>',0.00,'2021-11-02 15:16:57',24,'cr-4.jpg','Beginner','<div><br></div>',10,0.0),(12,4,'Master Web Development','<div><br></div>',0.00,'2021-11-02 15:17:01',24,'cr-6.jpg','Beginner','<div><br></div>',9,0.0),(13,4,'Master Web Development','<div><br></div>',0.00,'2021-11-02 15:17:06',24,'cr-5.jpg','Beginner','<div><br></div>',9,0.0),(14,4,'Competitive Programming','<div><br></div>',0.00,'2021-11-02 15:17:10',24,'cr-1.jpg','Beginner','<div><br></div>',5,0.0),(15,4,'Beginner Music Theory','<span style=\"color: rgb(60, 72, 82); font-family: Muli, sans-serif; font-size: 15px;\">Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore. veritatis et quasi architecto beatae vitae dicta sunt explicabo.&nbsp;</span><br>',100.00,'2021-11-05 14:34:08',30,'cr-3.jpg','Beginner','<div>At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio.</div>\r\nSed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt.<br><br><strong><strong>Requirements<br></strong></strong>\r\n<ul class=\"lists-3\">\r\n<li>At vero eos et accusamus et iusto odio dignissimos ducimus</li>\r\n<li>At vero eos et accusamus et iusto odio dignissimos ducimus</li>\r\n<li>At vero eos et accusamus et iusto odio dignissimos ducimus</li>\r\n<li>At vero eos et accusamus et iusto odio dignissimos ducimus</li>\r\n<li>At vero eos et accusamus et iusto odio dignissimos ducimus</li>\r\n</ul>',3,0.0),(16,5,'IAS crash course for Beginners','<div>A small crash course for IAS aspirants. Through this course we will try to cover the important topics of IAS examination. If you want to be a great IAS officer, start your journey here!!</div>',199.00,'2021-11-06 22:01:53',26,'cr-2.jpg','Beginner','<div><div>At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio.</div><div>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt.</div></div><div><br></div><div>Requirements</div><div><br></div><div>1. Well versed in english</div><div>2. Good will to learn</div><div><br></div><div><br></div>',4,3.0),(18,4,'Manage your Finance and bills','<div>Random text</div>',100.00,'2021-11-15 21:29:51',28,NULL,'Intermediate','<div>Random text</div>',7,0.0),(19,4,'Hello','<div><br></div>',100.00,'2021-11-15 21:34:06',24,NULL,'Intermediate','<div><br></div>',4,0.0),(20,4,'hello1','<div><br></div>',100.00,'2021-11-15 21:36:45',26,'','Intermediate','<div><br></div>',5,0.0),(21,4,'Manage your heath','<div>random text</div>',100.00,'2021-11-15 22:17:41',24,'cr-3.jpg','Intermediate','<div>random</div>',4,0.0);
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enrollments`
--

DROP TABLE IF EXISTS `enrollments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `enrollments` (
  `enrollment_id` int NOT NULL AUTO_INCREMENT,
  `student_id` int NOT NULL,
  `course_id` int NOT NULL,
  `enrollment_time` datetime DEFAULT NULL,
  PRIMARY KEY (`enrollment_id`),
  KEY `student-enrollment_idx` (`student_id`),
  KEY `course-enrollment_idx` (`course_id`),
  CONSTRAINT `course-enrollment` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `student-enrollment` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enrollments`
--

LOCK TABLES `enrollments` WRITE;
/*!40000 ALTER TABLE `enrollments` DISABLE KEYS */;
INSERT INTO `enrollments` VALUES (19,38,1,'2021-11-07 22:53:04'),(20,38,15,'2021-11-08 00:26:14'),(21,40,1,'2021-11-09 20:46:51'),(22,40,16,'2021-11-09 20:50:36'),(23,40,4,'2021-11-15 09:51:47'),(24,40,5,'2021-11-15 09:51:56'),(25,40,6,'2021-11-15 09:52:04'),(26,40,12,'2021-11-15 09:52:11'),(27,38,13,'2021-11-15 17:36:00'),(28,43,1,'2021-11-15 21:22:00'),(29,44,1,'2021-11-15 22:13:40'),(30,38,21,'2021-11-15 22:21:18'),(31,38,9,'2021-11-21 20:45:45'),(32,47,8,'2021-11-22 12:30:42');
/*!40000 ALTER TABLE `enrollments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedbacks`
--

DROP TABLE IF EXISTS `feedbacks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedbacks` (
  `feedback_id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(2000) DEFAULT NULL,
  `student_id` int NOT NULL,
  `course_id` int NOT NULL,
  `feedback_timestamp` datetime DEFAULT NULL,
  `star` int DEFAULT NULL,
  PRIMARY KEY (`feedback_id`),
  UNIQUE KEY `feedback_id_UNIQUE` (`feedback_id`),
  KEY `course-feedback_idx` (`course_id`),
  KEY `student-feedback_idx` (`student_id`),
  CONSTRAINT `course-feedback` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `student-feedback` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedbacks`
--

LOCK TABLES `feedbacks` WRITE;
/*!40000 ALTER TABLE `feedbacks` DISABLE KEYS */;
INSERT INTO `feedbacks` VALUES (8,'good one.',38,1,'2021-11-09 15:23:14',3),(9,'Good course',38,16,'2021-11-09 20:22:25',4),(10,'nice course',40,1,'2021-11-09 20:48:22',4),(11,'something can be done good!',40,16,'2021-11-09 20:52:02',2),(12,'Very good course',43,1,'2021-11-15 21:21:25',5),(13,'poor course',44,1,'2021-11-15 22:13:28',1),(17,'nice course',47,8,'2021-11-22 12:42:30',4),(18,'nice one',38,8,'2021-11-22 12:43:14',5);
/*!40000 ALTER TABLE `feedbacks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persistent_logins`
--

DROP TABLE IF EXISTS `persistent_logins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persistent_logins`
--

LOCK TABLES `persistent_logins` WRITE;
/*!40000 ALTER TABLE `persistent_logins` DISABLE KEYS */;
/*!40000 ALTER TABLE `persistent_logins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questions` (
  `question_id` int NOT NULL AUTO_INCREMENT,
  `question_text` varchar(2000) DEFAULT NULL,
  `option_one` varchar(100) DEFAULT NULL,
  `option_two` varchar(100) DEFAULT NULL,
  `option_three` varchar(100) DEFAULT NULL,
  `option_four` varchar(100) DEFAULT NULL,
  `answer` varchar(100) DEFAULT NULL,
  `quiz_id` int DEFAULT NULL,
  PRIMARY KEY (`question_id`),
  KEY `quiz-question_idx` (`quiz_id`),
  CONSTRAINT `quiz-question` FOREIGN KEY (`quiz_id`) REFERENCES `quizzes` (`quiz_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (6,'<div><span style=\"font-size:18px;\">What is your name?</span></div>','Myname','Yourname','Mymyname','namemy','Myname',2),(7,'<div>new question</div>','wrong1','wrong2','wrong3','correct','correct',6),(8,'<div>What is sun</div>','star','moon','satellite','natural satellite','star',7);
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz_attempts`
--

DROP TABLE IF EXISTS `quiz_attempts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz_attempts` (
  `attempt_id` int NOT NULL AUTO_INCREMENT,
  `student_id` int DEFAULT NULL,
  `quiz_id` int DEFAULT NULL,
  `marks_got` int DEFAULT NULL,
  `total_marks` int DEFAULT NULL,
  PRIMARY KEY (`attempt_id`),
  KEY `student-attempt_idx` (`student_id`),
  KEY `quiz-attempt_idx` (`quiz_id`),
  CONSTRAINT `quiz-attempt` FOREIGN KEY (`quiz_id`) REFERENCES `quizzes` (`quiz_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `student-attempt` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz_attempts`
--

LOCK TABLES `quiz_attempts` WRITE;
/*!40000 ALTER TABLE `quiz_attempts` DISABLE KEYS */;
INSERT INTO `quiz_attempts` VALUES (8,38,2,1,1),(9,38,6,1,1),(10,43,6,1,1),(11,44,6,1,1),(12,38,7,1,1);
/*!40000 ALTER TABLE `quiz_attempts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quizzes`
--

DROP TABLE IF EXISTS `quizzes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quizzes` (
  `quiz_id` int NOT NULL AUTO_INCREMENT,
  `quiz_title` varchar(45) DEFAULT NULL,
  `quiz_description` varchar(1000) DEFAULT NULL,
  `quiz_instructions` varchar(2000) DEFAULT NULL,
  `week` int DEFAULT NULL,
  `course_id` int DEFAULT NULL,
  `quiz_time` int DEFAULT NULL,
  PRIMARY KEY (`quiz_id`),
  KEY `quiz-course_idx` (`course_id`),
  CONSTRAINT `quiz-course` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quizzes`
--

LOCK TABLES `quizzes` WRITE;
/*!40000 ALTER TABLE `quizzes` DISABLE KEYS */;
INSERT INTO `quizzes` VALUES (2,'new Test','new description','new instructions',1,1,130),(3,'First Quiz','Test purpose only.','Don\'t submit it.',3,1,30),(4,'Quiz2','<div>this quiz is very interesting.</div>','<div>Read all the questions carefully.</div>',1,1,10),(5,'Quiz3','<div>Kuch bhi</div>','<div>djfdff</div>',2,1,13),(6,'New Quiz','<div>Random text</div>','<div>Random text</div>',2,1,NULL),(7,'First Quiz','<div>Beginner quiz</div>','<div>Please read all questionss</div>',1,21,NULL);
/*!40000 ALTER TABLE `quizzes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students` (
  `student_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `date_of_birth` date DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `country` varchar(45) DEFAULT NULL,
  `pincode` varchar(10) DEFAULT NULL,
  `street` varchar(45) DEFAULT NULL,
  `house_no` varchar(45) DEFAULT NULL,
  `profile_pic` varchar(45) DEFAULT 'default.png',
  `state` varchar(45) DEFAULT NULL,
  `phone_no` varchar(15) DEFAULT NULL,
  `no_of_photos_uploaded` int DEFAULT '0',
  PRIMARY KEY (`student_id`),
  UNIQUE KEY `student_id_UNIQUE` (`student_id`),
  KEY `user_id_idx` (`user_id`),
  CONSTRAINT `student_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` VALUES (38,62,'2001-12-03','','chanderi','India','473446','Near Mela Ground Road, Chanderi','','38_3_p1.png','Madhya Pradesh',NULL,3),(40,66,NULL,'','','','','','','40_1_tech.jfif','',NULL,1),(41,68,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'default.png',NULL,NULL,0),(42,69,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'default.png',NULL,NULL,0),(43,70,'2021-11-03','Male','Varanasi','','','','','43_1_p1.png','',NULL,1),(44,71,'2021-11-03','','','India','','','','44_1_p1.png','',NULL,0),(47,73,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0);
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teachers`
--

DROP TABLE IF EXISTS `teachers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teachers` (
  `teacher_id` int NOT NULL AUTO_INCREMENT,
  `gender` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `country` varchar(45) DEFAULT NULL,
  `street` varchar(100) DEFAULT NULL,
  `house_no` varchar(10) DEFAULT NULL,
  `about_me` varchar(300) DEFAULT NULL,
  `pincode` varchar(10) DEFAULT NULL,
  `user_id` int NOT NULL,
  `profile_pic` varchar(45) DEFAULT 'default.png',
  `no_of_photos_uploaded` int DEFAULT '0',
  `state` varchar(45) DEFAULT NULL,
  `teaching_experience` int DEFAULT '0',
  PRIMARY KEY (`teacher_id`),
  UNIQUE KEY `teacher_id_UNIQUE` (`teacher_id`),
  KEY `teacher_user_idx` (`user_id`),
  CONSTRAINT `teacher_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teachers`
--

LOCK TABLES `teachers` WRITE;
/*!40000 ALTER TABLE `teachers` DISABLE KEYS */;
INSERT INTO `teachers` VALUES (4,'Male','chanderi','India','Near Mela Ground Road, Chanderi,','','Enthusiastic in training students to achieve better goals in life','473446',63,'4_2_p1.png',0,'Madhya Pradesh',3),(5,'','Indore','India','Near market. ','','I am a geek enthusiast and a competitve programmer.','473448',64,'5_3_p1.png',3,'Madhya Pradesh',1),(6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,65,'default.png',0,NULL,2),(8,NULL,NULL,NULL,NULL,NULL,NULL,NULL,74,'default.png',0,NULL,0);
/*!40000 ALTER TABLE `teachers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `topics`
--

DROP TABLE IF EXISTS `topics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `topics` (
  `topic_id` int NOT NULL AUTO_INCREMENT,
  `topic_title` varchar(45) DEFAULT NULL,
  `topic_lecture` varchar(45) DEFAULT NULL,
  `topic_notes` varchar(10000) DEFAULT NULL,
  `topic_number` varchar(45) DEFAULT NULL,
  `course_id` int NOT NULL,
  `week` int DEFAULT NULL,
  PRIMARY KEY (`topic_id`),
  UNIQUE KEY `topic_id_UNIQUE` (`topic_id`),
  KEY `course-topic_idx` (`course_id`),
  CONSTRAINT `course-topic` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topics`
--

LOCK TABLES `topics` WRITE;
/*!40000 ALTER TABLE `topics` DISABLE KEYS */;
INSERT INTO `topics` VALUES (9,'Introduction','video1.mp4','<h2 style=\"box-sizing: inherit; -webkit-font-smoothing: antialiased; font-family: &quot;Source Sans Pro&quot;, Arial, sans-serif; font-size: 1.25rem; line-height: 1.75rem; color: rgb(31, 31, 31); margin-right: 0px; margin-bottom: 16px; margin-left: 0px; padding: 0px; max-width: 100%; letter-spacing: -0.1px; white-space: pre-wrap;\">What is Machine Learning?</h2><div>Two definitions of Machine Learning are offered. Arthur Samuel described it as: \"the field of study that gives computers the ability to learn without being explicitly programmed.\" This is an older, informal definition.</div><div>Tom Mitchell provides a more modern definition: \"A computer program is said to learn from experience E with respect to some class of tasks T and performance measure P, if its performance at tasks in T, as measured by P, improves with experience E.\"</div><div>Example: playing checkers.</div><div>E = the experience of playing many games of checkers</div><div>T = the task of playing checkers.</div><div>P = the probability that the program will win the next game.</div><div>In general, any machine learning problem can be assigned to one of two broad classifications:</div><div>Supervised learning and Unsupervised learning.</div>','1',1,1),(10,'Basic Syntax','video1.mp4','<h2 style=\"box-sizing: inherit; -webkit-font-smoothing: antialiased; font-family: &quot;Source Sans Pro&quot;, Arial, sans-serif; font-size: 1.25rem; line-height: 1.75rem; color: rgb(31, 31, 31); margin-right: 0px; margin-bottom: 16px; margin-left: 0px; padding: 0px; max-width: 100%; letter-spacing: -0.1px; white-space: pre-wrap;\">What is Machine Learning?</h2><div>Two definitions of Machine Learning are offered. Arthur Samuel described it as: \"the field of study that gives computers the ability to learn without being explicitly programmed.\" This is an older, informal definition.</div><div>Tom Mitchell provides a more modern definition: \"A computer program is said to learn from experience E with respect to some class of tasks T and performance measure P, if its performance at tasks in T, as measured by P, improves with experience E.\"</div><div>Example: playing checkers.</div><div>E = the experience of playing many games of checkers</div><div>T = the task of playing checkers.</div><div>P = the probability that the program will win the next game.</div><div>In general, any machine learning problem can be assigned to one of two broad classifications:</div><div>Supervised learning and Unsupervised learning.</div>','2',1,1),(11,'Introduction','video1.mp4','<h2 style=\"box-sizing: inherit; -webkit-font-smoothing: antialiased; font-family: &quot;Source Sans Pro&quot;, Arial, sans-serif; font-size: 1.25rem; line-height: 1.75rem; color: rgb(31, 31, 31); margin-right: 0px; margin-bottom: 16px; margin-left: 0px; padding: 0px; max-width: 100%; letter-spacing: -0.1px; white-space: pre-wrap;\">What is Machine Learning?</h2><div>Two definitions of Machine Learning are offered. Arthur Samuel described it as: \"the field of study that gives computers the ability to learn without being explicitly programmed.\" This is an older, informal definition.</div><div>Tom Mitchell provides a more modern definition: \"A computer program is said to learn from experience E with respect to some class of tasks T and performance measure P, if its performance at tasks in T, as measured by P, improves with experience E.\"</div><div>Example: playing checkers.</div><div>E = the experience of playing many games of checkers</div><div>T = the task of playing checkers.</div><div>P = the probability that the program will win the next game.</div><div>In general, any machine learning problem can be assigned to one of two broad classifications:</div><div>Supervised learning and Unsupervised learning.</div>','1',6,1),(12,'Functions','video1.mp4','<h2 style=\"box-sizing: inherit; -webkit-font-smoothing: antialiased; font-family: &quot;Source Sans Pro&quot;, Arial, sans-serif; font-size: 1.25rem; line-height: 1.75rem; color: rgb(31, 31, 31); margin-right: 0px; margin-bottom: 16px; margin-left: 0px; padding: 0px; max-width: 100%; letter-spacing: -0.1px; white-space: pre-wrap;\">What is Machine Learning?</h2><div>Two definitions of Machine Learning are offered. Arthur Samuel described it as: \"the field of study that gives computers the ability to learn without being explicitly programmed.\" This is an older, informal definition.</div><div>Tom Mitchell provides a more modern definition: \"A computer program is said to learn from experience E with respect to some class of tasks T and performance measure P, if its performance at tasks in T, as measured by P, improves with experience E.\"</div><div>Example: playing checkers.</div><div>E = the experience of playing many games of checkers</div><div>T = the task of playing checkers.</div><div>P = the probability that the program will win the next game.</div><div>In general, any machine learning problem can be assigned to one of two broad classifications:</div><div>Supervised learning and Unsupervised learning.</div>','1',1,2),(13,'Recursion','video2.mp4','<h2 style=\"box-sizing: inherit; -webkit-font-smoothing: antialiased; font-family: &quot;Source Sans Pro&quot;, Arial, sans-serif; font-size: 1.25rem; line-height: 1.75rem; color: rgb(31, 31, 31); margin-right: 0px; margin-bottom: 16px; margin-left: 0px; padding: 0px; max-width: 100%; letter-spacing: -0.1px; white-space: pre-wrap;\">What is Machine Learning?</h2><div>Two definitions of Machine Learning are offered. Arthur Samuel described it as: \"the field of study that gives computers the ability to learn without being explicitly programmed.\" This is an older, informal definition.</div><div>Tom Mitchell provides a more modern definition: \"A computer program is said to learn from experience E with respect to some class of tasks T and performance measure P, if its performance at tasks in T, as measured by P, improves with experience E.\"</div><div>Example: playing checkers.</div><div>E = the experience of playing many games of checkers</div><div>T = the task of playing checkers.</div><div>P = the probability that the program will win the next game.</div><div>In general, any machine learning problem can be assigned to one of two broad classifications:</div><div>Supervised learning and Unsupervised learning.</div>','2',1,2),(14,'Object Oriented Programming','video2.mp4','<h2 style=\"box-sizing: inherit; -webkit-font-smoothing: antialiased; font-family: &quot;Source Sans Pro&quot;, Arial, sans-serif; font-size: 1.25rem; line-height: 1.75rem; color: rgb(31, 31, 31); margin-right: 0px; margin-bottom: 16px; margin-left: 0px; padding: 0px; max-width: 100%; letter-spacing: -0.1px; white-space: pre-wrap;\">What is Machine Learning?</h2><div>Two definitions of Machine Learning are offered. Arthur Samuel described it as: \"the field of study that gives computers the ability to learn without being explicitly programmed.\" This is an older, informal definition.</div><div>Tom Mitchell provides a more modern definition: \"A computer program is said to learn from experience E with respect to some class of tasks T and performance measure P, if its performance at tasks in T, as measured by P, improves with experience E.\"</div><div>Example: playing checkers.</div><div>E = the experience of playing many games of checkers</div><div>T = the task of playing checkers.</div><div>P = the probability that the program will win the next game.</div><div>In general, any machine learning problem can be assigned to one of two broad classifications:</div><div>Supervised learning and Unsupervised learning.</div>','1',1,3),(15,'Virtual Function','video1.mp4','<h2 style=\"box-sizing: inherit; -webkit-font-smoothing: antialiased; font-family: &quot;Source Sans Pro&quot;, Arial, sans-serif; font-size: 1.25rem; line-height: 1.75rem; color: rgb(31, 31, 31); margin-right: 0px; margin-bottom: 16px; margin-left: 0px; padding: 0px; max-width: 100%; letter-spacing: -0.1px; white-space: pre-wrap;\">What is Machine Learning?</h2><div>Two definitions of Machine Learning are offered. Arthur Samuel described it as: \"the field of study that gives computers the ability to learn without being explicitly programmed.\" This is an older, informal definition.</div><div>Tom Mitchell provides a more modern definition: \"A computer program is said to learn from experience E with respect to some class of tasks T and performance measure P, if its performance at tasks in T, as measured by P, improves with experience E.\"</div><div>Example: playing checkers.</div><div>E = the experience of playing many games of checkers</div><div>T = the task of playing checkers.</div><div>P = the probability that the program will win the next game.</div><div>In general, any machine learning problem can be assigned to one of two broad classifications:</div><div>Supervised learning and Unsupervised learning.</div>','2',1,3),(16,'History Basics','video2.mp4','<h2 style=\"box-sizing: inherit; -webkit-font-smoothing: antialiased; font-family: &quot;Source Sans Pro&quot;, Arial, sans-serif; font-size: 1.25rem; line-height: 1.75rem; color: rgb(31, 31, 31); margin-right: 0px; margin-bottom: 16px; margin-left: 0px; padding: 0px; max-width: 100%; letter-spacing: -0.1px; white-space: pre-wrap;\">What is Machine Learning?</h2><div>Two definitions of Machine Learning are offered. Arthur Samuel described it as: \"the field of study that gives computers the ability to learn without being explicitly programmed.\" This is an older, informal definition.</div><div>Tom Mitchell provides a more modern definition: \"A computer program is said to learn from experience E with respect to some class of tasks T and performance measure P, if its performance at tasks in T, as measured by P, improves with experience E.\"</div><div>Example: playing checkers.</div><div>E = the experience of playing many games of checkers</div><div>T = the task of playing checkers.</div><div>P = the probability that the program will win the next game.</div><div>In general, any machine learning problem can be assigned to one of two broad classifications:</div><div>Supervised learning and Unsupervised learning.</div>','1',16,1),(17,'Control flow statements','file_example_MP4_480_1_5MG.mp4','<h2 style=\"box-sizing: inherit; -webkit-font-smoothing: antialiased; font-family: &quot;Source Sans Pro&quot;, Arial, sans-serif; font-size: 1.25rem; line-height: 1.75rem; color: rgb(31, 31, 31); margin-right: 0px; margin-bottom: 16px; margin-left: 0px; padding: 0px; max-width: 100%; letter-spacing: -0.1px; white-space: pre-wrap;\">What is Machine Learning?</h2><div>Two definitions of Machine Learning are offered. Arthur Samuel described it as: \"the field of study that gives computers the ability to learn without being explicitly programmed.\" This is an older, informal definition.</div><div>Tom Mitchell provides a more modern definition: \"A computer program is said to learn from experience E with respect to some class of tasks T and performance measure P, if its performance at tasks in T, as measured by P, improves with experience E.\"</div><div>Example: playing checkers.</div><div>E = the experience of playing many games of checkers</div><div>T = the task of playing checkers.</div><div>P = the probability that the program will win the next game.</div><div>In general, any machine learning problem can be assigned to one of two broad classifications:</div><div>Supervised learning and Unsupervised learning.</div>','3',1,1),(18,'Lambda expressions','ssc.mkv','<h2 style=\"box-sizing: inherit; -webkit-font-smoothing: antialiased; font-family: &quot;Source Sans Pro&quot;, Arial, sans-serif; font-size: 1.25rem; line-height: 1.75rem; color: rgb(31, 31, 31); margin-right: 0px; margin-bottom: 16px; margin-left: 0px; padding: 0px; max-width: 100%; letter-spacing: -0.1px; white-space: pre-wrap;\">What is Machine Learning?</h2><div>Two definitions of Machine Learning are offered. Arthur Samuel described it as: \"the field of study that gives computers the ability to learn without being explicitly programmed.\" This is an older, informal definition.</div><div>Tom Mitchell provides a more modern definition: \"A computer program is said to learn from experience E with respect to some class of tasks T and performance measure P, if its performance at tasks in T, as measured by P, improves with experience E.\"</div><div>Example: playing checkers.</div><div>E = the experience of playing many games of checkers</div><div>T = the task of playing checkers.</div><div>P = the probability that the program will win the next game.</div><div>In general, any machine learning problem can be assigned to one of two broad classifications:</div><div>Supervised learning and Unsupervised learning.</div>','3',1,3),(19,'Structure And Classes in c++','ssc.mkv','<h2 style=\"box-sizing: inherit; -webkit-font-smoothing: antialiased; font-family: \" source=\"\" sans=\"\" pro\",=\"\" arial,=\"\" sans-serif;=\"\" font-size:=\"\" 1.25rem;=\"\" line-height:=\"\" 1.75rem;=\"\" color:=\"\" rgb(31,=\"\" 31,=\"\" 31);=\"\" margin-right:=\"\" 0px;=\"\" margin-bottom:=\"\" 16px;=\"\" margin-left:=\"\" padding:=\"\" max-width:=\"\" 100%;=\"\" letter-spacing:=\"\" -0.1px;=\"\" white-space:=\"\" pre-wrap;\"=\"\">What is Machine Learning?</h2><div>Two definitions of Machine Learning are offered. Arthur Samuel described it as: \"the field of study that gives computers the ability to learn without being explicitly programmed.\" This is an older, informal definition.</div><div><br></div><div>Tom Mitchell provides a more modern definition: \"A computer program is said to learn from experience E with respect to some class of tasks T and performance measure P, if its performance at tasks in T, as measured by P, improves with experience E.\"</div><div><br></div><div>Example: playing checkers.</div><div><br></div><div>E = the experience of playing many games of checkers</div><div><br></div><div>T = the task of playing checkers.</div><div><br></div><div>P = the probability that the program will win the next game.</div><div><br></div><div>In general, any machine learning problem can be assigned to one of two broad classifications:</div><div>Supervised learning and Unsupervised learning.</div>','4',1,3),(20,'Hello world Example','ssc.mkv','<div><div class=\"rc-CDSToCMLStylesheet css-752t57\" style=\"box-sizing: inherit; -webkit-font-smoothing: antialiased; overflow-wrap: break-word; color: rgb(31, 31, 31); font-family: OpenSans, -apple-system, BlinkMacSystemFont, \" segoe=\"\" ui\",=\"\" roboto,=\"\" \"helvetica=\"\" neue\",=\"\" arial,=\"\" sans-serif;\"=\"\"><div id=\"\" class=\"rc-RenderableHtml rc-CML show-soft-breaks css-z95mbv\" dir=\"auto\" style=\"box-sizing: inherit; -webkit-font-smoothing: antialiased; overflow-wrap: break-word; width: 946px; white-space: pre-wrap;\"><div style=\"box-sizing: inherit; -webkit-font-smoothing: antialiased; margin-bottom: 0px !important;\"><div data-track=\"true\" data-track-app=\"open_course_home\" data-track-page=\"item_layout\" data-track-action=\"click\" data-track-component=\"cml\" role=\"presentation\" style=\"box-sizing: inherit; -webkit-font-smoothing: antialiased; margin-bottom: 0px !important;\"><div data-track=\"true\" data-track-app=\"open_course_home\" data-track-page=\"item_layout\" data-track-action=\"click\" data-track-component=\"cml_link\" style=\"box-sizing: inherit; -webkit-font-smoothing: antialiased;\"><div style=\"box-sizing: inherit; -webkit-font-smoothing: antialiased;\"><div class=\"cmlToHtml-content-container\" style=\"box-sizing: inherit; -webkit-font-smoothing: antialiased; width: 946px;\"><h2 style=\"box-sizing: inherit; -webkit-font-smoothing: antialiased; font-family: \" source=\"\" sans=\"\" pro\",=\"\" arial,=\"\" sans-serif;=\"\" font-size:=\"\" 1.25rem;=\"\" line-height:=\"\" 1.75rem;=\"\" margin-right:=\"\" 0px;=\"\" margin-bottom:=\"\" 16px;=\"\" margin-left:=\"\" padding:=\"\" max-width:=\"\" 100%;=\"\" letter-spacing:=\"\" -0.1px;\"=\"\">What is Machine Learning?</h2><div>Two definitions of Machine Learning are offered. Arthur Samuel described it as: \"the field of study that gives computers the ability to learn without being explicitly programmed.\" This is an older, informal definition.</div><div><br></div><div>Tom Mitchell provides a more modern definition: \"A computer program is said to learn from experience E with respect to some class of tasks T and performance measure P, if its performance at tasks in T, as measured by P, improves with experience E.\"</div><div><br></div><div>Example: playing checkers.</div><div><br></div><div>E = the experience of playing many games of checkers</div><div><br></div><div>T = the task of playing checkers.</div><div><br></div><div>P = the probability that the program will win the next game.</div><div><br></div><div>In general, any machine learning problem can be assigned to one of two broad classifications:</div><div>Supervised learning and Unsupervised learning.</div><div><br></div></div></div></div></div></div></div></div><div class=\"rc-ReadingCompleteButton horizontal-box\" style=\"box-sizing: inherit; -webkit-font-smoothing: antialiased; display: flex; flex-direction: row; margin-top: 36px; color: rgb(31, 31, 31); font-family: OpenSans, -apple-system, BlinkMacSystemFont, \" segoe=\"\" ui\",=\"\" roboto,=\"\" \"helvetica=\"\" neue\",=\"\" arial,=\"\" sans-serif;\"=\"\"><div class=\"completed css-2o6v2i\" style=\"box-sizing: inherit; -webkit-font-smoothing: antialiased; display: flex; -webkit-box-align: center; align-items: center;\"></div></div></div>','3',1,2),(21,'Hello world ','ssc.mkv','<div><div class=\"rc-CDSToCMLStylesheet css-752t57\" style=\"box-sizing: inherit; -webkit-font-smoothing: antialiased; overflow-wrap: break-word; color: rgb(31, 31, 31); font-family: OpenSans, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Roboto, &quot;Helvetica Neue&quot;, Arial, sans-serif;\"><div id=\"\" class=\"rc-RenderableHtml rc-CML show-soft-breaks css-z95mbv\" dir=\"auto\" style=\"box-sizing: inherit; -webkit-font-smoothing: antialiased; overflow-wrap: break-word; width: 946px; white-space: pre-wrap;\"><div style=\"box-sizing: inherit; -webkit-font-smoothing: antialiased; margin-bottom: 0px !important;\"><div data-track=\"true\" data-track-app=\"open_course_home\" data-track-page=\"item_layout\" data-track-action=\"click\" data-track-component=\"cml\" role=\"presentation\" style=\"box-sizing: inherit; -webkit-font-smoothing: antialiased; margin-bottom: 0px !important;\"><div data-track=\"true\" data-track-app=\"open_course_home\" data-track-page=\"item_layout\" data-track-action=\"click\" data-track-component=\"cml_link\" style=\"box-sizing: inherit; -webkit-font-smoothing: antialiased;\"><div style=\"box-sizing: inherit; -webkit-font-smoothing: antialiased;\"><div class=\"cmlToHtml-content-container\" style=\"box-sizing: inherit; -webkit-font-smoothing: antialiased; width: 946px;\"><h2 style=\"box-sizing: inherit; -webkit-font-smoothing: antialiased; font-family: &quot;Source Sans Pro&quot;, Arial, sans-serif; font-size: 1.25rem; line-height: 1.75rem; margin-right: 0px; margin-bottom: 16px; margin-left: 0px; padding: 0px; max-width: 100%; letter-spacing: -0.1px;\">What is Machine Learning?</h2><div>Two definitions of Machine Learning are offered. Arthur Samuel described it as: \"the field of study that gives computers the ability to learn without being explicitly programmed.\" This is an older, informal definition.</div><div><br></div><div>Tom Mitchell provides a more modern definition: \"A computer program is said to learn from experience E with respect to some class of tasks T and performance measure P, if its performance at tasks in T, as measured by P, improves with experience E.\"</div><div><br></div><div>Example: playing checkers.</div><div><br></div><div>E = the experience of playing many games of checkers</div><div><br></div><div>T = the task of playing checkers.</div><div><br></div><div>P = the probability that the program will win the next game.</div><div><br></div><div>In general, any machine learning problem can be assigned to one of two broad classifications:</div><div>Supervised learning and Unsupervised learning.</div><div><br></div></div></div></div></div></div></div></div><div class=\"rc-ReadingCompleteButton horizontal-box\" style=\"box-sizing: inherit; -webkit-font-smoothing: antialiased; display: flex; flex-direction: row; margin-top: 36px; color: rgb(31, 31, 31); font-family: OpenSans, -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Roboto, &quot;Helvetica Neue&quot;, Arial, sans-serif;\"><div class=\"completed css-2o6v2i\" style=\"box-sizing: inherit; -webkit-font-smoothing: antialiased; display: flex; -webkit-box-align: center; align-items: center;\"></div></div></div>','4',1,2),(22,'Structure And Classes',NULL,'<h2 style=\"box-sizing: inherit; -webkit-font-smoothing: antialiased; font-family: \" source=\"\" sans=\"\" pro\",=\"\" arial,=\"\" sans-serif;=\"\" font-size:=\"\" 1.25rem;=\"\" line-height:=\"\" 1.75rem;=\"\" color:=\"\" rgb(31,=\"\" 31,=\"\" 31);=\"\" margin-right:=\"\" 0px;=\"\" margin-bottom:=\"\" 16px;=\"\" margin-left:=\"\" padding:=\"\" max-width:=\"\" 100%;=\"\" letter-spacing:=\"\" -0.1px;=\"\" white-space:=\"\" pre-wrap;\"=\"\">What is Machine Learning?</h2><div>Two definitions of Machine Learning are offered. Arthur Samuel described it as: \"the field of study that gives computers the ability to learn without being explicitly programmed.\" This is an older, informal definition.</div><div>Tom Mitchell provides a more modern definition: \"A computer program is said to learn from experience E with respect to some class of tasks T and performance measure P, if its performance at tasks in T, as measured by P, improves with experience E.\"</div><div>Example: playing checkers.</div><div>E = the experience of playing many games of checkers</div><div>T = the task of playing checkers.</div><div>P = the probability that the program will win the next game.</div><div>In general, any machine learning problem can be assigned to one of two broad classifications:</div><div>Supervised learning and Unsupervised learning.</div>','4',1,0),(23,'Structure And Classes',NULL,'<h2 style=\"box-sizing: inherit; -webkit-font-smoothing: antialiased; font-family: \" source=\"\" sans=\"\" pro\",=\"\" arial,=\"\" sans-serif;=\"\" font-size:=\"\" 1.25rem;=\"\" line-height:=\"\" 1.75rem;=\"\" color:=\"\" rgb(31,=\"\" 31,=\"\" 31);=\"\" margin-right:=\"\" 0px;=\"\" margin-bottom:=\"\" 16px;=\"\" margin-left:=\"\" padding:=\"\" max-width:=\"\" 100%;=\"\" letter-spacing:=\"\" -0.1px;=\"\" white-space:=\"\" pre-wrap;\"=\"\">What is Machine Learning?</h2><div>Two definitions of Machine Learning are offered. Arthur Samuel described it as: \"the field of study that gives computers the ability to learn without being explicitly programmed.\" This is an older, informal definition.</div><div>Tom Mitchell provides a more modern definition: \"A computer program is said to learn from experience E with respect to some class of tasks T and performance measure P, if its performance at tasks in T, as measured by P, improves with experience E.\"</div><div>Example: playing checkers.</div><div>E = the experience of playing many games of checkers</div><div>T = the task of playing checkers.</div><div>P = the probability that the program will win the next game.</div><div>In general, any machine learning problem can be assigned to one of two broad classifications:</div><div>Supervised learning and Unsupervised learning.</div>','4',1,0),(24,'Hello world ','video1.mp4','<div>Notes</div>','1',21,1);
/*!40000 ALTER TABLE `topics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(64) NOT NULL,
  `role` varchar(45) NOT NULL,
  `enabled` tinyint DEFAULT '1',
  `email_id` varchar(45) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_id_UNIQUE` (`email_id`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (62,'akshat','$2a$10$Ciq3YJyDh3sGJVVQ3tpTkO82CyZAbOlMDMyGvonRFmkltiWrWsU6G','ROLE_STUDENT',1,'akshatjai258@gmail.com','Akshat','Jain'),(63,'wick','$2a$10$lfPvnPAmRibQDC2pk.T6HuUJj7cH3T87FAYtIwBhnkkH.eUja7jpq','ROLE_TEACHER',1,'johnwick@gmail.com','John','S Wick'),(64,'codemaster','$2a$10$rTBUAvpYikIuaJRBTun4iey1gmf4ePZQuScKDRCzx0sxGAiPQXjOK','ROLE_TEACHER',1,'codemaster@cpp.com','Code','Master'),(65,'rajkumar','$2a$10$YvaTOvCHEQFJmzjhKkZVo.uR1QIFHqvx91YdR6cdeoEKjgKvm0pGe','ROLE_TEACHER',1,'rajkumar@bollywood.com','Rajkumar','Rao'),(66,'shabari','$2a$10$dBlvy/PDkWiVUJyqQo7oHO4Khf5iAjVjMp7MT6OmGPptBJH66stZS','ROLE_STUDENT',1,'shabari@gmail.com','Shabari','S Nair'),(68,'Ramesh','$2a$10$dx/HcHQ/45zWvXTa1meS1uL31a2P/1QzS10wUi6YJIG1dYxoh5eAC','ROLE_STUDENT',1,'Ramesh@test.com','Ramesh','Kumar'),(69,'NewUser4','$2a$10$pfLLPWjAl9.3RSbi.pqkKeQdvwK2o6NsnaoeFZDzoL9LQ0pr95WAa','ROLE_STUDENT',1,'newuser4@user.com','Akshat','Jain'),(70,'ram','$2a$10$iYjc.OALqGYPmj0.R6e0auylBUrnflcMMX.gYYgN0mL2zDAntmRLS','ROLE_STUDENT',1,'ram@gmail.com','Ram','kumar'),(71,'rahul','$2a$10$crlUeF0US8UyChlNJUofCu4TyNMEFho.3Ii/RZFeTLOuzRrqdlp8e','ROLE_STUDENT',1,'rahul@test.com','Rahul','kumar'),(73,'Binod','$2a$10$ib/1lGWxj6X6HTTGjG0JAu2rt0AwRiRyrb6bkBbuwgJTfNZV/ETtG','ROLE_STUDENT',1,'binod@gmail.com','Binod','Sharma'),(74,'bittu','$2a$10$cthxuowDVmt.Zgu2bHbvyepkXzYs0m7x2pa/kCOZMUSC9o85iwpX6','ROLE_TEACHER',1,'bittu@gmail.com','Bittu','Sharma');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-11-22 12:51:07
