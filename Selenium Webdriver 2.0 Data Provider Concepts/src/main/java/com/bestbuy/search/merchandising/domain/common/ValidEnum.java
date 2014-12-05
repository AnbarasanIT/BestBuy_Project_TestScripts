package com.bestbuy.search.merchandising.domain.common;

/**
 * @author Kalaiselvi Jaganathan
 *
 */
public enum ValidEnum {

	Y("YES"),N("NO");

	private String display;

	private ValidEnum(String display){
		this.display = display;
	}

	public String getDisplay(){
		return display;
	}

}
