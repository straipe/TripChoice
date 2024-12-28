CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `last_modified_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('ADMIN','USER') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `attractions` (
  `no` int NOT NULL AUTO_INCREMENT,
  `content_id` int DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `content_type_id` int DEFAULT NULL,
  `area_code` int DEFAULT NULL,
  `si_gun_gu_code` int DEFAULT NULL,
  `first_image1` varchar(255) DEFAULT NULL,
  `first_image2` varchar(255) DEFAULT NULL,
  `map_level` int DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `addr1` varchar(255) DEFAULT NULL,
  `addr2` varchar(255) DEFAULT NULL,
  `homepage` longtext,
  `overview` longtext,
  PRIMARY KEY (`no`),
  KEY `attractions_typeid_to_types_typeid_fk_idx` (`content_type_id`),
  KEY `attractions_sido_to_sidos_code_fk_idx` (`area_code`),
  KEY `attractions_sigungu_to_guguns_gugun_fk_idx` (`si_gun_gu_code`),
  CONSTRAINT `attractions_area_to_sidos_code_fk` FOREIGN KEY (`area_code`) REFERENCES `sidos` (`sido_code`),
  CONSTRAINT `attractions_sigungu_to_guguns_gugun_fk` FOREIGN KEY (`si_gun_gu_code`) REFERENCES `guguns` (`gugun_code`),
  CONSTRAINT `attractions_typeid_to_types_typeid_fk` FOREIGN KEY (`content_type_id`) REFERENCES `contenttypes` (`content_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=56644 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `blacked_tokens` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `expires_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `boards` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `last_modified_at` datetime(6) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `contenttypes` (
  `content_type_id` int NOT NULL,
  `content_type_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`content_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `comments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `last_modified_at` datetime(6) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `plan_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `guguns` (
  `no` int NOT NULL AUTO_INCREMENT,
  `sido_code` int DEFAULT NULL,
  `gugun_code` int DEFAULT NULL,
  `gugun_name` varchar(255) DEFAULT NULL,
  `sidocode` int DEFAULT NULL,
  PRIMARY KEY (`no`),
  KEY `guguns_sido_to_sidos_cdoe_fk_idx` (`sido_code`),
  KEY `gugun_code_idx` (`gugun_code`),
  CONSTRAINT `guguns_sido_to_sidos_cdoe_fk` FOREIGN KEY (`sido_code`) REFERENCES `sidos` (`sido_code`)
) ENGINE=InnoDB AUTO_INCREMENT=235 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `sidos` (
  `no` int NOT NULL AUTO_INCREMENT,
  `sido_code` int DEFAULT NULL,
  `sido_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`no`),
  UNIQUE KEY `sido_code_UNIQUE` (`sido_code`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `plans` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `last_modified_at` datetime(6) NOT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbybv5po44ssyv6svnv062dwrf` (`user_id`),
  CONSTRAINT `FKbybv5po44ssyv6svnv062dwrf` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `hotplaces` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `image` longblob,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `content_type_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhavr06h06cfn9sbv8tfuoieaq` (`content_type_id`),
  CONSTRAINT `FKhavr06h06cfn9sbv8tfuoieaq` FOREIGN KEY (`content_type_id`) REFERENCES `contenttypes` (`content_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `schedule_attractions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `attraction_id` int DEFAULT NULL,
  `sequence` int DEFAULT NULL,
  `schedule_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8gjg7f9yca2kolchhrtfpx6dv` (`schedule_id`),
  CONSTRAINT `FK8gjg7f9yca2kolchhrtfpx6dv` FOREIGN KEY (`schedule_id`) REFERENCES `schedules` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `schedules` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `plan_id` bigint DEFAULT NULL,
  `sequence` int DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;