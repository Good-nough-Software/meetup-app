package controllers;

import models.userAccountSettingsForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;

import javax.inject.Inject;

import java.util.List;

public class UserAccountSettingsController extends Controller {

    private final Form<UserAccountSettingsData> form;
    private final List<userAccountSettingsForm> userAccountSettings;


    @Inject
    public UserAccountSettingsController(FormFactory formFactory) {
        this.form = formFactory.form(UserAccountSettingsData.class);
        this.userAccountSettings = com.google.common.collect.Lists.newArrayList();
    }

    public Result UserAccountSettings(){
        return ok(views.html.UserAccountSettings.render());
    }

    public Result createUserAccountSettings() {

        //TODO
        flash("info", "Settings Updated");
        return null;
    }

}