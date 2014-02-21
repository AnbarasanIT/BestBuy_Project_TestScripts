package com.bestbuy.search.merchandising.wrapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.validator.constraints.NotEmpty;

import com.bestbuy.search.merchandising.common.JsonDateDeSerializer;
import com.bestbuy.search.merchandising.common.JsonDateSerializer;
import com.bestbuy.search.merchandising.common.JsonDateTimeSerializer;
import com.bestbuy.search.merchandising.common.ValidWrapper;
import com.bestbuy.search.merchandising.domain.CategoryFacet;
import com.bestbuy.search.merchandising.domain.Facet;
import com.bestbuy.search.merchandising.domain.FacetAttributeValueOrder;

/**
 * @author Kalaiselvi Jaganathan
 * Wrapper class for Facets
 */
@ValidWrapper
public class FacetWrapper implements IWrapper{


	public static boolean sortDesc = true;

	private int id;
	private Long facetId;
	@NotEmpty(message="Field.NotEmpty")
	private String systemName;
	@NotEmpty(message="Field.NotEmpty")
	private String displayName;
	//possible values are hidden and Displayed
	private String facetDisplay;
	@NotNull(message="Field.NotEmpty")
	private Long attributeId;
	@NotEmpty(message="Field.NotEmpty")
	private String attributeName;
	@NotNull(message="Field.NotEmpty")
	private Date startDate;
	private Date endDate;
	//possible values are Search/Browse/Search&Browse
	private String displayMode;
	private String displayRecurrence;
	private String displayFacetRemoveLink;
	private String displayMobileFacet;
	private String displayMobileFacetRemoveLink;
	private Long glossaryItem;
	private String sortType;
	private Long minValue;
	private Long maxValue;
	private String editedBy;
	private Long statusId;
	private String status;
	private String createdBy;
	private Date createdDate;
	private List<KeyValueWrapper> actions;
	private Date modifiedDate;
	private List<CategoryWrapper> categoryWrapper;
	private List<AttributeValueWrapper> promoteList;
	private List<AttributeValueWrapper> excludeList;
	private Integer attributeValuecount;

	/**
	 * @return the attributeValuecount
	 */
	public Integer getAttributeValuecount() {
		return attributeValuecount;
	}

	/**
	 * @param to set the attributeValuecount 
	 */
	public void setAttributeValuecount(Integer attributeValuecount) {
		this.attributeValuecount = attributeValuecount;
	}

	/**
	 * @return the promoteList
	 */
	public List<AttributeValueWrapper> getPromoteList() {
		return promoteList;
	}

	/**
	 * @param to set the promoteList 
	 */
	public void setPromoteList(List<AttributeValueWrapper> promoteList) {
		this.promoteList = promoteList;
	}

	/**
	 * @return the excludeList
	 */
	public List<AttributeValueWrapper> getExcludeList() {
		return excludeList;
	}

	/**
	 * @param to set the excludeList 
	 */
	public void setExcludeList(List<AttributeValueWrapper> excludeList) {
		this.excludeList = excludeList;
	}

	/**
	 * @return the sortDesc
	 */
	public static boolean isSortDesc() {
		return sortDesc;
	}

	/**
	 * @param sortDesc the sortDesc to set
	 */
	public static void setSortDesc(boolean sortDesc) {
		FacetWrapper.sortDesc = sortDesc;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the facetId
	 */
	public Long getFacetId() {
		return facetId;
	}

	/**
	 * @param facetId the facetId to set
	 */
	public void setFacetId(Long facetId) {
		this.facetId = facetId;
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
	 * @return the attributeId
	 */
	public Long getAttributeId() {
		return attributeId;
	}

	/**
	 * @param attributeId the attributeId to set
	 */
	public void setAttributeId(Long attributeId) {
		this.attributeId = attributeId;
	}

	/**
	 * @return the attributeName
	 */
	public String getAttributeName() {
		return attributeName;
	}

	/**
	 * @param attributeName the attributeName to set
	 */
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	/**
	 * @return the startDate
	 */
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	@JsonDeserialize(using=JsonDateDeSerializer.class)
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	@JsonDeserialize(using=JsonDateDeSerializer.class)
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	 * @return the editedBy
	 */
	public String getEditedBy() {
		return editedBy;
	}

	/**
	 * @param editedBy the editedBy to set
	 */
	public void setEditedBy(String editedBy) {
		this.editedBy = editedBy;
	}

	/**
	 * @return the statusId
	 */
	public Long getStatusId() {
		return statusId;
	}

	/**
	 * @param statusId the statusId to set
	 */
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdDate
	 */
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	@JsonDeserialize(using=JsonDateDeSerializer.class)
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the actions
	 */
	public List<KeyValueWrapper> getActions() {
		return actions;
	}

	/**
	 * @param actions the actions to set
	 */
	public void setActions(List<KeyValueWrapper> actions) {
		this.actions = actions;
	}

	/**
	 * @return the modifiedDate
	 */
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	@JsonDeserialize(using=JsonDateDeSerializer.class)
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
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

	/**
	 * get SortType
	 * @return
	 */
	public String getSortType() {
		return sortType;
	}

	/**
	 * set Sort Type
	 * @param sortType
	 */
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	/**
	 * gets the categoryWrapper
	 * @return
	 */
	public List<CategoryWrapper> getCategoryWrapper() {
		return categoryWrapper;
	}

	/**
	 * sets the categoryWrapper
	 * @param categoryWrapper
	 */
	public void setCategoryWrapper(List<CategoryWrapper> categoryWrapper) {
		this.categoryWrapper = categoryWrapper;
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
	 * Overrides the compareTo method from the Comparable interface, Compares this object to the specified FacetWrapper
	 */
	@Override
	public int compareTo(IWrapper wrapper) {		
		FacetWrapper facetWrapper = (FacetWrapper) wrapper;
		if(modifiedDate != null){
			int comparison = modifiedDate.compareTo(facetWrapper.modifiedDate);  // by default compare to gives an asc ordering
			return sortDesc ? (-1 * comparison) : comparison;
		}else{
			return -1;
		}
	}

	/**
	 * Method to set the row ID's
	 * @param FacetWrapper
	 * @return List<IWrapper>
	 */
	public static List<IWrapper> sortRowIds(List<IWrapper> facetWrappers) {
		int i = 0;
		for(IWrapper wrapper : facetWrappers){
			FacetWrapper facetWrapper = (FacetWrapper) wrapper;
			facetWrapper.setId(i);
			facetWrappers.set(i,facetWrapper);			
			i++;
		}
		return facetWrappers;
	}

	/**
	 * Method to get the actions
	 * @param actions
	 * @return List<KeyValueWrapper>
	 */
	public static List<KeyValueWrapper> getActions(Set<String> actions) {
		List<KeyValueWrapper> dropdowns = new ArrayList<KeyValueWrapper>();

		for(String action : actions) {
			KeyValueWrapper dropdown = new KeyValueWrapper();
			dropdown.setKey(action);
			dropdown.setValue(action);
			dropdowns.add(dropdown);
		}
		return dropdowns;
	}

	/**
	 * Retrieve all the facets and convert to facetwrapper
	 * @param facets
	 * @return
	 */
	public static List<FacetWrapper> getAllFacets(List<Facet> facets) {
		List<FacetWrapper> facetWrappers = new ArrayList<FacetWrapper>(facets.size());
		int i = 0;
		FacetWrapper facetWrapper = null;
		//Get the facet entity data and set the facet wrapper	
		for(Facet  facet : facets){
			facetWrapper  = getFacetData(facet);
			List<CategoryWrapper> categoryWrappers = new ArrayList<CategoryWrapper>();
			if(facet.getCategoryFacet() != null){
				for(CategoryFacet categoryFacet:facet.getCategoryFacet()){
					CategoryWrapper categoryWrapper = null;
					categoryWrapper=getCategoryWrapper(categoryFacet);
					categoryWrappers.add(categoryWrapper);
				}
				facetWrapper.setCategoryWrapper(categoryWrappers);
			}
			facetWrapper.setId(i);

			//Add the facetwrapper to facetwrapper List
			facetWrappers.add(facetWrapper);
			i++;
		}	
		return facetWrappers;
	}

	/**
	 * Retrieve all the facets and convert to facetwrapper
	 * @param facets
	 * @return
	 */
	public static FacetWrapper getFacets(Facet facet) {	
		FacetWrapper facetWrapper = getFacetData(facet);
		//Get the category of the facet and set the facet wrapper
		if(facet.getCategoryFacet() != null){
			List<CategoryWrapper> categoryWrappers = new ArrayList<CategoryWrapper>();
			CategoryWrapper categoryWrapper = null;
			for(CategoryFacet categoryFacet:facet.getCategoryFacet()){
				categoryWrapper = getCategoryWrapper(categoryFacet);

				if(categoryFacet.getApplySubCategory() != null){
					categoryWrapper.setApplySubCategory(categoryFacet.getApplySubCategory().toString());
				}

				if(categoryFacet.getDepFacet() != null){
					categoryWrapper.setDepFacetId(categoryFacet.getDepFacet().getFacetId());
				}

				if(categoryFacet.getDepFacetDisplay() != null){
					categoryWrapper.setDisplayDepFacet(categoryFacet.getDepFacetDisplay().toString());
				}

				if(categoryFacet.getAttributeValue() != null){
					categoryWrapper.setFacetAttributeValueId(categoryFacet.getAttributeValue().getAttributeValueId());
				}

				if(categoryFacet.getDisplayOrder() != null){
					categoryWrapper.setFacetOrder(categoryFacet.getDisplayOrder());
				}

				if(categoryFacet.getDisplay() != null){
					categoryWrapper.setDisplayContext(categoryFacet.getDisplay().toString());
				}

				if(categoryFacet.getCategoryNode() != null){
					categoryWrapper.setCategoryId(categoryFacet.getCategoryNode().getCategoryNodeId());	
				}

				categoryWrappers.add(categoryWrapper);

			}
			//Add the categoryWrapper to the categoryWrapper list
			facetWrapper.setCategoryWrapper(categoryWrappers);

			//Get the attribute value order data of the facet and set the attributeValueWrapper 
			List<AttributeValueWrapper> promoteList = new ArrayList<AttributeValueWrapper>();
			List<AttributeValueWrapper> excludeList = new ArrayList<AttributeValueWrapper>();
			//Get the attribute value order data of the facet and set the attributeValueWrapper 
			if(facet.getFacetAttributeOrder() != null){

				for(FacetAttributeValueOrder facetAttributeValueOrder:facet.getFacetAttributeOrder()){
					if(facetAttributeValueOrder.getDisplayOrder() != null){
						AttributeValueWrapper attributeValueWrapper = new AttributeValueWrapper();
						attributeValueWrapper.setAttributeValue(facetAttributeValueOrder.getFacetAttributeValueOrderPK().getAttributeValue().getAttributeValue());
						attributeValueWrapper.setAttributeValueId(facetAttributeValueOrder.getFacetAttributeValueOrderPK().getAttributeValue().getAttributeValueId());
						if(facetAttributeValueOrder.getFacetDoNotInclude() != null){
							attributeValueWrapper.setAttrValuedisplay(facetAttributeValueOrder.getFacetDoNotInclude().toString());
						}
						attributeValueWrapper.setSortOrder(facetAttributeValueOrder.getDisplayOrder());
						promoteList.add(attributeValueWrapper);
					}
					else{
						AttributeValueWrapper attributeValueWrapper = new AttributeValueWrapper();
						attributeValueWrapper.setAttributeValue(facetAttributeValueOrder.getFacetAttributeValueOrderPK().getAttributeValue().getAttributeValue());
						attributeValueWrapper.setAttributeValueId(facetAttributeValueOrder.getFacetAttributeValueOrderPK().getAttributeValue().getAttributeValueId());
						if(facetAttributeValueOrder.getFacetDoNotInclude() != null){
							attributeValueWrapper.setAttrValuedisplay(facetAttributeValueOrder.getFacetDoNotInclude().toString());
						}
						attributeValueWrapper.setSortOrder(facetAttributeValueOrder.getDisplayOrder());
						excludeList.add(attributeValueWrapper);
					}
				}
			}
			facetWrapper.setPromoteList(promoteList);
			facetWrapper.setExcludeList(excludeList);
			if(facet.getAttribute() != null){
				facetWrapper.setAttributeValuecount(facet.getAttribute().getAttributeValue().size());
			}
		}
		return facetWrapper;
	}

	/**
	 * Map the facet data to facet wrapper
	 * @param facet
	 * @return FacetWrapper
	 */
	public static FacetWrapper getFacetData(Facet facet){
		FacetWrapper facetWrapper = null;
		facetWrapper = new FacetWrapper();
		facetWrapper.setFacetId(facet.getFacetId());
		facetWrapper.setDisplayName(facet.getDisplayName());
		facetWrapper.setSystemName(facet.getSystemName());

		if(facet.getAttribute() != null){
			facetWrapper.setAttributeName(facet.getAttribute().getAttributeName());
		}

		if(facet.getDisplayMode() != null){
			facetWrapper.setDisplayMode(facet.getDisplayMode().toString());
		}

		if(facet.getDisplay() != null){
			facetWrapper.setFacetDisplay(facet.getDisplay().toString());
		}

		if(facet.getDisplayRecurrence() != null){
			facetWrapper.setDisplayRecurrence(facet.getDisplayRecurrence().toString());
		}

		if(facet.getDisplayFacetRemoveLink() != null){
			facetWrapper.setDisplayFacetRemoveLink(facet.getDisplayFacetRemoveLink().toString());
		}

		if(facet.getDisplayMobileFacet() != null){
			facetWrapper.setDisplayMobileFacet(facet.getDisplayMobileFacet().toString());
		}

		if(facet.getDisplayMobileFacetRemoveLink() != null){
			facetWrapper.setDisplayMobileFacetRemoveLink(facet.getDisplayMobileFacetRemoveLink().toString());
		}

		if(facet.getGlossaryItem() != null){
			facetWrapper.setGlossaryItem(facet.getGlossaryItem());
		}

		if(facet.getAttrValSortType() != null){
			facetWrapper.setSortType(facet.getAttrValSortType());
		}

		if(facet.getMinAttrValue() != null){
			facetWrapper.setMinValue(facet.getMinAttrValue());
		}

		if(facet.getMaxAttrValue() != null){
			facetWrapper.setMaxValue(facet.getMaxAttrValue());
		}

		if(facet.getStartDate() != null){
			facetWrapper.setStartDate(facet.getStartDate());
		}

		if(facet.getEndDate() != null){
			facetWrapper.setEndDate(facet.getEndDate());
		}

		if(facet.getUpdatedDate() != null){
			facetWrapper.setModifiedDate(facet.getUpdatedDate());
		}

		if(facet.getAttribute() != null){
			facetWrapper.setAttributeId(facet.getAttribute().getAttributeId());
		}

		if(facet.getStatus() != null){
			facetWrapper.setStatusId(facet.getStatus().getStatusId());
			facetWrapper.setStatus(facet.getStatus().getStatus());
		}

		if(facet.getUpdatedBy() != null){
			facetWrapper.setEditedBy(facet.getUpdatedBy().getFirstName()+"."+facet.getUpdatedBy().getLastName());
		}
		return facetWrapper;
	}

	/**
	 * Set the category path to categoryWrapper
	 * @param categoryFacet
	 * @return CategoryWrapper
	 */
	public static CategoryWrapper getCategoryWrapper(CategoryFacet categoryFacet){
		CategoryWrapper categoryWrapper = new CategoryWrapper();
		if(categoryFacet.getCategoryNode() != null){
			String categoryPath=categoryFacet.getCategoryNode().getCategoryPath();
			categoryWrapper.setCategoryPath(categoryPath);
		}
		categoryWrapper.setValid(categoryFacet.getIsActive());
		return categoryWrapper;
	}
}
