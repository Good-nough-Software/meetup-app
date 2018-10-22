package controllers;

import com.google.inject.Inject;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import models.newUserForm;

/**
 * @author Lucas Buccilli
 * Class: newUserController.class
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
        return ok(views.html.viewNewUser.render(newUserForm));
    }

    public Result createNewUser(){
        return TODO;
    }
}
