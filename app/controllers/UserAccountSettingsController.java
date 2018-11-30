package controllers;

import models.Search;
import models.loginForm;
import models.userAccountSettingsForm;
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
import java.util.List;

public class UserAccountSettingsController extends Controller {

    @Inject
    FormFactory formFactory;
    public Result renderViewUserAccountSettings(){
        Form<userAccountSettingsForm> userAccountSettingsForm = formFactory.form(userAccountSettingsForm.class);
        return ok(viewUserAccountSettings.render(userAccountSettingsForm, "", formFactory.form(Search.class)));
    }

    public Result UserAccountSettings() {
        Form<loginForm> filledForm = formFactory.form(loginForm.class).bindFromRequest();

        String username = filledForm.field("username").getValue().get();
        String password = filledForm.field("password").getValue().get();
        String name = filledForm.field("name").getValue().get();
        String email = filledForm.field("email").getValue().get();
        String phone = filledForm.field("phone").getValue().get();

        Form<userAccountSettingsForm> userAccountSettingsForm = formFactory.form(userAccountSettingsForm.class);


        String queryString = "SELECT * FROM users WHERE username = '" + username + "'";
        SqlQuery query = Ebean.createSqlQuery(queryString);
        List<SqlRow> rows = query.findList();

       Transaction tx = Ebean.beginTransaction();

            String call = "CALL UPDATE_USER(?, ?, ?, ?, ?)";

            try{
                Connection dbConnect = tx.getConnection();
                CallableStatement callable = dbConnect.prepareCall(call);

                callable.setString(1, username);
                callable.setString(2, password);
                callable.setString(3, name);
                callable.setString(4, email);
                callable.setString(5, phone);

                callable.execute();

                session("username", username);
                flash("Message", "User Information Updated");

            }catch(Exception e){
                Ebean.rollbackTransaction();
                e.printStackTrace();
                flash("Message", "Error in Updating Information");
            } finally {
                Ebean.endTransaction();
                flash("Message", "User Information Updated");
                return ok(views.html.viewUserAccountSettings.render(userAccountSettingsForm, "User Info Updated"));
            }

    }

}
