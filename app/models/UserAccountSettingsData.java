package models;

public class UserAccountSettingsData {
    private String username;
    private String password;
    private String phone;
    private String email;
    private String locations;
    private String home;

    public UserAccountSettingsData() {
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

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocations() {
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

}
