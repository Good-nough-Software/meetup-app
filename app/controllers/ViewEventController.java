package controllers;

import com.google.inject.Inject;
import play.mvc.Controller;
import play.mvc.Result;
import play.data.Form;
import play.data.FormFactory;
import play.data.DynamicForm;
import play.db.ebean.Transactional;
import models.Event;
import models.Relation;
import models.User;
import models.Search;
import java.util.List;
import java.util.ArrayList;

public class ViewEventController extends Controller {

    @Inject
    FormFactory formFactory;

    @Transactional
    private void attendEvent(int eventid) {
        Event event = Event.find.byId(eventid);
        if (event == null) {
            return;
        }

        int userid = Integer.parseInt(session().get("userid"));

        Relation relate = new Relation(userid, event);
        relate.save();
    }


    public Result viewEvent(int eventid) {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String attending = requestData.get("attend");
        if (attending != null) {
            int attendid = Integer.parseInt(attending);
            attendEvent(attendid);
        }

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
