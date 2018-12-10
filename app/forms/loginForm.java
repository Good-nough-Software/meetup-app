package forms;

/**
 * @author: Lucas Buccilli
 * Date: 10/19/18
 * Time: 2:42 PM
 * Project Name: meetup-app
 * File Name: loginForm
 */

public class loginForm {

    public String username;
    public String password;


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

    public loginForm(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public loginForm() {
        this.username = null;
        this.password = null;
    }


}
