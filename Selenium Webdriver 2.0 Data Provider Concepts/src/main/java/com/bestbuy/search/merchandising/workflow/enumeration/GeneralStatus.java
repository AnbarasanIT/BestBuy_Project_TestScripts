/**
 * Oct 4, 2012 : File created by Ramiro.Serrato for BestBuy-TCS
 */
package com.bestbuy.search.merchandising.workflow.enumeration;

import com.bestbuy.search.merchandising.workflow.exception.InvalidStatusException;

/**
 * @author Ramiro.Serrato
 *
 */
public enum GeneralStatus implements IStatusable{
	APPROVED("Approved"),
	REJECTED("Rejected"),
	DELETED("Deleted"),
	DRAFT("Draft"),
	CF_PUBLISHED("Published CF"),
	AF_PUBLISHED("Published AF"),
	NOT_VALID("Not Valid"),
	EXPIRED("Expired");
	
	private String name;
	
	private GeneralStatus(String name) {
		this.name = name;
	}
	

	/* (non-Javadoc)
	 * @see com.bestbuy.search.merchandising.workflow.enumeration.IStatusable#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see com.bestbuy.search.merchandising.workflow.enumeration.IStatusable#toString()
	 */
	@Override
	public String toString() {
		return getName();
	}	
	
	/**
	 * Gets the enum value that maps to the specified String, the string must match the name field of the enum
	 * @param name The name of the enum
	 * @return A GeneralStatus enum object
	 * @throws InvalidStatusException
	 * @author Ramiro.Serrato
	 */
	public static GeneralStatus getStatusEnum(String name) throws InvalidStatusException {
		Object[] values = GeneralStatus.class.getEnumConstants();
		
		for(Object value : values) {
			GeneralStatus enumVal = (GeneralStatus) value;	
			if(enumVal.toString().equals(name)){
				return enumVal;
			}
		}
		
		throw new InvalidStatusException("The status["+ name +"] was not found in the enum of statuses");
	}	
}
