package controllers;

import com.google.inject.Inject;
import io.ebean.Ebean;
import io.ebean.Transaction;
import models.Event;
import models.Location;
import models.Search;
import models.userProfileForm;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Http;
import play.mvc.Result;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
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
    public Result renderNonCurrentUserProfileView(){
        Form<userProfileForm> nonCurrentUserForm = formFactory.form(userProfileForm.class);

        String userName;

        if (session().containsKey("nonUserProfileUsername")) {
            userName = session("nonUserProfileUsername");
            //get information from database
        }else {
            userName = "jsmith";
        }

        if(userName.equals(session("username"))){
            return redirect(routes.UserProfileController.renderViewUserProfile());
        }

        userProfileForm temp = new userProfileForm();
        temp.setUsername(userName);







        //gets information to populate user
        Transaction tx = Ebean.beginTransaction();
        String select = "SELECT id, email, name, location, phone FROM users WHERE username = '" + userName + "';";



        int locationId = -1;
        int userID = -1;

        try{
            Connection dbConnect = tx.getConnection();
            CallableStatement call = dbConnect.prepareCall(select);
            ResultSet result = call.executeQuery();
            while(result.next()){
                temp.setEmail(result.getString("email"));
                locationId = result.getInt("location");
                temp.setPhone(result.getString("phone"));
                temp.setName(result.getString("name"));
                userID = result.getInt("id");
            }

        } catch (Exception e){
            Ebean.rollbackTransaction();
            e.printStackTrace();
        } finally {
            tx.end();
            Ebean.endTransaction();
        }


        //add events user is attending

//        List<Event> matches = new ArrayList<>();
//        tx = Ebean.beginTransaction();
//        select = "SELECT id, locationid, summary, userid, startDate, endDate, name FROM events WHERE userid = '" + userID + "';";
//
//        try{
//            Connection dbConnect = tx.getConnection();
//            CallableStatement call = dbConnect.prepareCall(select);
//            ResultSet result = call.executeQuery();
//            while(result.next()){
//                int id = result.getInt("id");
//                int locid = result.getInt("locationid");
//                String sum = result.getString("summary");
//                int userid = result.getInt("userid");
//                Date strt = result.getDate("startDate");
//                Date end = result.getDate("endDate");
//                String name = result.getString("name");
//
//                Event event = new Event(id, locid, sum, userid, strt, end, name);
//                matches.add(event);
//            }
//
//        } catch (Exception e){
//            Ebean.rollbackTransaction();
//            e.printStackTrace();
//        } finally {
//            tx.end();
//            Ebean.endTransaction();
//        }
//
//
//        //Events, will look like shit
//        temp.setEvents(matches.toString());

        //gets location of user
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




        return ok(
                views.html.viewNonCurrentUserProfile.render(temp, formFactory.form(Search.class))
        );
    }
}
