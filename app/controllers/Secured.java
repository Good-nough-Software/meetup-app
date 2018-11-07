package controllers;

/**
 * @author: Lucas Buccilli
 * Date: 11/3/18
 * Time: 8:31 PM
 * Project Name: meetup-app
 * File Name: Secured
 */
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("username");
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        return redirect(routes.LoginController.renderViewLogin());
    }
}
