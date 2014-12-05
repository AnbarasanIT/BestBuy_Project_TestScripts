/**
 * Oct 29, 2012 : File created by Ramiro.Serrato for BestBuy-TCS
 */
package com.bestbuy.search.merchandising.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Ramiro.Serrato
 *
 */
public class MerchSecurityContextImpl implements IMerchSecurityContext {

	/* (non-Javadoc)
	 * @see com.bestbuy.search.merchandising.common.IMerchSecurityContext#getContext()
	 */
	@Override
	public SecurityContext getContext() {
		return SecurityContextHolder.getContext();
	}

	/* (non-Javadoc)
	 * @see com.bestbuy.search.merchandising.common.IMerchSecurityContext#setContext(org.springframework.security.core.context.SecurityContext)
	 */
	@Override
	public void setContext(SecurityContext securityContext) {
		SecurityContextHolder.setContext(securityContext);
	}
}
