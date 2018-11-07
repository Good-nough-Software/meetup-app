package models;
/**
 * Location object to pull data from database
 */
public class Location {
  public String country;
  public String state;
  public String city;
  public String address;
  public String zip;

  public String toString() {
    return address + "\n" + city + " " + state + " " + zip + "\n" + country;
  }
}
