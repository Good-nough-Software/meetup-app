-- noinspection SqlNoDataSourceInspectionForFile

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
	FOREIGN KEY (userid) REFERENCES users (id),
	FOREIGN KEY (friendid) REFERENCES users (id),
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

DROP PROCEDURE IF EXISTS RemoveRelation//
CREATE PROCEDURE RemoveRelation
	(parameter_eid int,
	parameter_uid int
	)
	BEGIN
	DELETE FROM relations WHERE parameter_eid = eventid AND parameter_uid = userid;

	END//

DROP PROCEDURE IF EXISTS LocationAdd//
CREATE PROCEDURE LocationAdd
	(parameter_country varchar(20),
	parameter_state varchar(2),
	parameter_city varchar(32),
	parameter_zip varchar(9),
	parameter_address varchar(64)
	)
	BEGIN
	INSERT into locations (country, state, city, zip, address) values 
		(parameter_country,
		parameter_state,
		parameter_city,
		parameter_zip,
		parameter_address);

	SELECT id FROM locations WHERE country=parameter_country AND state=parameter_state AND city=parameter_city AND zip=parameter_zip AND address=parameter_address;

	END//

DROP PROCEDURE IF EXISTS EventAdd//
CREATE PROCEDURE EventAdd
	(param_eventName varchar(20),
	param_description varchar(240),
	param_locCountry varchar(20),
	param_locState varchar(2),
	param_locCity varchar(32),
	param_locZip varchar(9),
	param_locAddress varchar(32),
	param_username varchar(32),
	param_startDate date,
	param_endDate date
	)
	BEGIN

	declare v_count int;
	declare v_id int;

	-- check if location exists
	select count(*), id into v_count, v_id from locations where param_locCountry=country AND param_locState=state AND param_locCity=City AND param_locZip=zip AND param_locAddress=address;

	IF (v_count  = 0)
		THEN
			INSERT INTO locations (country, state, city, zip, address) VALUES (param_locCountry, param_locState, param_locCity, param_locZip, param_locAddress);
			select  id INTO v_id FROM locations WHERE param_locCountry=country AND param_locState=state AND param_locCity=city AND param_locZip=zip AND param_locAddress=address;
		END IF;
		INSERT INTO events (name, summary, locationId, userid, startDate, endDate) VALUES (param_eventName, param_description, v_id, (SELECT id FROM users WHERE username=param_username), param_startDate, param_endDate);
	-- add event

	END//

delimiter ;
