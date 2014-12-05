/**
 * 
 */
package com.bestbuy.search.merchandising.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.bestbuy.search.merchandising.domain.Attribute;

/**
 * @author Lakshmi.Valluripalli
 * Wrapper class for the Attribute Data
 */
public class AttributeWrapper implements IWrapper{
	
	private Long id;
	private String name;

	/**
	 * Creates the wrapper object for Each of the attribute
	 * @param attributes
	 * @return List<IWrapper>
	 */
	public static List<IWrapper> getAttributes(List<Attribute> attributes) {
		
		List<IWrapper> attributeWrappers = new ArrayList<IWrapper>();
		for(Attribute attribute : attributes){
			AttributeWrapper wrapper = new AttributeWrapper();
			wrapper.setId(attribute.getAttributeId());
			wrapper.setName(attribute.getAttributeName());
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
	 * returns the Attribute Name
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * sets the Attribute Name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * returns the Attribute Id
	 * @return
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * sets the AttributeId
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

}
