package models;


public class userAccountSettingsForm {

    public String username;
    public String password;
    public String name;
    public String phone;
    public String email;

    public userAccountSettingsForm(String username, String password, String name, String phone, String email){
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public userAccountSettingsForm() {
        this.username = null;
        this.password = null;
        this.name = null;
        this.phone = null;
        this.email = null;
    }

}
