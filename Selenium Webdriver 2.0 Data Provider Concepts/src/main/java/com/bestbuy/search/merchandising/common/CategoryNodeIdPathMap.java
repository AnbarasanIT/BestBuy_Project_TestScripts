package com.bestbuy.search.merchandising.common;

import java.util.Map;

/** 
 * @author Chanchal Kumari
 * The categoryNodeIdPathMap Map stores all the path to Id mapping for all the categories received .
 */

public class CategoryNodeIdPathMap {

	public static Map<String, String> categoryNodeIdPathMap;

	public static Map<String, String> getCategoryNodeIdPathMap() {
		return categoryNodeIdPathMap;
	}

	public static void setCategoryNodeIdPathMap(
			Map<String, String> tempCategoryNodeIdPathMap) {
		categoryNodeIdPathMap = tempCategoryNodeIdPathMap;
	}

}
