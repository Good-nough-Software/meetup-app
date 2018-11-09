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
        if (query == null) {
            return index();
        }

        // Test Data
        List<Location> locations = new ArrayList<Location>();
        Location l = new Location();
        l.country = "United States";
        l.city = "Houghton";
        l.zip = "49931";
        l.state = "MI";
        l.address = "65 Isle Royale St";
        locations.add(l);
        Location l2 = new Location();
        l2.country = "United States";
        l2.city = "Brighton";
        l2.zip = "2391";
        l2.state = "MI";
        l2.address = "2391 Pepperidge Trl";
        locations.add(l2);

        // Pull from databases instead
        // if (query.matches("[\s]")) {
        //     String[] tokens = query.split("\\s+");
        // } else {
        //     String[] tokens = {query};
        // }

        // // Very simple weighted search
        // HashMap<Location,Integer> weight = new HashMap<Location,Integer>();
        // for (String tok: tokens) {
        //     String sqlstr = "SELECT id FROM locations WHERE country LIKE " + tok;
        //     SqlQuery sqlquery = Ebean.createSqlQuery(sqlstr);

        //     sqlstr = "SELECT id FROM locations WHERE city LIKE " + tok;
        //     sqlquery = Ebean.createSqlQuery(sqlstr);

        //     sqlstr = "SELECT id FROM locations WHERE state LIKE " + tok;
        //     sqlquery = Ebean.createSqlQuery(sqlstr);

        //     sqlstr = "SELECT id FROM locations WHERE zip LIKE " + tok;
        //     sqlquery = Ebean.createSqlQuery(sqlstr);

        //     sqlstr = "SELECT id FROM locations WHERE address LIKE " + tok;
        //     sqlquery = Ebean.createSqlQuery(sqlstr);
        // }

        // String dbquery = "SELECT id FROM locations WHERE LIKE ";

        List<Location> matches = new ArrayList<Location>();
        for (Location location: locations) {
            String address = location.toString();
            if (address.contains(query))
                matches.add(location);
        }

        Form<Search> form = formFactory.form(Search.class);
        return ok(views.html.results.render(form, matches, query));
    }
}
