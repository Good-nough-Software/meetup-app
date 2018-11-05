package controllers;


import com.google.inject.Inject;
import io.ebean.Ebean;
import io.ebean.SqlQuery;
import io.ebean.SqlRow;
import models.loginForm;
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
        return ok(
                viewLogin.render(formFactory.form(loginForm.class), "")
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
                viewLogin.render(formFactory.form(loginForm.class), "Username: " + session("username"))
        );
    }

    public Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(
                routes.LoginController.renderViewLogin()
        );
    }




    //must have a method called authenticate to access authenticated methods in secured
    public Result authenticate(Form<loginForm> filledForm) {
        loginForm validatedLoginForm = filledForm.get();

        String hashPassword = DigestUtils.sha1Hex(validatedLoginForm.getPassword());

        String queryString = "SELECT * FROM users WHERE username = '" + validatedLoginForm.getUsername() + "' AND password = '" + hashPassword + "'";
        //returns list where username is username
        SqlQuery query = Ebean.createSqlQuery(queryString);


        List<SqlRow> rows = query.findList();


        session("username", "null");

        for (SqlRow row : rows) {

            play.Logger.debug("Found user: " + row.getString("username"));
            if (row.getString("username").equals(validatedLoginForm.getUsername())) {
                if (row.getString("password").equals(hashPassword)) {
                    session("username", filledForm.get().username);
                }
            }

        }

        if (session().get("username").equals("null")){
            return badRequest(
                    viewLogin.render(formFactory.form(loginForm.class), "Could not login")
            );
        }else {

            session("username", validatedLoginForm.getUsername());
            return redirect(
                    routes.LoginController.test()
            );
        }
    }






}
