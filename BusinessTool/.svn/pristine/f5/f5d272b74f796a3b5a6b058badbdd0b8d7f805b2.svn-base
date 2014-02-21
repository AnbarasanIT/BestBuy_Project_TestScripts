package com.bestbuy.search.merchandising.service;

import java.util.List;

import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.CategoryFacet;
import com.bestbuy.search.merchandising.domain.Facet;
import com.bestbuy.search.merchandising.wrapper.CategoryFacetOrderWrapper;
import com.bestbuy.search.merchandising.wrapper.IWrapper;

/**
 * @author Kalaiselvi Jaganathan
 *
 */
public interface ICategoryFacetService extends IBaseService<Long,CategoryFacet> {
	/**
	 * To load the facets based on category
	 * @param catgId
	 * @return
	 * @throws ServiceException
	 */
	public List<IWrapper> loadFacetCategory( String catgId) throws ServiceException;
	
	/**
   * To load the facets based on category
   * @param catgId
   * @return
   * @throws ServiceException
   */
  public List<IWrapper> loadFacetCategory( String catgId, boolean includeFacetsFromItsParentCategories) throws ServiceException;
  
	/**
	 * Method to load dependent facets for a given category id and facet id
	 * 
	 * @param categoryId
	 * @param facetId
	 * @return List<CategoryFacet>
	 * @throws ServiceException
	 */
  	public List<IWrapper> loadDepFacetsForCategory(String categoryId, String facetId) throws ServiceException;
	/**
	 * Returns a list of facets assigned to the given category
	 * @param categoryId The id of the category 
	 * @return The list of facets inside an array of CategoryFacetOrderWrapper
	 * @throws ServiceException If any error found
	 * @author Ramiro.Serrato
	 */
	public List<IWrapper> loadCategoryFacetsForDisplayOrder(String categoryId) throws ServiceException;
	
	/**
	 * Updates the order of a list of facets, the idea is to update all the facets for a category so the method is expecting to receive all the facets assigned to the same category
	 * @param categoryFacetWrappers A list of CategoryFacetOrderWrapper with the id and display order to be updated
	 * @return The list of updated facets, this is a list of CategoryFacetOrderWrapper
	 * @throws ServiceException If any error found
	 * @author Ramiro.Serrato
	 */
	public List<IWrapper> updateCategoryFacetsOrder(List<CategoryFacetOrderWrapper> categoryFacetWrappers) throws ServiceException;
	
	/**
	 * Sets the appropriate display order for the new created facets
	 * @param categoryFacets The list of facets created for the category (Page)
	 * @return The list of CategoryFacet with the correct display order
	 * @throws ServiceException If an error occurs while retrieving the max display order from the DAO
	 * @author Ramiro.Serrato
	 */
	public List<CategoryFacet> setDisplayOrder(List<CategoryFacet> categoryFacets) throws ServiceException;	
	
	/**
	 * Returns a list of facets that are dependent of facets assigned to the given category
	 * @param categoryId A String with the id of the category (Page) that we are working with
	 * @return The list of dependant facets
	 * @throws ServiceException If an error occurs while retrieving the the list from the DAO
	 * @author Ramiro.Serrato
	 */
	public List<IWrapper> loadDependantFacetsForCategoryDisplayOrder(String categoryId) throws ServiceException;
	
	/**
	 * Returns the list of hidden facets assigned to the given category
	 * @param categoryId The id of the category
	 * @return The list of hidden facets that where selected for the category
	 * @throws ServiceException If an error occurs while retrieving the list from the DAO
	 * @author Ramiro.Serrato
	 */
	public List<IWrapper> loadHiddenFacetsForCategoryDisplayOrder(String categoryId) throws ServiceException;	
	
	/**
	 * This method makes invalid all the contexts (CategoryFacet) that point to the given facet, also will make invalid
	 * all the other contexts that belong to any category that the previously invalidated contexts point to. This is useful
	 * when deleting facets
	 * @param facet The facet that was deleted
	 * @throws ServiceException If an error occurs while trying to invalidate the contexts
	 * @author Ramiro.Serrato
	 */
	public void invalidateContextsForFacet(Facet facet) throws ServiceException;
	
	/**
	 * Update teh category facets
	 * @param categoryFacetIds
	 * @throws ServiceException
	 */
	public void updateCategoryFacet(String categoryFacetIds) throws ServiceException;
}
