package controllers;

import com.google.inject.Inject;
import play.mvc.Controller;
import play.mvc.Result;
import play.data.Form;
import play.data.FormFactory;
// import models.EventIdForm;
import models.Event;
import models.Relation;
import models.User;
import models.Search;
import java.util.List;
import java.util.ArrayList;

public class ViewEventController extends Controller {

    @Inject
    FormFactory formFactory;

    public Result event(String eventAddr) {
        int eventid = Integer.parseInt(eventAddr);

        if (eventid <= 0) {
            return redirect("/home");
        }

        Event event = Event.find.byId(eventid);

        List<Integer> userids = Relation.find.query()
            .select("user")
            .where().eq("eventid", eventid)
            .findSingleAttributeList();


        List<User> attendees = new ArrayList<User>();
        for (int id : userids) {
            attendees.add(User.find.byId(id));
        }

        Form<Search> searchForm = formFactory.form(Search.class);
        return ok(views.html.viewEvent.render(searchForm, event, attendees));
    }
}
