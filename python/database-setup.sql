-- Setup empty database for Meetup App

CREATE DATABASE IF NOT EXISTS meetup;

CREATE USER IF NOT EXISTS 'meet'@'localhost';
GRANT ALL ON meetup.* to 'meet'@'localhost';
-- GRANT SELECT ON meetup.* to 'meet'@'%';

USE meetup;
CREATE TABLE IF NOT EXISTS locations (
       id      INT AUTO_INCREMENT UNIQUE,
       country VARCHAR(20),
       state   VARCHAR(2),
       city    VARCHAR(32),
       zip     VARCHAR(9),
       address VARCHAR(64),
	PRIMARY KEY (country, state, city, zip, address)
);

CREATE TABLE IF NOT EXISTS users (
       id       INT AUTO_INCREMENT,
       username VARCHAR(32) UNIQUE,
       password CHAR(40), -- fixed size of hash size
       usertype ENUM('admin', 'user', 'organizer'),
       email    VARCHAR(32),
       name     VARCHAR(32),
       phone    CHAR(11),
       location INT,
       PRIMARY KEY (id, username, email),
       FOREIGN KEY (location) REFERENCES locations (id)
);

CREATE TABLE IF NOT EXISTS events (
       id		INT AUTO_INCREMENT UNIQUE,
       locationid	INT,
	summary	VARCHAR(240),
	userid		INT,
	startDate	DATE,
	endDate	DATE,
	name		VARCHAR(40),
	PRIMARY KEY (name, locationid, startDate),
       FOREIGN KEY (locationid) REFERENCES locations (id)
);

CREATE TABLE IF NOT EXISTS relations (
	id		INT AUTO_INCREMENT PRIMARY KEY,
	eventid	INT,
	userid		INT,
	FOREIGN KEY (eventid) REFERENCES events (id),
	FOREIGN KEY (userid) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS friends (
	id		INT AUTO_INCREMENT,
	userid		INT,
	friendid	INT,
	status		enum('pending', 'accepted', 'blocked'),
	FOREIGN KEY (userid) REFERENCES user (id),
	FOREIGN KEY (friendid) REFERENCES user (id),
	PRIMARY KEY (id, userid, friendid)
);

delimiter  //
DROP PROCEDURE IF EXISTS UserAdd//
CREATE PROCEDURE UserAdd
	(parameter_username VARCHAR(32),
	parameter_email VARCHAR(40),
	parameter_password CHAR(40),
	parameter_name VARCHAR(32))
	MODIFIES SQL DATA
	BEGIN
		insert into users (username, email, password, name) values(parameter_username, parameter_email, parameter_password, parameter_name);
	END;//

DROP PROCEDURE IF EXISTS UserValidate//
CREATE PROCEDURE UserValidate
	(parameter_username VARCHAR(32),
	parameter_password CHAR(40))
	READS SQL DATA
	BEGIN
		select count(*) from users where username=parameter_username and password=parameter_password;
	END;//

DROP PROCEDURE IF EXISTS UserPasswordChange//
CREATE PROCEDURE UserPasswordChange
	(parameter_username varchar(32),
	parameter_oldPassword varchar(40),
	parameter_newPassword varchar(40))
	MODIFIES SQL DATA
	BEGIN
		update users set users.password = parameter_newPassword where users.password = parameter_oldPassword and users.username = parameter_username;
	END;//

DROP PROCEDURE IF EXISTS UpdateUser//
CREATE PROCEDURE UpdateUser
	(parameter_username varchar(32),
	parameter_password varchar(40),
	parameter_name varchar(32),
	parameter_email varchar(32),
	parameter_phone char(11))
	BEGIN

	IF parameter_password != NULL THEN
		update users set password = parameter_password where users.username = parameter_username;
	END IF;

	IF parameter_name IS NOT NULL THEN
		update users set name = parameter_name where users.username = parameter_username;
	END IF;

	IF parameter_email IS NOT NULL THEN
		update users set email = parameter_email where users.username = parameter_username;
	END IF;

	IF parameter_phone IS NOT NULL THEN
		update users set phone = parameter_phone where users.username = parameter_username;
	END IF;

	END;//

delimiter ;
