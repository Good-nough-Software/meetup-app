package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class UserAccountSettingsController extends controller {

    public Result UserAccountSettings() {
        return ok(views.html.UserAccountSettings.render());
    }

}