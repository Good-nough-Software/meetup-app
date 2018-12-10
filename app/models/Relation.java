package models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import io.ebean.Model;
import io.ebean.Finder;

/**
 * User object to pull data from database
 */
@Entity
@Table(name="relations")
public class Relation extends Model {

    @Id
    public int id;

    @ManyToOne
    @JoinColumn(name="eventid")
    public Event event;

    @ManyToOne
    @JoinColumn(name="userid")
    public User user;

    public static final Finder<Integer, Relation> find = new Finder<>(Relation.class);

    public boolean equals(Relation other) {
        if (this.id == other.id) {
            return true;
        }
        return false;
    }

    public String toString() {
        return user.toString() + "\n" + event.toString();
    }
}
