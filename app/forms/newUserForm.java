package forms;

/**
 * @author: Lucas Buccilli
 * Date: 10/22/18
 * Time: 2:05 PM
 * Project Name: meetup-app
 * File Name: newUserForm
 */


public class newUserForm {
    /**
     * Class for the new user page
     * Has default constructor(args)
     * default constructor()
     * getters
     * setters
     */

    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;


    public newUserForm(String firstName, String lastName, String email, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public newUserForm() {
        this.firstName = null;
        this.lastName = null;
        this.username = null;
        this.password = null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
}
