package forms;

import java.util.ArrayList;

public class userProfileForm {


    public String username = "";
    public String password = "";
    public String usertype = "";
    public String email = "";
    public String name = "";
    public String phone = "";
    public int location = 0;
    public String country, state, city, zip, address;
    public ArrayList<String> events;
    public String firstName = "";
    public String lastName = "";

    public String getEvents() {
        if (events.isEmpty()) {
            return "User is not attending any events!";
        } else {
            return events.toString().replace("[", "").replace("]", "");
        }
    }

    public void setEvents(ArrayList<String> events) {
        this.events = events;
    }

    public String getCountry() {
        if (country == null) {
            return "Country";
        }
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        if (state == null) {
            return "NA";
        }
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        if (city == null) {
            return "City";
        }
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        if (zip == null) {
            return "Zip";
        }
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getAddress() {
        if (address == null) {
            return "Address";
        }
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getUsername() {
        if (username == null) {
            return "Username";
        }
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        if (firstName == null) {
            return "FirstName";
        }
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        if (lastName == null) {
            return "LastName";
        }
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        if (name == null) {
            return "Name";
        }
        return getFirstName() + " " + getLastName();
    }

    public void setName(String name) {
        setFirstName(name.substring(0, name.indexOf(" ")));
        setLastName(name.substring(name.indexOf(" ") + 1));
    }

    public String getPhone() {
        if (phone == null) {
            return "Phone";
        }
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }


    public userProfileForm() {
        this.events = null;
    }

}
