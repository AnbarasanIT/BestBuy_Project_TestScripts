/**
 * 
 */
package com.bestbuy.search.merchandising.wrapper;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Sreedhar Patlola
 */
public class DuplicateCheckWrapper implements IWrapper {

  private String currentValue;

  private String inputFieldValue;

  private String columnName;

  /**
   * @return the currentValue
   */
  @JsonProperty("currentValue")
  public String getCurrentValue() {
    return currentValue;
  }

  /**
   * @param currentValue
   *          the currentValue to set
   */
  @JsonProperty("currentValue")
  public void setCurrentValue(String currentValue) {
    this.currentValue = currentValue;
  }

  /**
   * @return the inputFieldValue
   */
  @JsonProperty("newValue")
  public String getInputFieldValue() {
    return inputFieldValue;
  }

  /**
   * @param inputFieldValue
   *          the inputFieldValue to set
   */
  @JsonProperty("newValue")
  public void setInputFieldValue(String inputFieldValue) {
    this.inputFieldValue = inputFieldValue;
  }

  /**
   * @return the columnName
   */
  @JsonProperty("inputName")
  public String getColumnName() {
    return columnName;
  }

  /**
   * @param columnName
   *          the columnName to set
   */
  @JsonProperty("inputName")
  public void setColumnName(String columnName) {
    this.columnName = columnName;
  }

  @Override
  public int compareTo(IWrapper arg0) {

    return 0;
  }

}
