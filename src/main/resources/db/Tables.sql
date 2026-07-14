-- travel_planner.trip definition

CREATE TABLE `trip` (
  `id` char(36) NOT NULL,
  `type` enum('PHOTO','SEA','MOUNTAIN') NOT NULL,
  `title` varchar(200) NOT NULL,
  `destination` varchar(200) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `status` enum('DRAFT','PLANNED','BOOKED') NOT NULL DEFAULT 'DRAFT',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `idx_trip_type` (`type`),
  KEY `idx_trip_dates` (`start_date`,`end_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- travel_planner.itinerary_item definition

CREATE TABLE `itinerary_item` (
  `id` char(36) NOT NULL,
  `trip_id` char(36) NOT NULL,
  `day_index` int(11) NOT NULL,
  `sequence` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `start_time` time DEFAULT NULL,
  `end_time` time DEFAULT NULL,
  `location` varchar(200) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `category` enum('ACTIVITY','MEAL','TRANSPORT','REST','SIGHTSEEING') NOT NULL,
  `budget_estimate` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_itin_trip_day` (`trip_id`,`day_index`),
  CONSTRAINT `fk_itin_trip` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- travel_planner.mountain_trip definition

CREATE TABLE `mountain_trip` (
  `trip_id` char(36) NOT NULL,
  `difficulty` enum('easy','medium','hard') NOT NULL DEFAULT 'easy',
  PRIMARY KEY (`trip_id`),
  CONSTRAINT `fk_mountain_trip` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- travel_planner.photo_trip definition

CREATE TABLE `photo_trip` (
  `trip_id` char(36) NOT NULL,
  `camera_preferences` varchar(200) DEFAULT NULL,
  `shooting_style` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`trip_id`),
  CONSTRAINT `fk_photo_trip` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- travel_planner.sea_trip definition

CREATE TABLE `sea_trip` (
  `trip_id` char(36) NOT NULL,
  `destination_port` varchar(200) DEFAULT NULL,
  `marine_activities` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`trip_id`),
  CONSTRAINT `fk_sea_trip` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- travel_planner.booking definition

CREATE TABLE `booking` (
  `id` char(36) NOT NULL,
  `trip_id` char(36) NOT NULL,
  `itinerary_item_id` char(36) DEFAULT NULL,
  `provider` varchar(100) NOT NULL,
  `booking_type` enum('FLIGHT','HOTEL','TOUR','CAR','RESTAURANT_RESERVATION','OTHER') NOT NULL,
  `reference_code` varchar(100) DEFAULT NULL,
  `check_in_date` date DEFAULT NULL,
  `check_out_date` date DEFAULT NULL,
  `scheduled_at` datetime DEFAULT NULL,
  `amount` decimal(10,2) DEFAULT NULL,
  `currency` char(3) NOT NULL DEFAULT 'USD',
  `status` enum('REQUESTED','CONFIRMED','CANCELLED','COMPLETED') NOT NULL DEFAULT 'REQUESTED',
  `notes` text DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_booking_trip` (`trip_id`),
  KEY `idx_booking_item` (`itinerary_item_id`),
  CONSTRAINT `fk_booking_itin` FOREIGN KEY (`itinerary_item_id`) REFERENCES `itinerary_item` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_booking_trip` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;