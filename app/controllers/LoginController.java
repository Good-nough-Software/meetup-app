package controllers;


import com.google.inject.Inject;
import models.loginForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.viewLogin;

import java.util.HashMap;


/**
 * @author: Lucas Buccilli
 * Class: CS3141
 * Date: 10/15/18
 * Time: 2:21 PM
 * Project Name: meetup-app
 * File Name: LoginController
 */
public class LoginController extends Controller {


    //Provides loginForm to viewLogin to render page
    @Inject
    FormFactory formFactory;

    public Result renderViewLogin() {
        return ok(viewLogin.render(formFactory.form(loginForm.class), ""));
    }


    //login action
    public Result login() {

        //populate form from viewLogin page
        Form<loginForm> filledForm = formFactory.form(loginForm.class).bindFromRequest();
        //parses the data from the form
        String username = filledForm.field("username").getValue().get();
        String password = filledForm.field("password").getValue().get();

        //checks if user info is valid
        if (validateUser(username, password) == 1) {
            return ok("Username: " + username + "\nPassword: " + password);
        } else if (validateUser(username, password) == -1) {
            return ok(
                    viewLogin.render(formFactory.form(loginForm.class), "Username not found"));
        } else if (validateUser(username, password) == 0) {
            return ok(
                    viewLogin.render(formFactory.form(loginForm.class), "Invalid Password"));
        }
        return TODO;


    }

    //validates user login
    private int validateUser(String username, String password) {
        HashMap<String, String> userDb = new HashMap<>();
        userDb.put("test", "password");

        if (userDb.get(username) != null) {
            if (userDb.get(username).compareTo(password) == 0) {
                return 1;
            } else {
                return 0;
            }


        } else {
            return -1;
        }
    }


}
