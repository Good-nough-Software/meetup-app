package controllers;

import models.userProfileForm;
import views.html.viewUserProfile;
import models.loginForm;


import javax.inject.Inject;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

public class UserProfileController extends Controller {

    @Inject
    FormFactory formFactory;
    public Result renderViewUserProfile(){
        Form<userProfileForm> userProfileForm = formFactory.form(models.userProfileForm.class);
        return ok(viewUserProfile.render(userProfileForm, ""));
    }

    public Result UserProfile() {
        Form<loginForm> filledForm = formFactory.form(loginForm.class).bindFromRequest();

        String locations = filledForm.field("locations").getValue().get();
        String username = filledForm.field("username").getValue().get();
        String password = filledForm.field("password").getValue().get();


        // if(LoginController.validateUser(username, password) != 1){
        if (session().get("username").equals("null")) { // TODO replace with new authentication method
            Form<userProfileForm> userProfileForm = formFactory.form(models.userProfileForm.class);
            return ok(viewUserProfile.render(userProfileForm, "Username Taken"));
        } else{
            return ok("Locations: " + locations);
        }

    }

}
