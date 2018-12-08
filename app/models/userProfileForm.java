package models;

import javax.persistence.Entity;

@Entity
public class userProfileForm {

    public String personal, home, events;

    public String username;
    public String password;
    public String usertype;
    public String email;
    public String name;
    public String phone;
    public int location;
    public String country, state, city, zip, address;

    public String getCountry() {
        if (country == null){
            return "Country";
        }
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public String getState() {
        if(state == null){
            return "State";
        }
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public String getCity() {
        if (city == null){
            return "City";
        }
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }


    public String getEvents() {
        return events;
    }

    public void setEvents(String events) {
        this.events = events;
    }


    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }


    public String getUsername() {
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


    public String getName() {
        if (name == null){
            return "Name";
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPhone() {
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


    public userProfileForm(String personal, String home, String events){
        this.personal = personal;
        this.home = home;
        this.events = events;
    }

    public userProfileForm(){
        this.personal = null;
        this.home = null;
        this.events = null;
    }

}
