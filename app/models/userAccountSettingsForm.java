package models;


public class userAccountSettingsForm {

    public String username;
    public String password;
    public String phone;
    public String email;
    public String locations;
    public String home;

    public userAccountSettingsForm(String username, String password, String phone, String email, String locations, String home){
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.locations = locations;
        this.home = home;
    }

}
