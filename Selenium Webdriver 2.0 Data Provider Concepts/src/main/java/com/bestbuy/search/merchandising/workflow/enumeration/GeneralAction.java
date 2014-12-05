/**
 * Oct 2, 2012 : File created by Ramiro.Serrato for BestBuy-TCS
 */
package com.bestbuy.search.merchandising.workflow.enumeration;

import com.bestbuy.search.merchandising.workflow.exception.InvalidActionException;

/**
 * This enum encapsulates the set of valid actions for the workflow
 * @author Ramiro.Serrato
 *
 */
public enum GeneralAction implements IActionable{
	EDIT("Edit", ActionType.USER),
	REJECT("Reject", ActionType.USER),
	DELETE("Delete", ActionType.USER),
	APPROVE("Approve", ActionType.USER),
	PUBLISH("Publish", ActionType.SYSTEM),
	INVALID("Invalid", ActionType.SYSTEM);
	
	private String name;
	private ActionType type;
	
	/**
	 * The ActionType enum encapsulates the type of action, this type is useful for adding behavior to the actions
	 * @author Ramiro.Serrato
	 */
	private enum ActionType {
		USER("User"),
		SYSTEM("System");
		
		private String name;
		
		private ActionType(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
	}
	
	private GeneralAction(String name, ActionType type) {
		this.name = name;
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see com.bestbuy.search.merchandising.workflow.enumeration.IActionable#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see com.bestbuy.search.merchandising.workflow.enumeration.IActionable#getType()
	 */
	@Override
	public String getType() {
		return type.getName();
	}
	
	public String toString() {
		return getName();
	}
	
	public static ActionType getEnumType(String value) {
		return ActionType.valueOf(value);
	}	
	
	public static GeneralAction getActionEnum(String name) throws InvalidActionException {
		Object[] values = GeneralAction.class.getEnumConstants();
		
		for(Object value : values) {
			GeneralAction enumVal = (GeneralAction) value;	
			if(enumVal.toString().equals(name)){
				return enumVal;
			}
		}
		
		throw new InvalidActionException("The action["+ name +"] was not found in the enum of actions");
	}
}
