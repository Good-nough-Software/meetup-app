package models;

import play.data.validation.Constraints;

import javax.persistence.Entity;

/**
 * This class is for getting search querys from
 */
@Entity
public class Search {

  protected String search;

  public void setSearch(String search) {
    this.search = search;
  }

  public String getSearch() {
    return this.search;
  }
}
