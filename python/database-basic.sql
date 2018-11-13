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
	values ("jmarek", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", "admin",
		"jmarek@mtu.edu", "Jerry Marek", "12311234567");

insert into locations (country, state, city, zip, address) 
	values ("United States", "Michigan", "Houghton", "49931", "1701 Woodland rd");

insert into locations (country, state, city, zip, address) 
	values ("United States", "Michigan", "Traverse City", "49643", '529 W 8th st');

insert into locations (country, state, city, zip, address) 
	values ("United States", "Michigan", "Ann Arbor", "48110", "1313 deadend drive");

insert into locations (country, state, city, zip, address) 
	values ("United States", "Pennsylvania", "Philadelphia", "19019", "6969 lovers lane");

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

insert into events (locationid, userid, startDate, endDate, name)
	values ((select id from locations where zip="19019"),
		(select id from users where email="mdock@gmail.com"),
		"2018-10-10 20:00:00", "2018-10-12 11:00:00", "Phillicon");

insert into relations (userid, eventid) values ((select id from users where email="jmarek@mtu.edu"), (select id from events where id > 0));

insert into relations (userid, eventid) values ((select id from users where email="jdoe@gmail.com"), (select id from events where id > 0));
