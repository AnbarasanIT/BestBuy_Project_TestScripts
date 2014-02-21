/**
 * 
 */
package com.bestbuy.search.merchandising.common;

/**
 * @author Chanchal.Kumari
 */

public enum ErrorType {
	
	CATEGORY_TREE("CATEGORY_TREE"),PUBLISHING("PUBLISHING"),ATTRIBUTES_JOB("ATTRIBUTES_JOB"),APPLICATION("APPLICATION");
	
	 private String errorType;

	  private ErrorType(String errorType) {
	    this.errorType = errorType;
	  }

	  public String getErrorType() {
	    return errorType;
	  }
	  
	  public static ErrorType fromValue(String v) {
	    return valueOf(v);
	}
	

}
