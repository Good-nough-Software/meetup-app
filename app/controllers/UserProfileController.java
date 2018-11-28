package controllers;

import models.Search;
import models.loginForm;
import models.userProfileForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.viewUserProfile;

import javax.inject.Inject;

public class UserProfileController extends Controller {

    @Inject
    FormFactory formFactory;
    public Result renderViewUserProfile(){
        Form<userProfileForm> userProfileForm = formFactory.form(models.userProfileForm.class);
        return ok(viewUserProfile.render(userProfileForm, "", formFactory.form(Search.class)));
    }

    public Result UserProfile() {
        Form<loginForm> filledForm = formFactory.form(loginForm.class).bindFromRequest();

        String locations = filledForm.field("locations").getValue().get();
        String username = filledForm.field("username").getValue().get();
        String password = filledForm.field("password").getValue().get();


        // if(LoginController.validateUser(username, password) != 1){
        if (session().get("username").equals("null")) { // TODO replace with new authentication method
            Form<userProfileForm> userProfileForm = formFactory.form(models.userProfileForm.class);
            return ok(viewUserProfile.render(userProfileForm, "Username Taken", formFactory.form(Search.class)));
        } else{
            return ok("Locations: " + locations);
        }

    }

}
