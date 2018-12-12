use meetup;

insert into users (username, password, usertype, email, name, phone)
	values ("jsmith", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", "user",
		"jsmith@gmail.com", "John Smith", "12311234567");

insert into users (username, password, usertype, email, name, phone)
	values ("jdoe", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", "organizer",
		"jdoe@gmail.com", "Jane Doe", "12311234567");

insert into users (username, password, usertype, email, name, phone)
	values ("mdock", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", "user",
		"mdock@gmail.com", "Matt Dock", "12311234567");

insert into users (username, password, usertype, email, name, phone)
	values ("tpoole", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", "user",
		"tpoole@gmail.com", "Tim Poole", "12311234567");

insert into users (username, password, usertype, email, name, phone)
	values ("mseuss", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", "user",
		"mseuss@gmail.com", "Mike Suess", "12311234567");

insert into users (username, password, usertype, email, name, phone)
	values ("jmarek", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", "admin",
		"jmarek@mtu.edu", "Jerry Marek", "12311234567");

insert into locations (country, state, city, zip, address) 
	values ("United States", "Mi", "Houghton", "49931", "1701 Woodland rd");

insert into locations (country, state, city, zip, address) 
	values ("United States", "Mi", "Traverse City", "49643", '529 W 8th st');

insert into locations (country, state, city, zip, address) 
	values ("United States", "Mi", "Ann Arbor", "48110", "1313 deadend drive");

insert into locations (country, state, city, zip, address) 
	values ("United States", "Pn", "Philadelphia", "19019", "6969 lovers lane");

update users 
	set location = (select id from locations where zip="49931")
where email = "jsmith@gmail.com";

update users 
	set location = (select id from locations where zip="49931")
where email = "jdoe@gmail.com";
update users 
	set location = (select id from locations where zip="48810")
where email = "mdock@gmail.com";
update users 
	set location = (select id from locations where zip="49643")
where email = "jmarek@mtu.edu";
update users 
	set location = (select id from locations where zip="49643")
where email = "tpoole@mtu.edu";
update users 
	set location = (select id from locations where zip="49643")
where email = "mseuss@mtu.edu";

insert into events (locationid, userid, startDate, endDate, name)
	values ((select id from locations where zip="19019"),
		(select id from users where email="mdock@gmail.com"),
		"2018-10-10 20:00:00", "2018-10-12 11:00:00", "Phillicon");

insert into events (locationid, userid, startDate, endDate, name)
	values ((select id from locations where zip="48110"),
		(select id from users where email="mseuss@gmail.com"),
		"2018-11-11 20:00:00", "2018-11-14 12:00:00", "Blueberry Festival");

insert into events (locationid, userid, startDate, endDate, name)
	values ((select id from locations where zip="49931"),
		(select id from users where email="jsmith@gmail.com"),
		"2019-01-11 20:00:00", "2019-01-11 22:00:00", "Swing Dance Competition");

insert into events (locationid, userid, startDate, endDate, name)
	values ((select id from locations where zip="49931"),
		(select id from users where email="jsmith@gmail.com"),
		"2019-02-16 20:00:00", "2019-02-18 10:00:00", "Winter Wonder Hack");

insert into events (locationid, userid, startDate, endDate, name)
	values ((select id from locations where zip="49931"),
		(select id from users where email="tpoole@gmail.com"),
		"2019-01-22 08:00:00", "2019-01-22 10:00:00", "Breakfast with Blizzard");

insert into relations (userid, eventid) values ((select id from users where email="jmarek@mtu.edu"), (select id from events where id = 0));

insert into relations (userid, eventid) values ((select id from users where email="jdoe@gmail.com"), (select id from events where id = 0));
