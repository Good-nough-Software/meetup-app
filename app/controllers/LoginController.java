package controllers;


import com.google.inject.Inject;
import io.ebean.Ebean;
import io.ebean.SqlQuery;
import io.ebean.SqlRow;
import models.loginForm;
import org.apache.commons.codec.digest.DigestUtils;
import play.Logger;
import play.api.Play;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.twirl.api.Content;
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
        return ok(
                viewLogin.render(formFactory.form(loginForm.class))
        );
    }


    //login action
    public Result login() {

        //populate form from viewLogin page
        Form<loginForm> filledForm = formFactory.form(loginForm.class).bindFromRequest();
        //parses the data from the form


        return authenticate(filledForm);


    }

    @Security.Authenticated
    public Result test() {
        return ok(
                viewLogin.render(formFactory.form(loginForm.class))
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
    public Result authenticate(Form<loginForm> filledForm) {

        //get loginForm information from the form that was filled inside of viewLogin
        loginForm validatedLoginForm = filledForm.get();

        //get hased password
        String hashPassword = DigestUtils.sha1Hex(validatedLoginForm.getPassword());

        String queryString = "SELECT * FROM users WHERE username = '" + validatedLoginForm.getUsername() + "' AND password = '" + hashPassword + "'";
        //returns list where username is username
        SqlQuery query = Ebean.createSqlQuery(queryString);


        List<SqlRow> rows = query.findList();


        session("username", "null");

        for (SqlRow row : rows) {

            if (row.getString("username").equals(validatedLoginForm.getUsername())) {
                if (row.getString("password").equals(hashPassword)) {
                    //username with password exists, add username to session
                    session("username", filledForm.get().username);
                }
            }

        }

        //user was not found or had incorrect password
        if (session().get("username").equals("null")){

            flash("Message", "Wrong username or password");
            return redirect(
                    routes.LoginController.renderViewLogin()
            );

        }else {
            return redirect(
                    routes.LoginController.test()
            );
        }
    }






}
