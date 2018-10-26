package controllers;

import com.google.inject.Inject;
import models.userAccountSettingsForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import javax.validation.Validator;

public class UserAccountSettingsController extends controller {

    @inject
    FormFactor formFactory;
    public Result renderViewUserAccountSettings() {
        Form<userAccountSettingsForm> userAccountSettingsFormForm = formFactory.form(userAccountSettingsForm.class);
        return ok(views.html.UserAccountSettings.render(userAccountSettingsFormForm));
    }

    public Result UserAccount(){
        //TODO
        return null;
    }

}