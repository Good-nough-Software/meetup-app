package controllers;

import com.google.inject.Inject;
import forms.userProfileForm;
import forms.Search;
import io.ebean.Ebean;
import io.ebean.SqlQuery;
import io.ebean.SqlRow;
import io.ebean.Transaction;
import play.Logger;
import play.data.FormFactory;
import play.mvc.Result;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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

    public Result renderNonCurrentUserProfileView(String usernameFromRequest) {

        String userName;

        //check if the session has the key, prevents null pointer error
        //if user has he session key set the username
        //if not set it to a generic one
        String queryString = "SELECT * FROM users WHERE username = '" + usernameFromRequest + "'";
        //returns list where username is username
        SqlQuery query = Ebean.createSqlQuery(queryString);


        List<SqlRow> rows = query.findList();

        if (rows.isEmpty()) {
            //username does not exists
            Logger.debug("User does not exist");
            userName = "jdoe";
        } else {
            //username taken
            userName = usernameFromRequest;
        }


        //check if user is looking at there own profile
        //if so direct to their profile page
        if (userName.equals(session("username"))) {
            return redirect(routes.UserProfileController.renderViewUserProfile());
        }

        //form to be filled
        userProfileForm temp = new userProfileForm();
        temp.setUsername(userName);


        //gets information to populate user
        Transaction tx = Ebean.beginTransaction();
        String select = "SELECT id, email, name, location, phone FROM users WHERE username = '" + userName + "';";


        //user id is id from users table
        int locationId = -1;
        int userID = -1;

        try {
            Connection dbConnect = tx.getConnection();
            CallableStatement call = dbConnect.prepareCall(select);
            ResultSet result = call.executeQuery();
            while (result.next()) {
                temp.setEmail(result.getString("email"));
                locationId = result.getInt("location");
                temp.setPhone(result.getString("phone"));
                temp.setName(result.getString("name"));
                userID = result.getInt("id");
            }

        } catch (Exception e) {
            Ebean.rollbackTransaction();
            e.printStackTrace();
        } finally {
            tx.end();
            Ebean.endTransaction();
        }


        //add events user is attending


        List<Integer> eventIds = new ArrayList<Integer>();

        tx = Ebean.beginTransaction();
        select = "SELECT eventid FROM relations WHERE userid = '" + userID + "';";

        try {
            Connection dbConnect = tx.getConnection();
            CallableStatement call = dbConnect.prepareCall(select);
            ResultSet result = call.executeQuery();
            while (result.next()) {
                int eventID = result.getInt("eventid");
                eventIds.add(eventID);
            }

        } catch (Exception e) {
            Ebean.rollbackTransaction();
            e.printStackTrace();
        } finally {
            tx.end();
            Ebean.endTransaction();
        }


        ArrayList<String> events = new ArrayList<String>();

        for (Integer eventIDS : eventIds) {

            tx = Ebean.beginTransaction();
            select = "SELECT name FROM events WHERE id = '" + eventIDS + "';";

            try {
                Connection dbConnect = tx.getConnection();
                CallableStatement call = dbConnect.prepareCall(select);
                ResultSet result = call.executeQuery();
                while (result.next()) {
                    String eventID = result.getString("name");
                    events.add(eventID);
                }

            } catch (Exception e) {
                Ebean.rollbackTransaction();
                e.printStackTrace();
            } finally {
                tx.end();
                Ebean.endTransaction();
            }
        }

        temp.setEvents(events);
        Logger.debug(events.toString());



        //gets location of user if location id was found
        if (locationId != -1) {

            tx = Ebean.beginTransaction();
            select = "SELECT country, state, city, zip, address FROM locations WHERE id = '" + Integer.toString(locationId) + "';";


            try {
                Connection dbConnect = tx.getConnection();
                CallableStatement call = dbConnect.prepareCall(select);
                ResultSet result = call.executeQuery();
                while (result.next()) {
                    temp.setCountry(result.getString("country"));
                    temp.setState(result.getString("state"));
                    temp.setCity(result.getString("city"));
                    temp.setZip(result.getString("zip"));
                    temp.setAddress(result.getString("address"));
                }

            } catch (Exception e) {
                Ebean.rollbackTransaction();
                e.printStackTrace();
            } finally {
                tx.end();
                Ebean.endTransaction();
            }
        }


        //passes in our filled form
        return ok(
                views.html.viewNonCurrentUserProfile.render(temp, formFactory.form(Search.class))
        );
    }
}
