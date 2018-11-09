package controllers;

import models.userAccountSettingsForm;
import views.html.viewUserAccountSettings;
import models.loginForm;


import javax.inject.Inject;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

public class UserAccountSettingsController extends Controller {

    @Inject
    FormFactory formFactory;
    public Result renderViewUserAccountSettings(){
        Form<userAccountSettingsForm> userAccountSettingsForm = formFactory.form(userAccountSettingsForm.class);
        return ok(viewUserAccountSettings.render(userAccountSettingsForm, ""));
    }

    public Result UserAccountSettings() {
        Form<loginForm> filledForm = formFactory.form(loginForm.class).bindFromRequest();

        String username = filledForm.field("username").getValue().get();
        String password = filledForm.field("password").getValue().get();
        String name = filledForm.field("name").getValue().get();
        String email = filledForm.field("email").getValue().get();
        String phone = filledForm.field("phone").getValue().get();
        String address = filledForm.field("address").getValue().get();

        // if(LoginController.validateUser(username, password) != 1){
        if (session().get("username").equals("null")) { // TODO replace with new method of autheication
            Form<userAccountSettingsForm> userAccountSettingsForm = formFactory.form(models.userAccountSettingsForm.class);
            return ok(viewUserAccountSettings.render(userAccountSettingsForm, "Invalid Username or Password"));
        } else {
            return ok("Username: " + username + "\nPassword: " + password + "\nName: " + name + "\nEmail: " + email + "\nPhone: " + phone + "\nAddress" + address);
        }
    }

}
