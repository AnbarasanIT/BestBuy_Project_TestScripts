/**
 * 
 */
package com.bestbuy.search.merchandising.common;

import java.util.Map;

import org.apache.solr.client.solrj.beans.Field;

/**
 * @author Lakshmi.Valluripalli
 *
 */
public class SolrFacetField {
	
	@Field("*_facet")
	public Map<String, String> facetValues;

	public Map<String, String> getFacetValues() {
		return facetValues;
	}

	public void setFacetValues(Map<String, String> facetValues) {
		this.facetValues = facetValues;
	}

}
