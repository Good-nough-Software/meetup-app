package controllers;


import models.Search;
import models.Event;
import models.Location;
import models.Relations;
import models.userProfileForm;
import views.html.viewUserProfile;
import models.loginForm;
import models.userProfileForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;
import views.html.viewUserProfile;

import javax.inject.Inject;

import io.ebean.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

public class UserProfileController extends Controller {

    @Inject
    FormFactory formFactory;
    public Result renderViewUserProfile(){
        Form<userProfileForm> userProfileForm = formFactory.form(userProfileForm.class);
        return ok(viewUserProfile.render(userProfileForm, null,"", formFactory.form(Search.class)));
    }

    public Result UserProfile() {
        Form<userProfileForm> userProfileForm = formFactory.form(userProfileForm.class);
        Form<loginForm> filledForm = formFactory.form(loginForm.class).bindFromRequest();
        List<Location> locals = Location.find.all();

        Transaction tx = Ebean.beginTransaction();

        List<Event> matches = new ArrayList<>();

        String select = "SELECT id, locationid, summary, userid, startDate, endDate, name FROM events WHERE userid = '" + filledForm.get().getUsername() + "';";

        // Be able to remove events (buttons)

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
    }

    public Result removeEvent(int eid){
        Form<userProfileForm> userProfileForm = formFactory.form(userProfileForm.class);
        Form<loginForm> filledForm = formFactory.form(loginForm.class).bindFromRequest();

        String userid = filledForm.field("userid").getValue().get();
        long id = 0;

      //  Relations rel = Relations.find.byId(eid);

        Transaction tx = Ebean.beginTransaction();

        List<Event> matches = new ArrayList<>();
        String query = "SELECT id FROM relations WHERE eventid = " + eid + " AND userid = " + userid;

        try{
            Connection connect = tx.getConnection();
            CallableStatement call = connect.prepareCall(query);

            ResultSet res = call.executeQuery();

            while (res.next()){
                id = res.getLong("id");
            }

        }catch (Exception e) {
            Ebean.rollbackTransaction();
            e.printStackTrace();
        } finally{
            tx.end();
            Ebean.endTransaction();
        }





        //Relations rel = Relations.find.deleteById(id);

        return ok(viewUserProfile.render(userProfileForm, matches, "", formFactory.form(Search.class))); //TODO fix the error
    }
}
