package controllers;

import com.google.inject.Inject;
import models.loginForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;



/**
 * @author: Lucas Buccilli
 * Class: CS3141
 * Date: 10/15/18
 * Time: 2:21 PM
 * Project Name: meetup-app
 * File Name: LoginController
 */
public class LoginController extends Controller {

    @Inject FormFactory formFactory;


    public Result renderViewLogin(){
        Form<loginForm> userForm = formFactory.form(loginForm.class);
        return ok(views.html.viewLogin.render(userForm));
    }

    public Result login() {
        Form<loginForm> userForm = formFactory.form(loginForm.class);

        loginForm loginForm = new loginForm("me@test.com", "mypassword");

        Form<loginForm> form = formFactory.form(loginForm.class);

        Form<loginForm> filledForm = form.fill(loginForm);

        return ok(views.html.viewLogin.render(filledForm));
    }


}
