/**
 * 
 */
package com.bestbuy.search.merchandising.service;

import java.util.List;

import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.Attribute;
import com.bestbuy.search.merchandising.domain.AttributeValue;

/**
 * defines the CRUD operations of Attribute
 * @author Lakshmi.Valluripalli
 */
public interface IAttributeService extends IBaseService<Long,Attribute>  {
	
	public static final String CACHE_NAME="attributeCache";
		
	/**
	 * Loads the attributeNames from the DB
	 * @return
	 * @throws ServiceException
	 */
	public List<Attribute> loadAttributes() throws ServiceException;
	
	/**
	 * Loads the attribute from the DB based on attribute name
	 * @return
	 * @throws ServiceException
	 */
	//public Attribute loadAttribute(String attributeName) throws ServiceException;
	
	/**
	 * Loads all the attributes whose names matches to the attributeName provided
	 * @param attributeName
	 * @return
	 * @throws ServiceException
	 */
	public List<Attribute> partialMatchAttribute(String attributeName) throws ServiceException;
		
	/**
	 * save the attribute from the DB
	 * @return
	 * @throws ServiceException
	 */
	public void saveAttribute(Attribute attribute) throws ServiceException;
	
	/**
	 * Load AttributeValues for a specific Attribute
	 * @param attributeId
	 * @return
	 * @throws ServiceException
	 */
	public List<AttributeValue> loadAttributeValues(Long attributeId) throws ServiceException;
	
	/**
	 * Method to import solr data
	 * 
	 * @throws ServiceException
	 */
	public void importSolrData() throws ServiceException;
	
}
