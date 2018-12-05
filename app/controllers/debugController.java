package controllers;

import play.mvc.Controller;
import play.mvc.Result;

import static play.mvc.Controller.request;
import static play.mvc.Controller.session;
import static play.mvc.Results.redirect;

/**
 * @author: Lucas Buccilli
 * Date: 11/7/18
 * Time: 2:04 PM
 * Project Name: meetup-app
 * File Name: debugController
 */
public class debugController {

    public Result main(){

        Controller.flash("debug", session().toString());
        String refererUrl = request().getHeader("referer");

                return redirect(refererUrl);

    }
}
