package controllers;

import com.google.inject.Inject;
import forms.userProfileForm;
import forms.Search;
import play.data.FormFactory;
import play.mvc.Result;
import java.util.List;
import java.util.ArrayList;

import models.User;
import models.Relation;
import models.Event;

import static play.mvc.Controller.session;
import static play.mvc.Results.ok;
import static play.mvc.Results.redirect;

/**
 * @author: Lucas Buccilli
 * Date: 11/30/18
 * Time: 3:10 PM
 * Project Name: meetup-app
 * File Name: nonCurrentUserProfileController
 */
public class nonCurrentUserProfileController {


    @Inject
    FormFactory formFactory;

    public Result renderProfile() {
        if (!session().containsKey("username") || session().get("username").equals("null")) {
            return redirect("/login");
        }

        String username = session().get("username");

        return renderNonCurrentUserProfileView(username);
    }


    public Result renderNonCurrentUserProfileView(String username) {

        // Gets information to populate user
        User user = User.find.query()
            .where()
            .eq("username", username)
            .findOne();

        if (user == null) {
            return redirect("/");
        }

        // Find events user is attending
        List<Integer> eventids = Relation.find.query()
            .select("event")
            .where()
            .eq("userid", user.id)
            .findSingleAttributeList();

        List<Event> events = new ArrayList<>();
        for (int id : eventids)
            events.add(Event.find.byId(id));

        //passes in our filled form
        return ok(
                  views.html.viewNonCurrentUserProfile.render(user, events, formFactory.form(Search.class))
        );
    }
}
