/**
 * Sep 13, 2012 : File created by Ramiro.Serrato for BestBuy-TCS
 */
package com.bestbuy.search.merchandising.workflow;

/**
 * This class encapsulates pair of status, action which defines a step in the workflow
 * @author Ramiro.Serrato
 *
 */
public class Step<Status extends Enum<Status>, Action extends Enum<Action>> {
	private final Status status;
	private final Action action;

	public Step(Status status, Action action) {
		this.status = status;
		this.action = action;
	}

	public Status getStatus() { 
		return status;
	}

	public Action getAction() { 
		return action; 
	}

	@Override
	public int hashCode() { 
		return new StringBuilder(status.toString()).append(action.toString()).toString().hashCode();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		Step<Status, Action> step = (Step<Status, Action>) o;
		
		return new StringBuilder(status.toString()).append(action.toString()).toString().equals(
				new StringBuilder(step.status.toString()).append(step.action.toString()).toString()
		);
	}
}
