package com.bestbuy.search.merchandising.domain.common;

/**
 * @author Kalaiselvi Jaganathan
 *
 */
public enum DisplayEnum {
	
	Y("DISPLAYED"),N("HIDDEN");
	
	private String display;
	 
	private DisplayEnum(String display){
		this.display = display;
	}
	
	public String getDisplay(){
		return display;
	}
	
}
