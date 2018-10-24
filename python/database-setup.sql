-- Setup empty database for Meetup App

CREATE DATABASE IF NOT EXISTS meetup;

CREATE USER 'meet'@'%';
GRANT ALL ON meetup.* to 'meet'@'%';
-- GRANT SELECT ON meetup.* to 'meet'@'%';

USE meetup;

CREATE TABLE users (
       id       UNSIGNED INT,
       username VARCHAR(32),
       password CHAR(40), -- fixed size of hash size
       usertype ENUM('admin', 'user', 'organizer'),
       email    VARCHAR(32),
       name     VARCHAR(32),
       phone    CHAR(11),
       location UNSIGNED INT,
       FOREIGN KEY (location) REFERENCES location (id),
       PRIMARY KEY (id, username, email)
);

CREATE TABLE locations (
       id      UNSIGNED INT PRIMARY KEY,
       country VARCHAR(20),
       state   VARCHAR(2),
       city    VARCHAR(32),
       zip     VARCHAR(9),
       address VARCHAR(64),
);

CREATE TABLE relations (
       id         UNSIGNED INT PRIMARY KEY,
       userid     UNSIGNED INT,
       locationid UNSIGNED INT,
       FOREIGN KEY (userid) REFERENCES users (id),
       FOREIGN KEY (locationid) REFERENCES locations (id)
);
