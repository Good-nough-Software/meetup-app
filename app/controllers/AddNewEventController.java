package controllers;

import com.google.inject.Inject;
import forms.newEventForm;
import forms.Search;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.db.Database;
import play.mvc.Result;
import views.html.viewAddEvent;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static play.mvc.Controller.flash;
import static play.mvc.Controller.session;
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
        if (!session().containsKey("username") || session().get("username").equals("null")) {
            return redirect("/login");
        }

        Result ok = ok(
                viewAddEvent.render(formFactory.form(newEventForm.class),(formFactory.form(Search.class)))
        );
        return ok;
    }

    @Inject
    Database db;
    public Result addNewEvent() {
        Form<newEventForm> filledForm = formFactory.form(newEventForm.class).bindFromRequest();
        //parses the data from the form
        String eventName = filledForm.get().getEventName();
        String eventDescription = filledForm.get().getEventDescription();
        String eventCreaterUsername = session().get("username");
        String eventCountry = filledForm.get().getEventCountry();
        if (eventCountry == null || eventCountry.isEmpty()) {
            eventCountry = "United States";
        }
        String eventState = filledForm.get().getEventState();
        String eventCity = filledForm.get().getEventCity();
        String eventAddress = filledForm.get().getEventAddress();
        String eventZip = filledForm.get().getEventZip();
        Date startDate = filledForm.get().getStartDate();
        Date endDate = filledForm.get().getEndDate();

        String startDateF = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
        String endDateF = new SimpleDateFormat("yyyy-MM-dd").format(endDate);

        String addUserSQLString = "{call EventAdd('" + eventName + "','" + eventDescription + "','" + eventCountry + "','" + eventState + "','" + eventCity + "','" + eventZip + "','" + eventAddress + "','" + eventCreaterUsername + "','" + startDateF + "','" + endDateF + "')}";

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
            flash("Message", "Error creating event: " + e.getMessage());
            return redirect(
                    routes.AddNewEventController.renderViewAddEvent()
            );
        }
    }
}
