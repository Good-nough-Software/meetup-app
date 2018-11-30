package models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import io.ebean.Model;
import io.ebean.Finder;

/**
 * User object to pull data from database
 */
@Entity
@Table(name="users")
public class User extends Model {

  @Id
  public int id;

  public String username;
  public String password;
  public String usertype;
  public String email;
  public String name;
  public String phone;

  @ManyToOne
  public Location location;

  public static final Finder<Long, User> find = new Finder<>(User.class);

  public boolean equals(User other) {
    if (this.id == other.id) {
      return true;
    }
    return false;
  }

  public String toString() {
    return username + "\n" + password + " " + usertype + " " + email + "\n" + phone;
  }
}
