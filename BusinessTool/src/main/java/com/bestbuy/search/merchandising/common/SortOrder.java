/**
 * 
 */
package com.bestbuy.search.merchandising.common;

/**
 * @author Sreedhar Patlola
 */
public enum SortOrder {

  ASC("asc"), DESC("desc");

  private String type;

  private SortOrder(String name) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
  
  public static SortOrder fromValue(String v) {
    return valueOf(v);
}

}