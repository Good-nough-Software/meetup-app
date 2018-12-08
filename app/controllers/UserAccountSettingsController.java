package controllers;

import models.Search;
import models.loginForm;
import models.userAccountSettingsForm;
import org.apache.commons.codec.digest.DigestUtils;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.viewUserAccountSettings;

import javax.inject.Inject;

import io.ebean.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class UserAccountSettingsController extends Controller {

    @Inject
    FormFactory formFactory;
    public Result renderViewUserAccountSettings(){
        Form<userAccountSettingsForm> userAccountSettingsForm = formFactory.form(userAccountSettingsForm.class);
        return ok(viewUserAccountSettings.render(userAccountSettingsForm, "",
                formFactory.form(Search.class)));
    }

    public Result UserAccountSettings() {
        Form<loginForm> filledForm = formFactory.form(loginForm.class).bindFromRequest();
        Form<userAccountSettingsForm> filled = formFactory.form(userAccountSettingsForm.class).bindFromRequest();


        String oldUsername = filledForm.field("username").getValue().get();
        String newUsername = filled.field("username").getValue().get();
        String newPassword = filled.field("password").getValue().get();
        String name = filled.field("name").getValue().get();
        String email = filled.field("email").getValue().get();
        String phone = filled.field("phone").getValue().get();
        String address = filled.field("address").getValue().get();
        String zip = filled.field("zip").getValue().get();
        String city = filled.field("city").getValue().get();
        String state = filled.field("state").getValue().get();
        String country = filled.field("country").getValue().get();

        String hashPass = DigestUtils.sha1Hex(newPassword);


        String location = null;

        Form<userAccountSettingsForm> userAccountSettingsForm = formFactory.form(userAccountSettingsForm.class);

        session("username", oldUsername);
/*
        String queryString = "SELECT * FROM users WHERE username = '" + username + "'";
        SqlQuery query = Ebean.createSqlQuery(queryString);
        List<SqlRow> rows = query.findList();
*/
        //Changes the User's home location
        if(address != null || zip != null || city != null || state != null || country != null) {
            Transaction trax = Ebean.beginTransaction();

            String calls = "CALL LocationAdd(?, ?, ?, ?, ?)";

            try {
                Connection dbConnect = trax.getConnection();
                CallableStatement callable = dbConnect.prepareCall(calls);

                callable.setString(1, country);
                callable.setString(2, state);
                callable.setString(3, city);
                callable.setString(4, zip);
                callable.setString(5, address);

                ResultSet result = callable.executeQuery();

                while (result.next()) {
                    location = result.getString("id");
                }

                flash("Message", "User Information Updated");

            } catch (Exception e) {
                Ebean.rollbackTransaction();
                e.printStackTrace();
                flash("Message", "Error in Updating Information");
            } finally {
                Ebean.endTransaction();
                flash("Message", "User Information Updated");
            }
        }


        //Updates the User Info in the Database
        if(newUsername != null || newPassword != null || name != null || email != null ||
                phone != null || location != null) {
            Transaction tx = Ebean.beginTransaction();

            String call = "CALL UpdateUser(?, ?, ?, ?, ?, ?)";

            try {
                Connection dbConnect = tx.getConnection();
                CallableStatement callable = dbConnect.prepareCall(call);

                callable.setString(1, newUsername);
                callable.setString(2, hashPass);
                callable.setString(3, name);
                callable.setString(4, email);
                callable.setString(5, phone);
                callable.setString(6, location);

                callable.execute();

                flash("Message", "User Information Updated");

            } catch (Exception e) {
                Ebean.rollbackTransaction();
                e.printStackTrace();
                flash("Message", "Error in Updating Information");
            } finally {
                Ebean.endTransaction();
                flash("Message", "User Information Updated");
            }
        }

        return ok(views.html.viewUserAccountSettings.render(userAccountSettingsForm,
                "User Info Updated", formFactory.form(Search.class)));

    }

}
