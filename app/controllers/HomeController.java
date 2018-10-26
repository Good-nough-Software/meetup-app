package controllers;

import play.mvc.*;
import java.util.List;
import java.util.ArrayList;
import models.Location;


/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(views.html.index.render());
    }

    public Result results() {
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


        return ok(views.html.results.render(locations));
    }
}
