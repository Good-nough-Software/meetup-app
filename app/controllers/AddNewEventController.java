package controllers;

import com.google.inject.Inject;
import models.newEventForm;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.db.Database;
import play.mvc.Result;
import views.html.viewAddEvent;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import static play.mvc.Controller.flash;
import static play.mvc.Results.ok;
import static play.mvc.Results.redirect;

/**
 * @author: Lucas Buccilli
 * Date: 11/16/18
 * Time: 2:59 PM
 * Project Name: meetup-app
 * File Name: AddNewEventController
 */
public class AddNewEventController {

    @Inject
    FormFactory formFactory;

    public Result renderViewAddEvent() {
        Result ok = ok(
                viewAddEvent.render(formFactory.form(newEventForm.class))
        );
        return ok;
    }

    @Inject
    Database db;
    public Result addNewEvent() {
        Form<newEventForm> filledForm = formFactory.form(newEventForm.class).bindFromRequest();
        //parses the data from the form
        String eventName = filledForm.get().getEventName();
        String eventDiscription = filledForm.get().getEventDiscription();
        String eventCreaterUsername = filledForm.get().getEventCreaterUsername();
        String eventLocation = filledForm.get().getEventLocation();

        String addUserSQLString  = "{call EventAdd('" + eventName + "','" + eventDiscription + "','" + eventLocation + "','" + eventCreaterUsername  + "')}";

        try {
            Connection con = db.getConnection();
            Logger.debug("Preparing call: " + addUserSQLString);
            CallableStatement userAddQuery;
            userAddQuery = con.prepareCall(addUserSQLString);
            Logger.debug("Prepared: " + userAddQuery.toString()) ;
            userAddQuery.execute();
            Logger.debug("Executed");

            return redirect(
                    routes.HomeController.index()
            );

        } catch (SQLException e) {
            Logger.debug(e.getMessage());
            flash("Message", "Error creating event");
            return ok();
        }
    }
}
