package controllers;


import forms.Search;
import forms.userProfileForm;
import io.ebean.Ebean;
import io.ebean.Transaction;
import play.Logger;
import play.api.db.Database;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.viewUserProfile;

import javax.inject.Inject;
import java.sql.*;

public class UserProfileController extends Controller {

    int locationID = -1;

    @Inject
    FormFactory formFactory;
    public Result renderViewUserProfile(){

        userProfileForm userProfileForm = new userProfileForm();

        Transaction tx = Ebean.beginTransaction();

        String select = "SELECT email, name, phone, location FROM users WHERE username = '" + session().get("username") + "';";

        Logger.debug(select);
        userProfileForm.setUsername(session().get("username"));

        try {
            Connection dbConnect = tx.getConnection();
            CallableStatement call = dbConnect.prepareCall(select);
            ResultSet result = call.executeQuery();
            while (result.next()) {

                userProfileForm.setName(result.getString("name"));
                userProfileForm.setPhone(result.getString("phone"));
                userProfileForm.setEmail(result.getString("email"));
                locationID = result.getInt("location");
                Logger.debug("Location ID from loading " + locationID);
            }

        } catch (Exception e) {
            Ebean.rollbackTransaction();
            e.printStackTrace();
        } finally {
            tx.end();
            Ebean.endTransaction();
        }

        if (locationID == -1) {
            locationID = 0;
        }

        tx = Ebean.beginTransaction();


        select = "SELECT country, state, city, zip, address FROM locations WHERE id = '" + locationID + "';";

        Logger.debug(select);
        try {
            Connection dbConnect = tx.getConnection();
            CallableStatement call = dbConnect.prepareCall(select);
            ResultSet result = call.executeQuery();
            while (result.next()) {
                userProfileForm.setAddress(result.getString("address"));
                userProfileForm.setCountry(result.getString("country"));
                userProfileForm.setState(result.getString("state"));
                userProfileForm.setZip(result.getString("zip"));
                userProfileForm.setCity(result.getString("city"));
            }

        } catch (Exception e) {
            Ebean.rollbackTransaction();
            e.printStackTrace();
            flash("Message", e.getMessage());
            return redirect(
                    routes.UserProfileController.renderViewUserProfile()
            );
        } finally {
            tx.end();
            Ebean.endTransaction();
        }
        Logger.debug("Done Loading info");

        Form<userProfileForm> form = formFactory.form(userProfileForm.class).fill(userProfileForm);

        return ok(viewUserProfile.render(form, "", formFactory.form(Search.class)));
    }


    @Inject
    Database db;

    public Result UpdateUserProfile() {
        Logger.debug("Starting update");


        Connection conn = db.getConnection();

        try {
            PreparedStatement select = conn.prepareStatement(
                    "SELECT location FROM users WHERE username = ?;"
            );

            select.setString(1, session().get("username"));
            Logger.debug("Select" + select.toString());
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                locationID = rs.getInt("location");
            }
        } catch (Exception e) {
            Logger.debug(e.getMessage());
            flash("Message", e.getMessage());
            try {
                conn.close();
            }catch (Exception f){}
            return redirect(
                    routes.UserProfileController.renderViewUserProfile()
            );
        }

        Logger.debug("Location Id start of execution " + locationID);
        Form<userProfileForm> filledForm = formFactory.form(userProfileForm.class).bindFromRequest();
        filledForm.get().setUsername(session().get("username"));

//        UpdateUser
//                (parameter_username varchar(32),
//                        parameter_password varchar(40),
//                        parameter_name varchar(32),
//                        parameter_email varchar(32),
//                        parameter_phone char(11))

        //update users set password = parameter_password where users.username = parameter_username;

        String addUserSQLString = "{call UpdateUser('" + filledForm.get().getUsername() + "','" + "','" + filledForm.get().getName() + "','" + filledForm.get().getEmail() + "','" + filledForm.get().getPhone() + "')}";

        try {
            Connection con = db.getConnection();
            Logger.debug("Preparing call: " + addUserSQLString);
            CallableStatement userAddQuery;
            userAddQuery = con.prepareCall(addUserSQLString);
            Logger.debug("Prepared: " + userAddQuery.toString());
            userAddQuery.execute();
            Logger.debug("Executed");

        } catch (SQLException e) {
            Logger.debug(e.getMessage());
            flash("Message", "Error updating user");
            try {
                conn.close();
            }catch (Exception n){}
            return ok();
        }


        Logger.debug("LocationID " + Integer.toString(locationID));


        //user does not have a location
        if (locationID == 0) {

            //insert into users (username, email, password, name) values(parameter_username, parameter_email, parameter_password, parameter_name);
            //country VARCHAR(20),
            //       state   VARCHAR(2),
            //       city    VARCHAR(32),
            //       zip     VARCHAR(9),
            //       address VARCHAR(64),
            try {

//                //check if location exists
//                PreparedStatement select = conn.prepareCall(
//                        "SELECT id FROM locations WHERE country = ? AND state = ? AND city = ? AND zip = ? AND address = ? ;"
//                );
//
//                select.setString(1, filledForm.get().getCountry());
//                select.setString(2, filledForm.get().getState());
//                select.setString(3, filledForm.get().getCity());
//                select.setString(4, filledForm.get().getZip());
//                select.setString(5, filledForm.get().getAddress());
//                Logger.debug("Select" + select.toString());
//                ResultSet rs = select.executeQuery();
//                while (rs.next()) {
//                    locationID = rs.getInt("id");
//                    Logger.debug("Location Found at " + locationID);
//                }
//
//                if(locationID == 0) {

                PreparedStatement insert = conn.prepareStatement
                        ("INSERT into locations (country, state, city, zip, address) values(?, ?, ?, ?, ?);");
                insert.setString(1, filledForm.get().getCountry());
                insert.setString(2, filledForm.get().getState());
                insert.setString(3, filledForm.get().getCity());
                insert.setString(4, filledForm.get().getZip());
                insert.setString(5, filledForm.get().getAddress());
                insert.execute();

                PreparedStatement select = conn.prepareCall(
                        "SELECT id FROM locations WHERE country = ? AND state = ? AND city = ? AND zip = ? AND address = ? ;"
                );

                select.setString(1, filledForm.get().getCountry());
                select.setString(2, filledForm.get().getState());
                select.setString(3, filledForm.get().getCity());
                select.setString(4, filledForm.get().getZip());
                select.setString(5, filledForm.get().getAddress());
                Logger.debug("Select" + select.toString());
                ResultSet rs = select.executeQuery();
                while (rs.next()) {
                    locationID = rs.getInt("id");
                }


                PreparedStatement update = conn.prepareCall(
                        "UPDATE users SET location = ? WHERE username = ?;"
                );
                update.setInt(1, locationID);
                update.setString(2, session().get("username"));
                update.execute();


            } catch (Exception e) {
                Logger.debug(e.getMessage());
                flash("Message", e.getMessage());
                try {
                    conn.close();
                }catch (Exception n){}
                return redirect(
                        routes.UserProfileController.renderViewUserProfile()
                );

            }

        } else {
            Logger.debug("User has location");
            try {
                PreparedStatement update = conn.prepareStatement
                        ("UPDATE locations SET state = ?, city = ?, address = ?, zip = ?, country = ? WHERE id = ?;");

                update.setString(1, filledForm.get().getState());
                update.setString(2, filledForm.get().getCity());
                update.setString(3, filledForm.get().getAddress());
                update.setString(4, filledForm.get().getZip());
                update.setString(5, filledForm.get().getCountry());
                update.setInt(6, locationID);
                Logger.debug(update.toString());
                update.executeUpdate();

            } catch (Exception e) {
                Logger.debug(e.getMessage());
                flash("Message", e.getMessage());
                try {
                    conn.close();
                }catch (Exception n){}
                return redirect(
                        routes.UserProfileController.renderViewUserProfile()
                );

            }


        }
        try {
            conn.close();
        }catch (Exception e){}


        return redirect(
                routes.UserProfileController.renderViewUserProfile()
        );






    }
}
