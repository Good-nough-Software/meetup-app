package models;

/**
 * @author: Lucas Buccilli
 * Date: 10/22/18
 * Time: 2:36 PM
 * Project Name: meetup-app
 * File Name: resetPasswordForm
 */
public class resetPasswordForm {
    private String email;

    public resetPasswordForm(String email) {
        this.email = email;
    }
    public resetPasswordForm() {
        this.email = null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
