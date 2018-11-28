package controllers;

import models.Search;
import models.loginForm;
import models.userAccountSettingsForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.viewUserAccountSettings;

import javax.inject.Inject;

public class UserAccountSettingsController extends Controller {

    @Inject
    FormFactory formFactory;
    public Result renderViewUserAccountSettings(){
        Form<userAccountSettingsForm> userAccountSettingsForm = formFactory.form(userAccountSettingsForm.class);
        return ok(viewUserAccountSettings.render(userAccountSettingsForm, "", formFactory.form(Search.class)));
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
            return ok(viewUserAccountSettings.render(userAccountSettingsForm, "Invalid Username or Password", formFactory.form(Search.class)));
        } else {
            return ok("Username: " + username + "\nPassword: " + password + "\nName: " + name + "\nEmail: " + email + "\nPhone: " + phone + "\nAddress" + address);
        }
    }

}
