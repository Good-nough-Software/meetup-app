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
  public int Location;

  @ManyToOne
  @JoinColumn(name="location")
  public Location location;

  public User(int ID, String uname, String pass, String utype, String mail, String nam, String phne, int loc){
    id = ID;
    username = uname;
    password = pass;
    usertype = utype;
    email = mail;
    name = nam;
    phone = phne;
    Location = loc;
  }

  public static final Finder<Integer, User> find = new Finder<>(User.class);

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
