package controllers;

import com.google.inject.Inject;
import io.ebean.Ebean;
import io.ebean.SqlQuery;
import io.ebean.SqlRow;
import models.loginForm;
import models.newUserForm;
import org.apache.commons.codec.digest.DigestUtils;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.db.Database;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.viewNewUser;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Lucas Buccilli
 * Date: 10/20/2018
 * Time: 6:40 PM
 * Project Name: meetup-app
 * File Name: newUserController
 */
public class newUserController extends Controller {




    @Inject
    FormFactory formFactory;
    public Result renderViewNewUser(){

        Form<newUserForm> newUserForm = formFactory.form(newUserForm.class);
        return ok(viewNewUser.render(newUserForm));
    }

    @Inject
    Database db;
    public Result createNewUser() {
        //populate form from viewLogin page
        Form<loginForm> filledForm = formFactory.form(loginForm.class).bindFromRequest();
        //parses the data from the form
        String username = filledForm.field("username").getValue().get();
        String password = filledForm.field("password").getValue().get();
        String email = filledForm.field("email").getValue().get();
        String firstName = filledForm.field("firstName").getValue().get();
        String lastName = filledForm.field("lastName").getValue().get();


        String queryString = "SELECT * FROM users WHERE username = '" + username + "'";
        //returns list where username is username
        SqlQuery query = Ebean.createSqlQuery(queryString);


        List<SqlRow> rows = query.findList();

        if (rows.isEmpty()) {
            //username does not exists
            Logger.debug("User does not exist");
            String hashPassword = DigestUtils.sha1Hex(password);
            String addUserSQLString  = "{call UserAdd('" + username + "','" + email + "','" + hashPassword + "','" + firstName + " " + lastName + "')}";

            try {
                Connection con = db.getConnection();
                Logger.debug("Preparing call: " + addUserSQLString);
                CallableStatement userAddQuery;
                userAddQuery = con.prepareCall(addUserSQLString);
                Logger.debug("Prepared: " + userAddQuery.toString()) ;
                userAddQuery.execute();
                Logger.debug("Executed");
                //set username to user
                session("username", username);
                return redirect(
                        routes.HomeController.index()
                );

            } catch (SQLException e) {
                Logger.debug(e.getMessage());
                flash("Message", "Error creating user");
                return ok();
            }



        }else {
            //username taken
            flash("Message", "Username already exists");
            return redirect(
                    routes.newUserController.renderViewNewUser()
            );
        }
    }
}
