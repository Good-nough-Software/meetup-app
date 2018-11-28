package models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import io.ebean.*;

/**
 * User object to pull data from database
 */
@Entity
@Table(name="friends")
public class Friends extends Model {

  @Id
  public int id;

  public int userid;
  public int friendid;
	public String status;

  public static final Finder<Long, Friends> find = new Finder<>(Friends.class);

  public boolean equals(Friends other) {
    if (this.id == other.id) {
      return true;
    }
    return false;
  }

  public String toString() {
    return userid + " " + friendid + " " + status;
  }
}
