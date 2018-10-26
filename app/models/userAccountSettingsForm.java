package models;

import play.data.validation.Constraints;

public class userAccountSettingsForm {


    public String username;
    public String password;
    public String phone;
    public String email;
    public String locations;
    public String home;

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

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String eamil) {
        this.email = email;
    }

    public String getlocations() {
        return locations;
    }
    public void setLocations(String locations) {
        this.locations = locations;
    }

    public String getHome() {
        return home;
    }
    public void setHome(String home) {
        this.home = home;
    }

    public loginForm(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public loginForm(){
        this.username = null;
        this.password = null;
    }


}
