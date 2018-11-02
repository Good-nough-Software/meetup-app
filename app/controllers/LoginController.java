package controllers;


import com.google.inject.Inject;
import io.ebean.Ebean;
import io.ebean.SqlQuery;
import io.ebean.SqlRow;
import models.loginForm;
import org.apache.commons.codec.digest.DigestUtils;
import play.data.Form;
import play.data.FormFactory;
import play.filters.csrf.CSRF;
import play.filters.csrf.RequireCSRFCheck;
import play.mvc.Controller;
import play.mvc.Result;
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
        return ok(viewLogin.render(formFactory.form(loginForm.class), ""));
    }


    //login action
    public Result login() {

        //populate form from viewLogin page
        Form<loginForm> filledForm = formFactory.form(loginForm.class).bindFromRequest();
        //parses the data from the form


        loginForm validatedLoginForm = validateUser(filledForm);

        if (validatedLoginForm.getCsrfToken() != null){
            //can pass in loginForm with filled info and csrf token;
            return redirect(routes.HomeController.index());
        }else {
            return ok(viewLogin.render(formFactory.form(loginForm.class), "ERROR"));
        }
    }

    @RequireCSRFCheck
    public Result userLoggedIn(loginForm validatedLoginForm){

        return ok();


    }

    //validates user login



    protected static loginForm validateUser(Form<loginForm> filledForm) {




        String username = filledForm.field("username").getValue().get();
        String password = filledForm.field("password").getValue().get();


        loginForm validatedLoginForm = new loginForm();
        validatedLoginForm.setUsername(username);
        validatedLoginForm.setPassword(password);

        String hashPassword = DigestUtils.sha1Hex(password);

        String queryString = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + hashPassword + "'";
        //returns list where username is username
        SqlQuery query = Ebean.createSqlQuery(queryString);


        List<SqlRow> rows = query.findList();

        for (SqlRow row : rows) {

            play.Logger.debug("Found user: " + row.getString("username"));
            if (row.getString("username").equals(validatedLoginForm.getUsername())) {
                if (row.getString("password").equals(hashPassword)) {
                    validatedLoginForm.setCsrfToken(CSRF.getToken(request()).map(CSRF.Token::value).orElse("null"));
                }
            }

        }

        return validatedLoginForm;
    }






}
