/**
 * 
 */
package com.bestbuy.search.merchandising.service;

import org.apache.solr.client.solrj.response.QueryResponse;

import com.bestbuy.search.merchandising.common.exception.ServiceException;


/**
 * @author Lakshmi.Valluripalli
 *
 */
public interface ISolrSearchService {
	
	//public List<Attribute> loadFacetFields();
	
	public QueryResponse loadFacetFields() throws ServiceException;

}
