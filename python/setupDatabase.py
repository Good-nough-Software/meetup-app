#/usr/bin/python3
import os
import mysql.connector as mariadb

verison = "0.1"
author = "Jerry Marek"

# Currently untested
def main(host=None, port=None):

    # Check for user@host[port]
    if (self.host == None):
        print("Host not provided")
        self.host = raw_input("Host ip: ")

    if (self.port == None):
        print("Port not provided")
        self.port = raw_input("port number: ")

    if (self.user == None):
        print("User not provided")
        self.user = raw_input("user name: ")

    # Attempt to connect to database
    mariadb.connect(self.user, self.password, self.host)
    cursor = mariadb.cursor()

def init_good_state(cursor):
    # ["table", "a1, a2, a3, ... an"]
    users = {"User": ["blob", "user", "abc@gmail.com", "Alice", "12318675309"],
            "User": ["blob", "user", "cde@gmail.com", "Bob", "12311234567"]
            };
    locations = {"Locations": ["US", "Michigan", "Houghton", "1701 Townsend Drive"]
                };
    user_locs = {"UserLocations": ["ID", "enrolled at", "locFK", "startDate", "endData"],
                        "UserLocations": ["ID", "worked at", "locFK", "startDate", "endData"]
                    };

    for user in user.list:
        cursor.execute("INSERT %s %s %s %s %s INTO %s" % ((w for w in users[user]), user));

    for location in locations.list:
        cursor.execute("INSERT (%s %s %s %s) INTO %s" % ((w for w in locations[location]), location));

    for user_loc in user_locs.list:
        cursor.execute("INSERT (%s %s %s %s %s) INTO %s" % ((w for w in locations[location]), location));

def clean_database(cursor):
    pass

if __name__ == "__main__":
    main()
