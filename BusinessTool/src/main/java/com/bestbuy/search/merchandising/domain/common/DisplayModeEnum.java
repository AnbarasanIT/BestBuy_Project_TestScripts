package com.bestbuy.search.merchandising.domain.common;

/**
 * @author Kalaiselvi Jaganathan
 *
 */
public enum DisplayModeEnum {

	SEARCH(1), BROWSE(2), SEARCH_BROWSE(3);

	private int displayMode;
	private DisplayModeEnum(int displayMode){
		this.displayMode = displayMode;
	}

	public int getDisplayMode(){
		return displayMode;
	}

}