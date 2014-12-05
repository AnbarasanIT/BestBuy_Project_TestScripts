package com.bestbuy.search.merchandising.wrapper;

import java.util.List;

/**
 * @author Kalaiselvi Jaganathan
 *
 */
public class ContextBaseWrapper {

	private String categoryPath;
	private String applyToSubCategories;
	private String keywords;
	private List<ContextFacetBaseWrapper> facets;
	private String searchProfileType;
	/**
	 * @return the categoryPath
	 */
	public String getCategoryPath() {
		return categoryPath;
	}
	/**
	 * @param to set the categoryPath
	 */
	public void setCategoryPath(String categoryPath) {
		this.categoryPath = categoryPath;
	}
	
	/**
	 * @return the keywords
	 */
	public String getKeywords() {
		return keywords;
	}
	/**
	 * @param to set the keywords
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
	/**
	 * @return the searchProfileType
	 */
	public String getSearchProfileType() {
		return searchProfileType;
	}
	/**
	 * @param to set the searchProfileType
	 */
	public void setSearchProfileType(String searchProfileType) {
		this.searchProfileType = searchProfileType;
	}
	
	/**
	 * @return the applyToSubCategories
	 */
	public String getApplyToSubCategories() {
		return applyToSubCategories;
	}
	
	/**
	 * @param to set the applyToSubCategories
	 */
	public void setApplyToSubCategories(String applyToSubCategories) {
		this.applyToSubCategories = applyToSubCategories;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<ContextFacetBaseWrapper> getFacets() {
		return facets;
	}
	public void setFacets(List<ContextFacetBaseWrapper> facets) {
		this.facets = facets;
	}
	
}
