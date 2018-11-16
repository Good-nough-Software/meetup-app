package controllers;

import com.google.inject.Inject;
import io.ebean.Ebean;
import io.ebean.SqlQuery;
import io.ebean.SqlRow;
import models.resetPasswordForm;
import org.apache.commons.codec.digest.DigestUtils;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.db.Database;
import play.libs.mailer.Email;
import play.libs.mailer.MailerClient;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.viewResetPassword;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

/**
 * @author Lucas Buccilli
 * Class: newUserController.class
 * Date: 10/20/2018
 * Time: 6:40 PM
 * Project Name: meetup-app
 * File Name: newUserController
 */
public class resetPasswordController extends Controller {

    @Inject
    FormFactory formFactory;
    //used to send email
    @Inject
    MailerClient mailerClient;
    @Inject
    Database db;

    public Result renderViewResetPassword() {

        Form<resetPasswordForm> resetPasswordForm = formFactory.form(resetPasswordForm.class);

        //checks if the session resetCode has already been instantiated

        if (!session().containsKey("resetCode")) {
            session("resetCode", "");
        }

        Result ok = ok(viewResetPassword.render(resetPasswordForm));
        return ok;
    }

    public Result resetPassword() {


        Form<resetPasswordForm> filledForm = formFactory.form(resetPasswordForm.class).bindFromRequest();
        String email = filledForm.get().getEmail();

        String queryString = "SELECT * FROM users WHERE email = '" + email + "'";
        //returns list where username is username
        SqlQuery query = Ebean.createSqlQuery(queryString);


        List<SqlRow> rows = query.findList();

        Logger.debug(rows.toString());

        if (!rows.isEmpty()) {
            //get random code
            String code = getSaltString();

            //send email to user specified email
            sendEmail(email, code);

            //sets session
            session("resetCode", code);
            session("resetEmail", email);

            //returns to same page, but in view there is a condition to change if the reset code is set
            return redirect(routes.resetPasswordController.renderViewResetPassword());
        } else {
            flash("Message", "Email not found");
            session("resetCode", "");
            session().remove("resetEmail");
            return redirect(
                    routes.resetPasswordController.renderViewResetPassword()
            );
        }


    }

    //after user enters code
    public Result sendResetPassword() {
        Form<resetPasswordForm> filledForm = formFactory.form(resetPasswordForm.class).bindFromRequest();
        String codeEntered = filledForm.get().getResetCode();
        String newPassword = DigestUtils.sha1Hex(filledForm.get().getTempNewPassword());


        //check if password does not match confim password

        if (!filledForm.get().getTempNewPassword().equals(filledForm.get().getTempNewPasswordConfirm())) {
            flash("Message", "Passwords do not match");
            return redirect(
                    routes.resetPasswordController.renderViewResetPassword()
            );
        } else {


            //code should never be empty for session, only way they can enact this method is if
            //the sesson code is not empty

            //Checks if key user entered and key attached to session match
            if (!codeEntered.equals("") && codeEntered.equals(session("resetCode"))) {

                //check if email is associated with a user
                //reset password
                session("resetCode", "");

                //Must contain reset email so we can recover the password from db
                if (session().containsKey("resetEmail")) {
                    String email = session("resetEmail");
                    String queryString = "SELECT * FROM users WHERE email = '" + email + "'";
                    //returns list where username is username
                    SqlQuery query = Ebean.createSqlQuery(queryString);


                    List<SqlRow> rows = query.findList();

                    //extract username and current password from db
                    String password = rows.get(0).getString("password");
                    String username = rows.get(0).getString("username");


                    //UserPasswordChange(username, oldPassword, newPassword)
                    String resetPassword = "{call UserPasswordChange('" + username + "','" + password + "','" + newPassword + "')}";

                    try {
                        Connection con = db.getConnection();
                        Logger.debug("Preparing call: " + resetPassword);
                        CallableStatement stmt;
                        stmt = con.prepareCall(resetPassword);
                        Logger.debug("Prepared: " + stmt.toString());
                        stmt.execute();
                        Logger.debug("Executed");
                        //set username to user
                        session().remove("resetEmail");
                        flash("Message", "Your password has been reset");

                        return redirect(

                                routes.resetPasswordController.renderViewResetPassword()
                        );

                    } catch (SQLException e) {
                        Logger.debug(e.getMessage());
                    }


                } else {
                    response().discardCookie("resetEmail");
                    return badRequest("Error with procedure");
                }


            } else {
                flash("Message", "Incorrect code");
                return redirect(
                        routes.resetPasswordController.renderViewResetPassword()
                );
            }
        }
        response().discardCookie("resetEmail");
        return badRequest();

    }

    //generates random code
    private String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 6) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    private void sendEmail(String formEmail, String code) {
        Email email = new Email()
                .setSubject("Password Reset")
                .setFrom("Meet-Up Password Reset <no-reply@meetup.com>")
                .addTo("<" + formEmail + ">")
                .setBodyText("Use this code to reset your password: " + code);
        mailerClient.send(email);
    }
}
