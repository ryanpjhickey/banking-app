CREATE DATABASE db_Bank; 

CREATE TABLE `user` (

 `acc_no` int NOT NULL AUTO_INCREMENT,

 `username` varchar(45) DEFAULT NULL,

 `bal` varchar(45) DEFAULT NULL,

 `pw` int DEFAULT NULL,

 PRIMARY KEY (`acc_no`),

 UNIQUE KEY `username_UNIQUE` (`username`)

) ;