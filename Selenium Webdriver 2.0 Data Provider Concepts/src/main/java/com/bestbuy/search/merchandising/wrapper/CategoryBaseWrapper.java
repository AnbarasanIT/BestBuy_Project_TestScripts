package com.bestbuy.search.merchandising.wrapper;

/**
 * @author Lakshmi Valluripalli
 *
 */
public class CategoryBaseWrapper {
	
	private String categoryPath;
	private String depFacet;
	private String depFacetField;
	private String applySubCategory;
	private String displayContext;
	private String displayDepFacet;
	
	
	/**
	 * @return the categoryPath
	 */
	public String getCategoryPath() {
		return categoryPath;
	}
	
	/**
	 * @param To set the categoryPath
	 */
	public void setCategoryPath(String categoryPath) {
		this.categoryPath = categoryPath;
	}
	
	/**
	 * @return the applySubCategory
	 */
	public String getApplySubCategory() {
		return applySubCategory;
	}
	
	/**
	 * @param applySubCategory the applySubCategory to set
	 */
	public void setApplySubCategory(String applySubCategory) {
		this.applySubCategory = applySubCategory;
	}
	
	/**
	 * @return the displayContext
	 */
	public String getDisplayContext() {
		return displayContext;
	}
	
	/**
	 * @param displayContext the displayContext to set
	 */
	public void setDisplayContext(String displayContext) {
		this.displayContext = displayContext;
	}
	
	/**
	 * returns the Dependent Facet Display Status
	 * @return
	 */
	public String getDisplayDepFacet() {
		return displayDepFacet;
	}
	
	/**
	 * sets the Dependent Facet display Status
	 * @param displayDepFacet
	 */
	public void setDisplayDepFacet(String displayDepFacet) {
		this.displayDepFacet = displayDepFacet;
	}
	
	/**
	 * gets the value of DepFacet
	 * @return
	 */
	public String getDepFacet() {
		return depFacet;
	}
	
	/**
	 * sets the value of depFacet
	 * @param depFacet
	 */
	public void setDepFacet(String depFacet) {
		this.depFacet = depFacet;
	}
	
	/**
	 * gets the depFacet Field Value
	 * @return
	 */
	public String getDepFacetField() {
		return depFacetField;
	}
	
	/**
	 * sets the depFacet Field Value
	 * @param depFacetField
	 */
	public void setDepFacetField(String depFacetField) {
		this.depFacetField = depFacetField;
	}
}
