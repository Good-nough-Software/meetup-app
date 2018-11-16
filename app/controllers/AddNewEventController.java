package controllers;

import com.google.inject.Inject;
import models.newEventForm;
import play.data.FormFactory;
import play.mvc.Result;
import views.html.viewAddEvent;

import static play.mvc.Results.ok;

/**
 * @author: Lucas Buccilli
 * Date: 11/16/18
 * Time: 2:59 PM
 * Project Name: meetup-app
 * File Name: AddNewEventController
 */
public class AddNewEventController {

    @Inject
    FormFactory formFactory;

    public Result renderViewAddEvent() {
        Result ok = ok(
                viewAddEvent.render(formFactory.form(newEventForm.class))
        );
        return ok;
    }
}
