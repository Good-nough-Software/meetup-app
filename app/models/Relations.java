package models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import io.ebean.*;

/**
 * User object to pull data from database
 */
@Entity
@Table(name="relations")
public class Relations extends Model {

  @Id
  public int id;

  public int eventid;
  public int userid;

  public static final Finder<Long, Relations> find = new Finder<>(Relations.class);

  public boolean equals(Relations other) {
    if (this.id == other.id) {
      return true;
    }
    return false;
  }

  public String toString() {
    return eventid + "\n" + userid;
  }
}
