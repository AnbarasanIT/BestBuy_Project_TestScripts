/**
 * 
 */
package com.bestbuy.search.merchandising.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.bestbuy.search.merchandising.domain.AttributeValue;
import com.bestbuy.search.merchandising.domain.FacetAttributeValueOrder;

/**
 * @author Lakshmi.Valluripalli
 * Wrapper class for the Attribute Value Data
 */
public class AttributeValueWrapper implements IWrapper{
	
	private Long attributeValueId;
	private String attributeValue;
	private String attrValuedisplay;
	private Long sortOrder;

	/**
	 * Creates the wrapper object for Each of the attribute Values
	 * @param attributes
	 * @return List<IWrapper>
	 */
	public static List<IWrapper> getAttributeValueWrapper(List<AttributeValue> attributeValues) {
		
		List<IWrapper> attributeWrappers = new ArrayList<IWrapper>();
		for(AttributeValue attrValue : attributeValues){
			AttributeValueWrapper wrapper = new AttributeValueWrapper();
			wrapper.setAttributeValueId(attrValue.getAttributeValueId());
			wrapper.setAttributeValue(attrValue.getAttributeValue());
			//need to add attributeValue Display
			attributeWrappers.add(wrapper);
		}		
		return attributeWrappers;
	}
	
	/**
	 * Not used Currently
	 */
	@Override
	public int compareTo(IWrapper arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * returns the attribute value display status
	 */
	public String getAttrValuedisplay() {
		return attrValuedisplay;
	}
	
	/**
	 * sets the display status of attribute value
	 */
	public void setAttrValuedisplay(String attrValuedisplay) {
		this.attrValuedisplay = attrValuedisplay;
	}
	
	/**
	 * returns the attributeSorOrder
	 */
	public Long getSortOrder() {
		return sortOrder;
	}
	
	/**
	 * sets the Attribute sort Order
	 */
	public void setSortOrder(Long sortOrder) {
		this.sortOrder = sortOrder;
	}
	

	/**
	 * returns the attributeValue
	 * @return
	 */
	public String getAttributeValue() {
		return attributeValue;
	}
	
	/**
	 * sets the attributeValue
	 * @param attributeValue
	 */
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
	
	/**
	 * sets the attributeValueId
	 * @return
	 */
	public Long getAttributeValueId() {
		return attributeValueId;
	}

	/**
	 * returns the attributeValueId
	 * @param attributeValueId
	 */
	public void setAttributeValueId(Long attributeValueId) {
		this.attributeValueId = attributeValueId;
	}
	
	/**
	 * Retrieve all the attribute values for a facet and convert to attributevalue wrapper
	 * @param facetAttributeValueOrders
	 * @return IWrapper
	 */
	public static List<IWrapper> getFacetAttrValues(List<FacetAttributeValueOrder> facetAttributeValueOrders) {
		List<IWrapper> attributeValueWrappers = new ArrayList<IWrapper>(facetAttributeValueOrders.size());
		AttributeValueWrapper attributeValueWrapper = null;

		//Get the facet entity data and set the facet wrapper
		for(FacetAttributeValueOrder  facetAttributeValueOrder : facetAttributeValueOrders){
			attributeValueWrapper = new AttributeValueWrapper();
			
			//Retrieve the attribute value for the facet
			AttributeValue attributeValue = facetAttributeValueOrder.getFacetAttributeValueOrderPK().getAttributeValue();
			if(attributeValue != null){
			attributeValueWrapper.setAttributeValueId(attributeValue.getAttributeValueId());
			attributeValueWrapper.setAttributeValue(attributeValue.getAttributeValue());
			}
			attributeValueWrappers.add(attributeValueWrapper);
		}
		return attributeValueWrappers;
	}
}
