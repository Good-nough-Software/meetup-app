package models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import io.ebean.*;

/**
 * Location object to pull data from database
 */
@Entity
@Table(name="events")
public class Event extends Model {

    @Id
    public int id;
    public int locationid;
    public Location location;
    public String name;
    public String summary;
    public Date startDate;
    public Date endDate;

    public static final Finder<Long, Event> find = new Finder<>(Event.class);
}
