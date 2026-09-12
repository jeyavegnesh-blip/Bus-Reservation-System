CREATE DATABASE IF NOT EXISTS bus_db;
USE bus_db;

CREATE TABLE buses(
 id INT AUTO_INCREMENT PRIMARY KEY,
 bus_number VARCHAR(30) UNIQUE NOT NULL,
 source VARCHAR(80) NOT NULL,
 destination VARCHAR(80) NOT NULL,
 travel_date DATE NOT NULL,
 seats INT NOT NULL,
 available_seats INT NOT NULL
);

CREATE TABLE reservations(
 id INT AUTO_INCREMENT PRIMARY KEY,
 bus_id INT NOT NULL,
 passenger_name VARCHAR(100) NOT NULL,
 phone VARCHAR(20),
 seat_no INT NOT NULL,
 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 UNIQUE(bus_id,seat_no),
 FOREIGN KEY(bus_id) REFERENCES buses(id)
);

INSERT INTO buses(bus_number,source,destination,travel_date,seats,available_seats)
VALUES('TN-01-AB-1234','Dindigul','Chennai','2026-10-01',40,40);