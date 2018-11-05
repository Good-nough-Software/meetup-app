package controllers;

import com.google.inject.Inject;
import models.loginForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import models.newUserForm;
import views.html.viewNewUser;

/**
 * @author Lucas Buccilli
 * Date: 10/20/2018
 * Time: 6:40 PM
 * Project Name: meetup-app
 * File Name: newUserController
 */
public class newUserController extends Controller {


    @Inject
    FormFactory formFactory;
    public Result renderViewNewUser(){

        Form<newUserForm> newUserForm = formFactory.form(newUserForm.class);
        return ok(viewNewUser.render(newUserForm, ""));
    }

    public Result createNewUser(){
        //populate form from viewLogin page
        Form<loginForm> filledForm = formFactory.form(loginForm.class).bindFromRequest();
        //parses the data from the form
        String username = filledForm.field("username").getValue().get();
        String password = filledForm.field("password").getValue().get();
        String email = filledForm.field("email").getValue().get();
        String firstName = filledForm.field("firstName").getValue().get();
        String lastName = filledForm.field("lastName").getValue().get();

//        //username does not exist
//        if (LoginController.validateUser(username, password) != -1) {
//            Form<newUserForm> newUserForm = formFactory.form(newUserForm.class);
//            return ok(viewNewUser.render(newUserForm, "Username already taken"));
//        } else {
//            return ok("Username: " + username + "\nPassword: " + password + "\nEmail: " + email + "\nFirstname: " + firstName + "\nLastname: " + lastName);
//        }

        return TODO;

    }
}
