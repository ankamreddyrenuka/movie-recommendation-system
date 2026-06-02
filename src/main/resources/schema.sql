CREATE TABLE IF NOT EXISTS destinations (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  destination_id VARCHAR(80) NOT NULL UNIQUE,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(2000),
  image_url VARCHAR(1024),
  backdrop_url VARCHAR(1024),
  region VARCHAR(120),
  country VARCHAR(120),
  district VARCHAR(120),
  address VARCHAR(1000),
  category VARCHAR(120),
  tags VARCHAR(1000),
  best_time_to_visit VARCHAR(120),
  travel_style VARCHAR(120),
  budget_level VARCHAR(80),
  budget_range VARCHAR(80),
  trip_duration INT,
  recommended_duration INT,
  best_season VARCHAR(80),
  avg_cost_per_day DOUBLE,
  monthly_visitors INT,
  rating DOUBLE,
  popularity DOUBLE,
  latitude DOUBLE,
  longitude DOUBLE,
  highlights VARCHAR(1200)
);

CREATE TABLE IF NOT EXISTS wishlist_destinations (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  destination_id VARCHAR(80) NOT NULL,
  destination_name VARCHAR(255) NOT NULL,
  image_url VARCHAR(1024),
  region VARCHAR(120),
  preferred_season VARCHAR(120),
  budget_category VARCHAR(80),
  desired_activities VARCHAR(1000),
  notes VARCHAR(2000)
);

CREATE TABLE IF NOT EXISTS saved_trips (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  destination_id VARCHAR(80) NOT NULL,
  destination_name VARCHAR(255) NOT NULL,
  image_url VARCHAR(1024),
  region VARCHAR(120),
  travel_dates VARCHAR(120),
  travelers INT,
  status VARCHAR(120),
  budget_range VARCHAR(80),
  saved_date DATE,
  notes VARCHAR(2000)
);
