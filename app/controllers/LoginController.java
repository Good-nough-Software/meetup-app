package controllers;

import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author: Lucas Buccilli
 * Class: CS1131
 * Date: 10/15/18
 * Time: 2:21 PM
 * Project Name: meetup-app
 * File Name: LoginController
 */
public class LoginController extends Controller {

    public Result index() {
        return ok(views.html.Login.render());
    }

}
