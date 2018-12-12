package controllers;

import io.ebean.SqlQuery;
import io.ebean.SqlRow;
import models.Search;
import forms.loginForm;
import forms.userProfileForm;
import io.ebean.Ebean;
import io.ebean.Transaction;
import models.Event;
import models.User;
import models.Location;
import play.Logger;
import views.html.viewUserProfile;
import models.Search;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;

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
        return UserProfile(); //ok(views.html.viewUserProfile.render(userProfileForm,null,null,null, formFactory.form(Search.class)));
    }

    public Result UserProfile() {
        Form<userProfileForm> userProfileForm = formFactory.form(userProfileForm.class);
        Form<loginForm> filledForm = formFactory.form(loginForm.class).bindFromRequest();
        String username = session("username");

        /*if(!filledForm.field("username").getValue().get().isEmpty()) {
            username = filledForm.field("username").getValue().get();
        }*/
        //session("username", username);

        int location = 0;
        int id = 0;
        String uname = null;
        String pass = null;
        String utype = null;
        String email = null;
        String name = null;
        String phone = null;
        Transaction trax = Ebean.beginTransaction();


        String sel2 = "SELECT *  FROM users WHERE username = '" + username + "'";
        SqlQuery q = Ebean.createSqlQuery(sel2);
        List<SqlRow> use = q.findList();
        if (use.isEmpty()) {
            //username does not exists
            Logger.debug("User does not exist");
            username = "jdoe";
        } else {
            //username taken
            username = session("username");// = filledForm.field("username").getValue().get();
        }

        String sel = "SELECT *  FROM users WHERE username = '" + username + "'";
        List<User> users = new ArrayList<>();


        try{
            Connection dbConnect = trax.getConnection();
            CallableStatement call = dbConnect.prepareCall(sel);
            ResultSet result = call.executeQuery();

            while(result.next()) {
                id = result.getInt("id");
                uname = result.getString("username");
                pass = result.getString("password");
                utype = result.getString("usertype");
                email = result.getString("email");
                name = result.getString("name");
                phone = result.getString("phone");
                location = result.getInt("location");
            }
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
                int id2 = location;
                String country = result.getString("country");
                String state = result.getString("state");
                String city = result.getString("city");
                String zip = result.getString("zip");
                String address = result.getString("address");

                Location homes = new Location(id2, country, state, city, zip, address);
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
        String select = "SELECT * FROM events WHERE userid = '"
                + id + "';";

        try{
            Connection dbConnect = tx.getConnection();
            CallableStatement call = dbConnect.prepareCall(select);
            ResultSet result = call.executeQuery();
            while(result.next()){
                int id3 = result.getInt("id");
                int locid = result.getInt("locationid");
                String sum = result.getString("summary");
                int userid = result.getInt("userid");
                Date strt = result.getDate("startDate");
                Date end = result.getDate("endDate");
                String name2 = result.getString("name");

                Event event = new Event(id3, locid, sum, userid, strt, end, name2);
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
        return ok("Locations: " + locations);



        if (session().get("username").equals("null")) {
            Form<userProfileForm> userProfileForm = formFactory.form(forms.userProfileForm.class);
            return ok(viewUserProfile.render(userProfileForm, null, "Username Taken", formFactory.form(Search.class)));
        } else {
            return ok("Locations: " + locations);
        }

        Relations rel = Relations.find.deleteById(id);
        */


        Result result = UserProfile();

        return result;
    }
}
