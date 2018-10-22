package controllers;

import com.google.inject.Inject;
import models.newUserForm;
import models.resetPasswordForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author Lucas Buccilli
 * Class: newUserController.class
 * Date: 10/20/2018
 * Time: 6:40 PM
 * Project Name: meetup-app
 * File Name: newUserController
 */
public class resetPasswordController extends Controller {


    @Inject
    FormFactory formFactory;
    public Result renderViewResetPassword(){

        Form<resetPasswordForm> resetPasswordForm = formFactory.form(resetPasswordForm.class);
        return ok(views.html.viewResetPassword.render(resetPasswordForm));
    }

    public Result resetPassword(){
        return TODO;
    }
}
