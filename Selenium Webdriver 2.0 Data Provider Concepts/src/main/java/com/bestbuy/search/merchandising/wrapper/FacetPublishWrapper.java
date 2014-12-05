
package com.bestbuy.search.merchandising.wrapper;

import java.util.List;

/**
 * Class that defines the Facet Json Structure
 * @author Lakshmi.Valluripalli
 */
public class FacetPublishWrapper {
	
	String category;
	List<FacetBaseWrapper> facets;
	
	/**
	 * returns the list of FacetBaseWrapper Data 
	 * @return
	 */
	public List<FacetBaseWrapper> getFacets() {
		return facets;
	}
	
	/**
	 * sets the list FacetBaseWrapper
	 * @param facets
	 */
	public void setFacets(List<FacetBaseWrapper> facets) {
		this.facets = facets;
	}
	
	/**
	 * get the category
	 * @return
	 */
	public String getCategory() {
		return category;
	}
	
	/**
	 * sets the category
	 * @param category
	 */
	public void setCategory(String category) {
		this.category = category;
	}
}
