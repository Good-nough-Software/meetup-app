package controllers;


import com.google.inject.Inject;
import models.loginForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import javax.validation.Validator;


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
    public Result renderViewLogin(){

        Form<loginForm> loginForm = formFactory.form(loginForm.class);
        return ok(views.html.viewLogin.render(loginForm));
    }

    //Needs users authentication
    public Result login() {

        return TODO;
    }




}
