package forms;

import play.data.validation.Constraints;

/**
 * This class is for getting search querys from
 */
public class Search {

  protected String search;

  public void setSearch(String search) {
    this.search = search;
  }

  public String getSearch() {
    return this.search;
  }
}
