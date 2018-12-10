package controllers;


import com.google.inject.Inject;
import forms.loginForm;
import models.Search;
import models.User;
import org.apache.commons.codec.digest.DigestUtils;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.viewLogin;

import java.util.List;


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
        session("username", "null");
        session("resetCode", "");
        return ok(
                viewLogin.render(formFactory.form(loginForm.class), formFactory.form(Search.class))
        );
    }


    //login action
    public Result login() {

        //populate form from viewLogin page
        Form<loginForm> filledForm = formFactory.form(loginForm.class).bindFromRequest();
        //parses the data from the form
        String username = filledForm.field("username").getValue().get();
        String password = filledForm.field("password").getValue().get();


        return authenticate(filledForm);
    }

    @Security.Authenticated
    public Result test() {
        return ok(
                viewLogin.render(formFactory.form(loginForm.class), formFactory.form(Search.class))
        );
    }

    public Result logout() {
        session().clear();
        flash("Message", "You've been logged out");
        return redirect(
                routes.LoginController.renderViewLogin()
        );
    }


    //must have a method called authenticate to access authenticated methods in secured
    private Result authenticate(Form<loginForm> filledForm) {

        //get loginForm information from the form that was filled inside of viewLogin
        loginForm validatedLoginForm = filledForm.get();

        int userid = userValidate(validatedLoginForm.getUsername(), validatedLoginForm.getPassword());
        //user was not found or had incorrect password
        if (userid <= 0) {

            session("username", "null");
            flash("Message", "Wrong username or password");
            return redirect(
                    routes.LoginController.renderViewLogin()
            );

        } else {
            session("username", validatedLoginForm.getUsername());
            session("userid", Integer.toString(userid));
            return redirect(
                    routes.HomeController.index()
            );
        }
    }

    // Returns the user id > 0 if user name and password is valid
    public int userValidate(String username, String password) {

        // get hased password
        String hashPassword = DigestUtils.sha1Hex(password);

        int userid;
        try {
            userid = User.find.query()
                .select("id")
                .where()
                .eq("username", username)
                .eq("password", hashPassword)
                .findSingleAttribute();
        } catch (NullPointerException e) {
            // Username not found
            return -1;
        }

        return userid;
    }

}
