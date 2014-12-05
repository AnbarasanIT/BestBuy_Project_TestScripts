/**
 * Oct 29, 2012 : File created by Ramiro.Serrato for BestBuy-TCS
 */
package com.bestbuy.search.merchandising.security;

import org.springframework.security.core.context.SecurityContext;

/**
 * @author Ramiro.Serrato
 *
 */
public interface IMerchSecurityContext {
	/**
	 * Gets the security context
	 * @return The current security context
	 * @author Ramiro.Serrato
	 */
	public SecurityContext getContext();
	
	/**
	 * Sets the security context
	 * @param securityContext
	 * @author Ramiro.Serrato
	 */
	public void setContext(SecurityContext securityContext);	
}
