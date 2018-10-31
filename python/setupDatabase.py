#!/usr/bin/python3
# Author Jerry Marek

import hashlib
import pymysql as mariadb
from subprocess import call

# Currently untested
def main(host="127.0.0.1", user="meet", password=''):

    # Attempt to connect to database
    db = mariadb.connect(host, user, password, db='meetup')
    cursor = db.cursor()
    init_good_state(cursor)
    db.commit()
    db.close()

def init_good_state(cursor):
    # user[id int auto_inc, username varchar(32), password char(40) usertype enum, 
    #       email varchar(32), name varchar(32), phone char(11), location int]
    password = hashlib.sha1(b'password').hexdigest()
    users = {"John": ("jsmith", password, "user", "jsmith@gmail.com", "John Smith",
                        "12345678910"),
            "Jane": ("jnsmith", password, "user", "cde@gmail.com", "Jane Smith",
                        "12311234567")
            };
    # locations [id int auto_inc, country varchar(20), state varchar(2), city varchar(2),
    #               zip varchar(9), address varchar(64)]
    locations = {"Wads": ("United States", "Mi", "Houghton", "49931",
                                "1701 Townsend Drive")
                };
    # relations [id int auto_inc, userid fk users:is, locationid fk location:id]
    #

    for user in users:
        cursor.execute("INSERT INTO users (username, password, usertype, email, name, phone) values ('{}', '{}', '{}', '{}', '{}', '{}');".format(*users[user]))

    for location in locations:
        cursor.execute("INSERT INTO locations (country, state, city, zip, address) values ('{}', '{}', '{}', '{}', '{}')".format(*locations[location]))

    userids = ()
    locationid=0

    cursor.execute("SELECT id from users")
    a = cursor.fetchall()
    userids = userids + a[0]
    userids = userids + a[1]

    cursor.execute("SELECT id from locations")
    for id in cursor:
        locationid = id[0]

    relations = {"r1": (userids[0], locationid),
                    "r2": (userids[1], locationid)
                };

    for relation in relations:
        cursor.execute("INSERT INTO relations (userid, locationid) values ('{}', '{}')".format(*relations[relation]))

if __name__ == "__main__":
    main()
