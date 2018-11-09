package models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import io.ebean.*;

/**
 * Location object to pull data from database
 */
@Entity
@Table(name="locations")
public class Location extends Model {

  @Id
  public int id;

  public String country;
  public String state;
  public String city;
  public String zip;
  public String address;

  public static final Finder<Long, Location> find = new Finder<>(Location.class);

  public boolean equals(Location other) {
    if (this.id == other.id) {
      return true;
    }
    return false;
  }

  public String toString() {
    return address + "\n" + city + " " + state + " " + zip + "\n" + country;
  }
}
