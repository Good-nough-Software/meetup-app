package controllers;

import com.google.inject.Inject;
import models.resetPasswordForm;
import play.data.Form;
import play.data.FormFactory;
import play.libs.mailer.Email;
import play.libs.mailer.MailerClient;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.viewResetPassword;

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


    //renders reset password page
    @Inject
    FormFactory formFactory;
    //used to send email
    @Inject
    MailerClient mailerClient;

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

        //get random code
        String code = getSaltString();

        //send email to user specified email
        sendEmail(email, code);

        //sets session
        session("resetCode", code);

        //returns to same page, but in view there is a condition to change if the reset code is set
        return redirect(routes.resetPasswordController.renderViewResetPassword());
    }

    //after user enters code
    public Result sendResetPassword() {
        Form<resetPasswordForm> filledForm = formFactory.form(resetPasswordForm.class).bindFromRequest();
        String codeEntered = filledForm.get().getResetCode();


        //code should never be empty for session, only way they can enact this method is if
        //the sesson code is not empty

        //Checks if key user entered and key attached to session match
        if (!codeEntered.equals("") && codeEntered.equals(session("resetCode"))) {

            //check if email is associated with a user
            //reset password
            session("resetCode", "");
            return TODO;
        } else {
            flash("Message", "Incorrect code");
            return redirect(
                    routes.resetPasswordController.renderViewResetPassword()
            );
        }

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
