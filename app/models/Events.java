package models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import io.ebean.*;

import java.sql.Date;

@Entity
@Table(name="events")
public class Events {

    @Id
    public int id;

    public int locationid;
    public String summary;
    public int userid;
    public Date startDate;
    public Date endDate;
    public String name;

    public Events(int ID, int locid, String sum, int user, Date start, Date end, String nme){
        id = ID;
        locationid = locid;
        summary = sum;
        userid = user;
        startDate = start;
        endDate = end;
        name = nme;
    }

    public static final Finder<Long, Events> find = new Finder<>(Events.class);

    public boolean equals(Events other){
        if(this.id == other.id){
            return true;
        }
        return false;
    }

    public String toString() {
        return name + "\n" + startDate + " - " + endDate + "\n" + summary;
    }
}
