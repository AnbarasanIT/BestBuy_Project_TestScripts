
package com.bestbuy.search.merchandising.service;

import java.text.ParseException;
import java.util.List;

import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.Facet;
import com.bestbuy.search.merchandising.wrapper.FacetWrapper;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.PaginationWrapper;

/**
 * Defines the Facet Functionality Structure
 * @author chanchal.kumari
 * Date 24th oct 2012 
 */
public interface IFacetService extends IBaseService<Long,Facet>{
	
	/**
	 * Method to load the facets from the Facet entity
	 * @throws ServiceException
	 */
	public List<FacetWrapper> loadFacets(PaginationWrapper paginationWrapper) throws ServiceException;
	
	/**
	 * Method to update status of the Facet
	 * @throws ServiceException
	 */
	public void updateStatusForFacet(Facet facet, String statusName) throws ServiceException;
	
	/**
	 * Method to update the status of facet to deleted
	 * @throws ServiceException
	 */
	public void deleteFacet(Long id) throws ServiceException;
	
	/**
	 * Rejects a facet with the given id
	 * @throws ServiceException
	 */
	public void rejectFacet(Long id) throws ServiceException;
	
	/**
	 * Publishes a facet with the given id
	 * @throws ServiceException
	 */
	public void publishFacet(Long id, String status) throws ServiceException;
	
	/**
	 * Approves a facet with the given id
	 * @throws ServiceException
	 */
	public void approveFacet(Long id) throws ServiceException;
	
	/**
	 * create a facet
	 * @throws ServiceException
	 */
	public void createFacet(FacetWrapper facetWrapper) throws ServiceException, ParseException;
	
	/**
	 * Update the facet
	 * @param facetWrapper
	 * @throws ServiceException
	 * @throws ParseException
	 * @throws DataAcessException
	 */
	public void updateFacet(FacetWrapper facetWrapper) throws ServiceException, ParseException, DataAcessException;
	
	
	/**
	 * Loads the Facet from DB
	 * @param facetId
	 * @return
	 * @throws ServiceException
	 */
	public FacetWrapper loadEditFacetData(Long facetId) throws ServiceException;
	
	/**
	 * Updates the List of Facets to DataBase
	 * @param facets
	 * @throws ServiceException
	 */
	public void updateFacets(List<Facet> facets) throws ServiceException;
	
	/**
	 * To load the attribute value for a facet
	 * @param facetId
	 * @return
	 * @throws ServiceException
	 */
	public List<IWrapper> loadFacetAttributeValues(Long facetId) throws ServiceException, DataAcessException ;
	
	/**
	 * Method to get display order ids for child paths
	 * 
	 * @param categoryPath
	 * @return List<Long>
	 * @throws ServiceException
	 */
	public List<Long> getDisplayOrderIdsForChildPaths(String categoryPath) throws ServiceException;

}

