package com.bestbuy.search.merchandising.wrapper;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;



/**wrapper class created for Context functionality
 * @author Chanchal Kumari
 *@Date 25th Sep 2012.
 */
@JsonIgnoreProperties({"contextFacetBaseWrapper"})
public class ContextWrapper  extends ContextBaseWrapper {
	private Long contextId;
	private String contextPathId;
	private Long searchProfileId;
	private Long inherit;
	private String facetValues;
	private List<ContextFacetWrapper> contextFacetWrapper;
	private String valid;
	
	/**
	 * @return the valid
	 */
	public String getValid() {
		return valid;
	}
	/**
	 * @param to set the valid 
	 */
	public void setValid(String valid) {
		this.valid = valid;
	}
	/**
	 * @return the inherit
	 */
	public Long getInherit() {
		return inherit;
	}
	/**
	 * @param to set the inherit
	 */
	public void setInherit(Long inherit) {
		this.inherit = inherit;
	}

	/**
	 * @return the searchProfileId
	 */
	public Long getSearchProfileId() {
		return searchProfileId;
	}
	/**
	 * @param To set the searchProfileId
	 */
	public void setSearchProfileId(Long searchProfileId) {
		this.searchProfileId = searchProfileId;
	}

	/**
	 * @return the contextId
	 */
	public Long getContextId() {
		return contextId;
	}
	/**
	 * @param contextId the contextId to set
	 */
	public void setContextId(Long contextId) {
		this.contextId = contextId;
	}
	/**
	 * @return the contexts
	 */
	public String getContextPathId() {
		return contextPathId;
	}
	/**
	 * @param contexts the contexts to set
	 */
	public void setContextPathId(String contextPathId) {
		this.contextPathId = contextPathId;
	}
	public String getFacetValues() {
		return facetValues;
	}
	public void setFacetValues(String facetValues) {
		this.facetValues = facetValues;
	}
	public List<ContextFacetWrapper> getContextFacetWrapper() {
		return contextFacetWrapper;
	}
	public void setContextFacetWrapper(List<ContextFacetWrapper> contextFacetWrapper) {
		this.contextFacetWrapper = contextFacetWrapper;
	}
	
}
