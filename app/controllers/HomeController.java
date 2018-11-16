package controllers;

import com.google.inject.Inject;
import play.mvc.*;
import play.data.Form;
import play.data.FormFactory;
import io.ebean.Ebean;
import io.ebean.SqlQuery;
import io.ebean.SqlRow;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import models.Search;
import models.Location;
import play.db.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    @Inject
    FormFactory formFactory;

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        Form<Search> form = formFactory.form(Search.class);
        return ok(views.html.index.render(form));
    }

    public Result results() {
        Form<Search> resultsForm = formFactory.form(Search.class).bindFromRequest();
        Search search = resultsForm.get();
        String query = search.getSearch();

        if (query == null || query.isEmpty()) {
            return redirect("/home");
        }

        query = query.toLowerCase();

        List<Location> locations = Location.find.all();

        String[] tokens = query.split("\\s+");
        List<Location> matches = new ArrayList<Location>();
        for (String tok : tokens) {
            // Replace with queries to DB
            for (Location location: locations) {
                String address = location.toString();
                if (address.toLowerCase().contains(tok))
                    matches.add(location);
            }
        }

        Form<Search> form = formFactory.form(Search.class);
        return ok(views.html.results.render(form, matches, query));
    }
}
