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

delimiter  //
CREATE PROCEDURE IF NOT EXISTS UserAdd
	(parameter_username VARCHAR(32),
	parameter_email VARCHAR(40),
	parameter_password CHAR(40))
	MODIFIES SQL DATA
	BEGIN
		insert into users (username, email, password) values(parameter_username, parameter_email, paramter_password);
	END;//

CREATE PROCEDURE IF NOT EXISTS UserValidate
	(parameter_username VARCHAR(32),
	parameter_password CHAR(40))
	READS SQL DATA
	BEGIN
		select count(*) from users where username=parameter_username and password=parameter_password;
	END;//

CREATE PROCEDURE IF NOT EXISTS UserPasswordChange
	(parameter_username varchar(32),
	parameter_oldPassword varchar(40),
	parameter_newPassword varchar(40))
	MODIFIES SQL DATA
	BEGIN
		update users set users.password = parameter_newPassword where user.password = parameter_newPassword and user.username = parameter_username;
	END;//

CREATE PROCEDURE IF NOT EXISTS UpdateUser
	(parameter_username varchar(32),
	parameter_password varchar(40),
	parameter_name varchar(32),
	parameter_email varchar(32),
	parameter_phone char(11))
	BEGIN

	if parameter_password != null then
		update users set password = parameter_password where user = parameter_username;
	end if;

	if parameter_name != null then
		update users set name = parameter_name where user = parameter_username;
	end if;

	if parameter_email != null then
		update users set email = parameter_email where user = parameter_username;
	end if;

	if parameter_phone != null then
		update users set phone = parameter_phone where user = parameter_username;
	end if;

	END;//

delimiter ;
