package com.bestbuy.search.merchandising.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.bestbuy.search.merchandising.domain.CategoryFacet;

/**
 * @author Kalaiselvi Jaganathan
 * Wrapper class for Category Facet entity
 */
public class CategoryFacetWrapper implements IWrapper {

	private Long facetId;
	private String facetName;
	private String categoryId;
	private Long displayOrder;
	private String display;

	/**
	 * @return the facetId
	 */
	public Long getFacetId() {
		return facetId;
	}
	/**
	 * @param to set the facetId 
	 */
	public void setFacetId(Long facetId) {
		this.facetId = facetId;
	}
	/**
	 * @return the facetName
	 */
	public String getFacetName() {
		return facetName;
	}
	/**
	 * @param to set the facetName 
	 */
	public void setFacetName(String facetName) {
		this.facetName = facetName;
	}
	/**
	 * @return the categoryId
	 */
	public String getCategoryId() {
		return categoryId;
	}
	/**
	 * @param to set the categoryId 
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * @return the displayOrder
	 */
	public Long getDisplayOrder() {
		return displayOrder;
	}
	/**
	 * @param to set the displayOrder
	 */
	public void setDisplayOrder(Long displayOrder) {
		this.displayOrder = displayOrder;
	}
	
	/**
	 * @return the display
	 */
	public String getDisplay() {
		return display;
	}
	/**
	 * @param to set the display 
	 */
	public void setDisplay(String display) {
		this.display = display;
	}
	
	@Override
	public int compareTo(IWrapper arg0) {
		return 0;
	}
	/**
	 * Retrieve all the facets for a category and convert to categoryFacetWrapper
	 * @param categoryFacets
	 * @return categoryFacetWrappers
	 */
	public static List<IWrapper> getAllFacetsforCatg(List<CategoryFacet> categoryFacets) {
		List<IWrapper> categoryFacetWrappers = new ArrayList<IWrapper>(categoryFacets.size());
		CategoryFacetWrapper categoryFacetWrapper = null;

		//Get the facet entity data and set the facet wrapper
		for(CategoryFacet  categoryFacet : categoryFacets){
			categoryFacetWrapper = new CategoryFacetWrapper();
			if(categoryFacet.getCategoryNode().getCategoryNodeId() != null){
			categoryFacetWrapper.setCategoryId(categoryFacet.getCategoryNode().getCategoryNodeId());
			}
			
			if(categoryFacet.getFacet().getFacetId() != null){
			categoryFacetWrapper.setFacetId(categoryFacet.getFacet().getFacetId());
			}
			
			categoryFacetWrapper.setFacetName(categoryFacet.getFacet().getDisplayName()+"("+categoryFacet.getFacet().getSystemName()+")");
			 
			if(categoryFacet.getDisplayOrder() != null){
			categoryFacetWrapper.setDisplayOrder(categoryFacet.getDisplayOrder());
			}
			
			if(categoryFacet.getDisplay() != null){
			categoryFacetWrapper.setDisplay(categoryFacet.getDisplay().getDisplay());
			}
			
			categoryFacetWrappers.add(categoryFacetWrapper);
		}
		return categoryFacetWrappers;
	}

}
