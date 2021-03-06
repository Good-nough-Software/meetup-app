package controllers;


import forms.loginForm;
import forms.userProfileForm;
import forms.Search;
import io.ebean.Ebean;
import io.ebean.Transaction;
import models.Event;
import models.Location;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.viewUserProfile;

import javax.inject.Inject;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserProfileController extends Controller {

    @Inject
    FormFactory formFactory;
    public Result renderViewUserProfile(){
        Form<userProfileForm> userProfileForm = formFactory.form(userProfileForm.class);
        return ok(viewUserProfile.render(userProfileForm, null,"", formFactory.form(Search.class))); //TODO fix the null
    }

    public Result UserProfile() {
        Form<userProfileForm> userProfileForm = formFactory.form(userProfileForm.class);
        Form<loginForm> filledForm = formFactory.form(loginForm.class).bindFromRequest();
        List<Location> locals = Location.find.all();

        Transaction tx = Ebean.beginTransaction();

        List<Event> matches = new ArrayList<>();

        String select = "SELECT id, locationid, summary, userid, startDate, endDate, name FROM events WHERE userid = '" + filledForm.get().getUsername() + "';";

        try{
            Connection dbConnect = tx.getConnection();
            CallableStatement call = dbConnect.prepareCall(select);
            ResultSet result = call.executeQuery();
            while(result.next()){
                int id = result.getInt("id");
                int locid = result.getInt("locationid");
                String sum = result.getString("summary");
                int userid = result.getInt("userid");
                Date strt = result.getDate("startDate");
                Date end = result.getDate("endDate");
                String name = result.getString("name");

                Event event = new Event(id, locid, sum, userid, strt, end, name);
                matches.add(event);
            }

        } catch (Exception e){
            Ebean.rollbackTransaction();
            e.printStackTrace();
        } finally {
            tx.end();
            Ebean.endTransaction();
        }


        return ok(viewUserProfile.render(userProfileForm, matches,"", formFactory.form(Search.class))); //TODO fix the error

        /*
        String locations = filledForm.field("locations").getValue().get();

        return ok("Locations: " + locations);
        */
    /*
        if (session().get("username").equals("null")) {
            Form<userProfileForm> userProfileForm = formFactory.form(forms.userProfileForm.class);
            return ok(viewUserProfile.render(userProfileForm, null, "Username Taken", formFactory.form(Search.class)));
        } else {
            return ok("Locations: " + locations);
        }

    */

    }
}
