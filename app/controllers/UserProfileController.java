package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class UserProfileController extends controller {

    public Result UserProfile() {
        return ok(views.html.UserProfile.render());
    }

}