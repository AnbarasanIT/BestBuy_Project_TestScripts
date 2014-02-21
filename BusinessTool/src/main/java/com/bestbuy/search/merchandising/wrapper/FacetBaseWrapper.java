package com.bestbuy.search.merchandising.wrapper;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.bestbuy.search.merchandising.common.JsonDateDeSerializer;
import com.bestbuy.search.merchandising.common.JsonDateSerializer;
import com.bestbuy.search.merchandising.domain.CategoryFacet;
import com.bestbuy.search.merchandising.domain.Facet;
import com.bestbuy.search.merchandising.domain.FacetAttributeValueOrder;
import com.bestbuy.search.merchandising.domain.common.DisplayEnum;
import com.bestbuy.search.merchandising.domain.common.DisplayModeEnum;

/**
 * Base Wrapper class for Facets Publish Json Creation
 * this class also performs sort on the Facet DisplayOrder
 * @author Lakshmi Valluripalli
 * 
 */
@JsonIgnoreProperties({"facetDisplayOrder"})
public class FacetBaseWrapper  implements Comparable<FacetBaseWrapper> {
	
	private Date startDate;
	private Date endDate;
	private String systemName;
	private String displayName;
	private String facetField;
	private List<String> promoteList = new ArrayList<String>();
	private List<String> excludeList = new ArrayList<String>();
	private String excludeAll = "No";
	private String valueOrder;
	private String facetDisplay; //Yes/No
	private String displayMode;
	private String displayRecurrence;
	private String displayFacetRemoveLink;
	private String displayMobileFacet;
	private String displayMobileFacetRemoveLink;
	private Long glossaryItem;
	private Long minValue;
	private Long maxValue;
	private String depFacetField;
	private String depFacetFieldValue;
	private String applyToSubCategories;
	private String displayContext;
	private String displayDepFacet;
	private Long facetDisplayOrder = 0l;
	public FacetBaseWrapper(){}
	
	/**
	 * constructor that sets the Data from Facet and CategoryFacet
	 * @param facet
	 * @param categoryFacet
	 */
	public FacetBaseWrapper(Facet facet,CategoryFacet categoryFacet) {
		
		this.systemName = facet.getSystemName();
		this.displayName = facet.getDisplayName();
		if(facet.getAttribute() != null){
			this.facetField = facet.getAttribute().getAttributeName();
		}
		this.facetDisplay = getDisplay(facet.getDisplay());
		this.displayMode = getDisplayMode(facet.getDisplayMode());
		this.displayRecurrence = getDisplay(facet.getDisplayRecurrence());
		this.displayFacetRemoveLink = getDisplay(facet.getDisplayFacetRemoveLink());
		this.displayMobileFacet =  getDisplay(facet.getDisplayMobileFacet());
		this.displayMobileFacetRemoveLink = getDisplay(facet.getDisplayMobileFacetRemoveLink());
		this.glossaryItem = facet.getGlossaryItem();
		this.minValue = facet.getMinAttrValue();
		if(facet.getMaxAttrValue() != null){
			this.maxValue = facet.getMaxAttrValue();
		}
		this.startDate = facet.getStartDate();
		this.endDate = facet.getEndDate();
		if(categoryFacet.getApplySubCategory() != null){
			this.setApplyToSubCategories(getDisplay(categoryFacet.getApplySubCategory()));
		}
		if(categoryFacet.getDisplay() != null){
			this.setDisplayContext(getDisplay(categoryFacet.getDisplay()));
		}
		if(categoryFacet.getDepFacetDisplay() != null){
			this.setDisplayDepFacet(getDisplay(categoryFacet.getDepFacetDisplay()));
		}

		if(categoryFacet.getDepFacet() != null && categoryFacet.getDepFacet().getAttribute() != null){
			this.setDepFacetField(categoryFacet.getDepFacet().getAttribute().getAttributeName());
		}
		if(categoryFacet.getAttributeValue() != null){
			this.setDepFacetFieldValue(categoryFacet.getAttributeValue().getAttributeValue());
		}
		if(categoryFacet.getDisplayOrder() != null){
			facetDisplayOrder = new Long(categoryFacet.getDisplayOrder().longValue());
		}
		prepareFacetFields(facet);
	}
	
	/**
	 * Method that returns the DisplayEnum depending on the
	 * 'Y'/'N' values received from UI wrapper
	 * @param mode
	 * @return
	 */
	private String getDisplay(DisplayEnum display){
		if(display.getDisplay().equals(DisplayEnum.Y.getDisplay()))
		{ 
			return "Yes";
		}else{
			return "No";
		}
	}
	
	/**
	 * Method that returns the DisplayEnum depending on the
	 * 'Y'/'N' values received from UI wrapper
	 * @param mode
	 * @return
	 */
	private String getDisplayMode(DisplayModeEnum mode){
		if(mode.name().equalsIgnoreCase(DisplayModeEnum.SEARCH.toString()))
		{
			return DisplayModeEnum.SEARCH.toString();
		}else if(mode.name().equalsIgnoreCase(DisplayModeEnum.BROWSE.toString()))
		{
			return DisplayModeEnum.BROWSE.toString();
		}else
		{
			return DisplayModeEnum.SEARCH_BROWSE.toString();
		}
	}
	
	/**
	 * Method that returns the DisplayEnum depending on the
	 * 'Y'/'N' values received from UI wrapper
	 * @param mode
	 * @return
	 */
	private void prepareFacetFields(Facet facet){
		if(facet.getAttrValSortType().equals("AZ") || facet.getAttrValSortType().equals("num_spec_AZ"))
		{
			this.valueOrder = "Alphabetical";
		}
		
		if(facet.getAttrValSortType().equals("num_res_least") || facet.getAttrValSortType().equals("non_spec_least"))
		{
			this.valueOrder = "Count";
		}
		if(facet.getAttrValSortType().equals("excludeAll")){
			this.excludeAll = "Yes";
		}
		TreeMap<Long, String> promoteMap = null;
		//prepare PromoteList and Exclude List
		for(FacetAttributeValueOrder facetAttribtue:facet.getFacetAttributeOrder()){
			if(facetAttribtue.getFacetAttributeValueOrderPK() != null && facetAttribtue.getFacetAttributeValueOrderPK().getAttributeValue() != null){
				if(facetAttribtue.getFacetDoNotInclude().equals(DisplayEnum.Y)){
						excludeList.add(facetAttribtue.getFacetAttributeValueOrderPK().getAttributeValue().getAttributeValue());
				}else{
					if((facet.getAttrValSortType().equals("num_spec_AZ") || facet.getAttrValSortType().equals("non_spec_least") ||
							facet.getAttrValSortType().equals("excludeAll")) && facetAttribtue.getDisplayOrder() != null){
						if (promoteMap == null) {
							promoteMap = new TreeMap<Long, String>();
						}
						promoteMap.put(facetAttribtue.getDisplayOrder(), 
								facetAttribtue.getFacetAttributeValueOrderPK().getAttributeValue().getAttributeValue());
					}
				}
			}
		}
		if (promoteMap != null && promoteMap.size() > 0) {
			promoteList.addAll(promoteMap.values());
		}
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
	 * @return the facetDisplay
	 */
	public String getFacetDisplay() {
		return facetDisplay;
	}

	/**
	 * @param facetDisplay the facetDisplay to set
	 */
	public void setFacetDisplay(String facetDisplay) {
		this.facetDisplay = facetDisplay;
	}

	/**
	 * @return the displayMode
	 */
	public String getDisplayMode() {
		return displayMode;
	}

	/**
	 * @param displayMode the displayMode to set
	 */
	public void setDisplayMode(String displayMode) {
		this.displayMode = displayMode;
	}

	/**
	 * @return the glossaryItem
	 */
	public Long getGlossaryItem() {
		return glossaryItem;
	}

	/**
	 * @param glossaryItem the glossaryItem to set
	 */
	public void setGlossaryItem(Long glossaryItem) {
		this.glossaryItem = glossaryItem;
	}

	/**
	 * @return the minValue
	 */
	public Long getMinValue() {
		return minValue;
	}

	/**
	 * @param minValue the minValue to set
	 */
	public void setMinValue(Long minValue) {
		this.minValue = minValue;
	}

	/**
	 * @return the maxValue
	 */
	public Long getMaxValue() {
		return maxValue;
	}

	/**
	 * @param maxValue the maxValue to set
	 */
	public void setMaxValue(Long maxValue) {
		this.maxValue = maxValue;
	}

	/**
	 * get Display Recurrence
	 * @return
	 */
	public String getDisplayRecurrence() {
		return displayRecurrence;
	}
	
	/**
	 * set Display Recurrence
	 * @param displayRecurrence
	 */
	public void setDisplayRecurrence(String displayRecurrence) {
		this.displayRecurrence = displayRecurrence;
	}
	
	/**
	 * gets Display Facet Remove Link
	 * @return
	 */
	public String getDisplayFacetRemoveLink() {
		return displayFacetRemoveLink;
	}
	
	/**
	 * sets Display Facet Remove Link
	 * @param displayFacetRemoveLink
	 */
	public void setDisplayFacetRemoveLink(String displayFacetRemoveLink) {
		this.displayFacetRemoveLink = displayFacetRemoveLink;
	}
	
	/**
	 * gets Display MobileFacet
	 * @return
	 */
	public String getDisplayMobileFacet() {
		return displayMobileFacet;
	}
	
	/**
	 * sets DisplayMobile Facet
	 * @param displayMobileFacet
	 */
	public void setDisplayMobileFacet(String displayMobileFacet) {
		this.displayMobileFacet = displayMobileFacet;
	}
	
	/**
	 * gets DispalyMobile Facet Remove Link
	 * @return
	 */
	public String getDisplayMobileFacetRemoveLink() {
		return displayMobileFacetRemoveLink;
	}
	
	/**
	 * sets displayMobileFacetRemove Link
	 * @param displayMobileFacetRemoveLink
	 */
	public void setDisplayMobileFacetRemoveLink(String displayMobileFacetRemoveLink) {
		this.displayMobileFacetRemoveLink = displayMobileFacetRemoveLink;
	}
	
	/**
	 * getter for the FacetField
	 * @return
	 */
	public String getFacetField() {
		return facetField;
	}
	
	/**
	 * setter for the FacetFieldq
	 * @param facetField
	 */
	public void setFacetField(String facetField) {
		this.facetField = facetField;
	}
	
	/**
	 * getter for the PromoList
	 * @return
	 */
	public List<String> getPromoteList() {
		return promoteList;
	}
	
	/**
	 * setter for the promoList
	 * @param promoteList
	 */
	public void setPromoteList(List<String> promoteList) {
		this.promoteList = promoteList;
	}
	
	/**
	 * getter for the ExcludeList
	 * @return
	 */
	public List<String> getExcludeList() {
		return excludeList;
	}
	
	/**
	 * setter for Exclude List
	 * @param excludeList
	 */
	public void setExcludeList(List<String> excludeList) {
		this.excludeList = excludeList;
	}
	
	/**
	 * gets the valueOrder
	 * @return
	 */
	public String getValueOrder() {
		return valueOrder;
	}
	
	/**
	 * sets value Order
	 * @param valueOrder
	 */
	public void setValueOrder(String valueOrder) {
		this.valueOrder = valueOrder;
	}

	/**
	 * gets the DepFacetDField
	 * @return
	 */
	public String getDepFacetField() {
		return depFacetField;
	}
	
	/**
	 * sets the DepFacetField
	 * @param depFacetField
	 */
	public void setDepFacetField(String depFacetField) {
		this.depFacetField = depFacetField;
	}
	
	/**
	 * gets the DepFacetFieldValue
	 * @return
	 */
	public String getDepFacetFieldValue() {
		return depFacetFieldValue;
	}
	
	/**
	 * sets the DepFacetFieldValue
	 * @param depFacetFieldValue
	 */
	public void setDepFacetFieldValue(String depFacetFieldValue) {
		this.depFacetFieldValue = depFacetFieldValue;
	}
	
	/**
	 * gets the Apply to SubCategories
	 * @return
	 */
	public String getApplyToSubCategories() {
		return applyToSubCategories;
	}
	
	/**
	 * sets the Apply to Subcategories 
	 * @param applyToSubCategories
	 */
	public void setApplyToSubCategories(String applyToSubCategories) {
		this.applyToSubCategories = applyToSubCategories;
	}
	
	/**
	 * gets the DisplayContext
	 * @return
	 */
	public String getDisplayContext() {
		return displayContext;
	}
	
	/**
	 * sets the DisplayContext
	 * @param displayContext
	 */
	public void setDisplayContext(String displayContext) {
		this.displayContext = displayContext;
	}

	/**
	 * gets the DisplayDepFacet
	 * @return
	 */
	public String getDisplayDepFacet() {
		return displayDepFacet;
	}
	
	/**
	 * sets the DisplayDepFacet
	 * @param displayDepFacet
	 */
	public void setDisplayDepFacet(String displayDepFacet) {
		this.displayDepFacet = displayDepFacet;
	}
	
	/**
	 * Overrides the compareTo method from the Comparable interface,
	 *  Compares this object to the specified facetBaseWrapper
	 * @param facetBaseWrapper
	 */
	@Override
	public int compareTo(FacetBaseWrapper facetBaseWrapper) {		
		if(this.facetDisplayOrder != null && facetBaseWrapper.getFacetDisplayOrder() != null){
			int comparison = this.facetDisplayOrder.compareTo(facetBaseWrapper.getFacetDisplayOrder());
			return comparison;
		}
		
		else{
			return 0;
		}
	}

	/**
	 * gets the facetDisplayOrder
	 * @return
	 */
	public Long getFacetDisplayOrder() {
		return facetDisplayOrder;
	}
	
	/**
	 * sets the facet Display Order
	 * @param facetDisplayOrder
	 */
	public void setFacetDisplayOrder(Long facetDisplayOrder) {
		this.facetDisplayOrder = facetDisplayOrder;
	}
	
	/**
	 * 
	 * @return
	 */
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getStartDate() {
		return startDate;
	}
	
	/**
	 * 
	 * @param startDate
	 */
	@JsonDeserialize(using=JsonDateDeSerializer.class)
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	/**
	 * 
	 * @return
	 */
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getEndDate() {
		return endDate;
	}
	
	/**
	 * 
	 * @param endDate
	 */
	@JsonDeserialize(using=JsonDateDeSerializer.class)
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getExcludeAll() {
		return excludeAll;
	}

	public void setExcludeAll(String excludeAll) {
		this.excludeAll = excludeAll;
	}
}
