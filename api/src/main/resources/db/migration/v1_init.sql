-- V1__create_tables.sql

-- USERS
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `best_streak` int DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `last_activity` datetime(6) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  `streak` int DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `active_routine_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`),
  UNIQUE KEY `UK4md46u68niy6jff0l6wiyt6yl` (`active_routine_id`)
);

-- ROUTINES
CREATE TABLE `routines` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `priority` enum('HIGH','LOW','MEDIUM') NOT NULL,
  `started_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKlsu2ed1i9h7w7j9i3t5nhqyka` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);

-- ROUTINE HISTORIES
CREATE TABLE `routine_histories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) NOT NULL,
  `finished_at` datetime(6) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `priority` enum('HIGH','LOW','MEDIUM') NOT NULL,
  `started_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `routine_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKoyiftowdx1dapkeaq3wq4epnm` FOREIGN KEY (`routine_id`) REFERENCES `routines` (`id`),
  CONSTRAINT `FKi5gp9ccp40wram079cv73uigd` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);

-- ROUTINE SCHEDULE
CREATE TABLE `routine_schedule` (
  `id` int NOT NULL AUTO_INCREMENT,
  `day_of_week` enum('FRIDAY','MONDAY','SATURDAY','SUNDAY','THURSDAY','TUESDAY','WEDNESDAY') NOT NULL,
  `routine_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKsx4xdemw0wxn6a3o5mpcpqyfe` FOREIGN KEY (`routine_id`) REFERENCES `routines` (`id`),
  CONSTRAINT `FKn3hyfqu9tg59qsr3o9aw86xyq` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);

-- TASKS
CREATE TABLE `tasks` (
  `id` int NOT NULL AUTO_INCREMENT,
  `completed` bit(1) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `estimate` double NOT NULL,
  `finished_at` datetime(6) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `started_at` datetime(6) DEFAULT NULL,
  `timer` int DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK6s1ob9k4ihi75xbxe2w0ylsdh` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);

-- TASK HISTORIES
CREATE TABLE `task_histories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `completed` bit(1) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `estimate` double NOT NULL,
  `finished_at` datetime(6) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `started_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `routine_history_id` int NOT NULL,
  `task_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK3f1lsp7kwimqfb1ufwdvb4fuf` FOREIGN KEY (`routine_history_id`) REFERENCES `routine_histories` (`id`),
  CONSTRAINT `FKmacu1ui1wsdfvrow9y358v5u5` FOREIGN KEY (`task_id`) REFERENCES `tasks` (`id`),
  CONSTRAINT `FKt2tnviebedqgoqb3cy2i8luk7` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);

-- TASK ROUTINES
CREATE TABLE `task_routines` (
  `task_id` int NOT NULL,
  `routine_id` int NOT NULL,
  PRIMARY KEY (`task_id`,`routine_id`),
  CONSTRAINT `FK8ke7fknl30l2wrjv5lmqse9q` FOREIGN KEY (`task_id`) REFERENCES `tasks` (`id`),
  CONSTRAINT `FKjcpcoop2v0fcxf4r0ly6f8tyx` FOREIGN KEY (`routine_id`) REFERENCES `routines` (`id`)
);

-- RELACIONAMENTO USERS -> ROUTINES
ALTER TABLE `users` ADD CONSTRAINT `FKnt12vktprjorqm53qfspkouns` FOREIGN KEY (`active_routine_id`) REFERENCES `routines` (`id`);
