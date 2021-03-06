package models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import io.ebean.Model;
import io.ebean.Finder;

import java.sql.Date;

@Entity
@Table(name="events")
public class Event extends Model {

    @Id
    public int id;

    @ManyToOne
    @JoinColumn(name="locationid")
    public Location location;

    public String summary;

    @ManyToOne
    @JoinColumn(name="userid")
    public User user;

    @Column(name="startDate")
    public Date startDate;

    @Column(name="endDate")
    public Date endDate;

    public String name;

    public Event(int id, int locid, String sum, int userid, Date start, Date end, String nme){
        this.id = id;
        this.summary = sum;
        this.user = User.find.byId(userid);
        this.startDate = start;
        this.endDate = end;
        this.name = nme;
        this.location = Location.find.byId(locid);
    }

    public static final Finder<Integer, Event> find = new Finder<>(Event.class);

    public boolean equals(Event other){
        if(this.id == other.id){
            return true;
        }
        return false;
    }

    public String toString() {
        return name + "\n" + startDate + " - " + endDate + "\n" + summary + "\n" + location.toString();
    }
}
