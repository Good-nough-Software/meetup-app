-- Setup empty database for Meetup App

CREATE DATABASE IF NOT EXISTS meetup;

CREATE USER IF NOT EXISTS 'meet'@'%';
GRANT ALL ON meetup.* to 'meet'@'%';
-- GRANT SELECT ON meetup.* to 'meet'@'%';

USE meetup;
CREATE TABLE locations (
       id      INT AUTO_INCREMENT PRIMARY KEY,
       country VARCHAR(20),
       state   VARCHAR(2),
       city    VARCHAR(32),
       zip     VARCHAR(9),
       address VARCHAR(64)
);

CREATE TABLE users (
       id       INT AUTO_INCREMENT,
       username VARCHAR(32) UNIQUE,
       password CHAR(40), -- fixed size of hash size
       usertype ENUM('admin', 'user', 'organizer'),
       email    VARCHAR(32),
       name     VARCHAR(32),
       phone    CHAR(11),
       location INT,
       FOREIGN KEY (location) REFERENCES locations (id),
       PRIMARY KEY (id, username, email)
);

CREATE TABLE relations (
       id         INT AUTO_INCREMENT PRIMARY KEY,
       userid     INT,
       locationid INT,
       FOREIGN KEY (userid) REFERENCES users (id),
       FOREIGN KEY (locationid) REFERENCES locations (id)
);
