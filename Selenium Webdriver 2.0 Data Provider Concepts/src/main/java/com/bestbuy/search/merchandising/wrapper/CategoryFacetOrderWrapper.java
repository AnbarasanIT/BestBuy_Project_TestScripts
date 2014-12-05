/**
 * Nov 8, 2012 : File created by Ramiro.Serrato for BestBuy-TCS
 */
package com.bestbuy.search.merchandising.wrapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.ObjectUtils;

import com.bestbuy.search.merchandising.domain.CategoryFacet;
import com.bestbuy.search.merchandising.domain.FacetAttributeValueOrder;

/**
 * @author Ramiro.Serrato
 *
 */
public class CategoryFacetOrderWrapper implements IWrapper {
	private String categoryNodeId;
	private String displayName;
	private String systemName;
	private Long displayOrder;
	private Long categoryFacetId;
	private String status;
	private String values;
	
	/**
	 * Method to get category node id
	 * 
	 * @return String
	 */
	public String getCategoryNodeId() {
		return categoryNodeId;
	}

	/**
	 * Method to set category node id
	 * 
	 * @param categoryNodeId
	 */
	public void setCategoryNodeId(String categoryNodeId) {
		this.categoryNodeId = categoryNodeId;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the systemName
	 */
	public String getSystemName() {
		return systemName;
	}

	/**
	 * @param systemName the systemName to set
	 */
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	/**
	 * @return the displayOrder
	 */
	public Long getDisplayOrder() {
		return displayOrder;
	}

	/**
	 * @param displayOrder the displayOrder to set
	 */
	public void setDisplayOrder(Long displayOrder) {
		this.displayOrder = displayOrder;
	}

	/**
	 * @return the categoryFacetId
	 */
	public Long getCategoryFacetId() {
		return categoryFacetId;
	}

	/**
	 * @param categoryFacetId the categoryFacetId to set
	 */
	public void setCategoryFacetId(Long categoryFacetId) {
		this.categoryFacetId = categoryFacetId;
	}
	
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}	

	public static List<IWrapper> getWrappers(List<CategoryFacet> categoryFacets){
		List<IWrapper> iWrappers = new ArrayList<IWrapper>(categoryFacets.size());
		
		for(CategoryFacet categoryFacet : categoryFacets){
			CategoryFacetOrderWrapper categoryFacetOrderWrapper = new CategoryFacetOrderWrapper();
			categoryFacetOrderWrapper.setSystemName(categoryFacet.getFacet().getSystemName());
			categoryFacetOrderWrapper.setDisplayName(categoryFacet.getFacet().getDisplayName());
			categoryFacetOrderWrapper.setDisplayOrder(categoryFacet.getDisplayOrder());
			categoryFacetOrderWrapper.setCategoryFacetId(categoryFacet.getCatgFacetId());
			categoryFacetOrderWrapper.setStatus(categoryFacet.getFacet().getStatus().getStatus());
			
			List<FacetAttributeValueOrder> facetAttributes = categoryFacet.getFacet().getFacetAttributeOrder();
			categoryFacetOrderWrapper.setValues("");
			StringBuilder valueSummary = new StringBuilder();
			
			for(FacetAttributeValueOrder facetAttribute : facetAttributes) {
				valueSummary.append(facetAttribute.getFacetAttributeValueOrderPK().getAttributeValue().getAttributeValue());
				valueSummary.append(", ");
			}
			
			if(valueSummary.length() > 0) {
				String summary = valueSummary.substring(0, valueSummary.length() - 2);
				
				if(summary.length() > 100) {
					summary = summary.substring(0, 100) + "...";
				}
				
				categoryFacetOrderWrapper.setValues(summary);
			}
			
			iWrappers.add(categoryFacetOrderWrapper);
		}
		
		return iWrappers;
	}

	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(IWrapper target) {
		CategoryFacetOrderWrapper wrapper = (CategoryFacetOrderWrapper) target;
		return (int) (this.displayOrder - wrapper.displayOrder);
	}
	
	public boolean equals(CategoryFacetOrderWrapper otherWrapper){
		return ObjectUtils.nullSafeEquals(displayName, otherWrapper.getDisplayName()) && ObjectUtils.nullSafeEquals(systemName, otherWrapper.getSystemName())
				&& ObjectUtils.nullSafeEquals(displayOrder, otherWrapper.getDisplayOrder()) && ObjectUtils.nullSafeEquals(categoryFacetId, otherWrapper.getCategoryFacetId())
				&& ObjectUtils.nullSafeEquals(values, otherWrapper.getValues());
	}
}
