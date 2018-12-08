package controllers;

import models.Search;
import models.Event;
import models.User;
import models.Location;
import models.userProfileForm;
import views.html.viewUserProfile;
import models.loginForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;

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
        return ok(views.html.viewUserProfile.render(userProfileForm, null, null,null, formFactory.form(Search.class)));
    }

    public Result UserProfile() {
        Form<userProfileForm> userProfileForm = formFactory.form(userProfileForm.class);
        Form<loginForm> filledForm = formFactory.form(loginForm.class).bindFromRequest();
        String username = filledForm.field("username").getValue().get();
       // session("username", username);

        int location = 0;
        Transaction trax = Ebean.beginTransaction();

        List<User> users = new ArrayList<>();
        String sel = "SELECT id, username, password, usertype, email, name, phone, location  FROM user WHERE username = '" + username + "'";
        try{
            Connection dbConnect = trax.getConnection();
            CallableStatement call = dbConnect.prepareCall(sel);
            ResultSet result = call.executeQuery();
            int id = result.getInt("id");
            String uname = result.getString("username");
            String pass = result.getString("password");
            String utype = result.getString("usertype");
            String email = result.getString("email");
            String name = result.getString("name");
            String phone = result.getString("phone");
            location = result.getInt("location");

            User user = new User(id, uname, pass, utype, email, name, phone, location);
            users.add(user);

        } catch (Exception e){
            Ebean.rollbackTransaction();
            e.printStackTrace();
        } finally {
            trax.end();
            Ebean.endTransaction();
        }

        Transaction trx = Ebean.beginTransaction();

        List<Location> home = new ArrayList<>();
        String selec = "SELECT * FROM locations WHERE id = '" + location + "'";
        try{
            Connection dbConnect = trx.getConnection();
            CallableStatement call = dbConnect.prepareCall(selec);
            ResultSet result = call.executeQuery();
            while (result.next()){
                int id = location;
                String country = result.getString("country");
                String state = result.getString("state");
                String city = result.getString("city");
                String zip = result.getString("zip");
                String address = result.getString("address");

                Location homes = new Location(id, country, state, city, zip, address);
                home.add(homes);
            }
        } catch (Exception e){
            Ebean.rollbackTransaction();
            e.printStackTrace();
        } finally {
            trax.end();
            Ebean.endTransaction();
        }

        Transaction tx = Ebean.beginTransaction();
        List<Event> matches = new ArrayList<>();
        String select = "SELECT id, locationid, summary, userid, startDate, endDate, name FROM events WHERE userid = '"
                + filledForm.get().getUsername() + "';";

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


        return ok(viewUserProfile.render(userProfileForm, users, home, matches, formFactory.form(Search.class)));
    }

    public Result removeEvent(int eid){
        //int eid = Integer.parseInt(eventid);
        Form<loginForm> filledForm = formFactory.form(loginForm.class).bindFromRequest();

        int userid = Integer.parseInt(filledForm.field("userid").getValue().get());

        Transaction tx = Ebean.beginTransaction();

        String query = "CALL RemoveRelation(?, ?)";

        try{
            Connection connect = tx.getConnection();
            CallableStatement call = connect.prepareCall(query);

            call.setInt(1, eid);
            call.setInt(2, userid);

            call.executeQuery();

        }catch (Exception e) {
            Ebean.rollbackTransaction();
            e.printStackTrace();
        } finally{
            tx.end();
            Ebean.endTransaction();
        }
/*
        int id = 0;
        Transaction trax = Ebean.beginTransaction();

        String queue = "SELECT id FROM relations WHERE eventid = " + eid + " AND userid = " + userid;

        try{
            Connection connect = tx.getConnection();
            CallableStatement call = connect.prepareCall(queue);

            ResultSet res = call.executeQuery();

            while (res.next()){
                id = res.getInt("id");
            }

        }catch (Exception e) {
            Ebean.rollbackTransaction();
            e.printStackTrace();
        } finally{
            trax.end();
            Ebean.endTransaction();
        }

        Relations rel = Relations.find.deleteById(id);
        */


        Result result = UserProfile();

        return result;
    }
}
