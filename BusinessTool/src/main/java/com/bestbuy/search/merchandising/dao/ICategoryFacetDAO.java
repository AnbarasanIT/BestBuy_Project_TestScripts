package com.bestbuy.search.merchandising.dao;

import java.util.List;

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.CategoryFacet;
import com.bestbuy.search.merchandising.domain.Facet;

/**
 * @author Kalaiselvi Jaganathan
 * Structure for CategoryFacetDAO
 */
public interface ICategoryFacetDAO extends IBaseDAO<Long,CategoryFacet>{
	
	/**
	 * Method to load the facets for a category
	 * @throws DataAcessException
	 */
	public List<CategoryFacet> loadFacetForCatg(SearchCriteria criteria) throws DataAcessException, ServiceException;
	
	
	 
	/**
	 * Loads the Facets for the given categoryPath and its parent categoryPaths
	 * 
	 * @param categoryPath
	 * @param categoryPaths
	 * @return
	 * @throws DataAcessException
	 * @throws ServiceException
	 */
	public List<CategoryFacet> loadFacetForCatg(String categoryPath, List<String> categoryPaths) throws DataAcessException, ServiceException;
	
	/**
	 * Retrieves the list of CategoryFacet from the facets to be displayed in the page represented by the given categoryId
	 * @param categoryId The category id where this facet is being displayed
	 * @return The list of CategoryFacet
	 * @throws DataAcessException If an error occurs
	 * @author Ramiro.Serrato
	 */
	public List<CategoryFacet> loadCategoryFacetsForDisplayOrder(String categoryPath,List<String> parentPaths) throws DataAcessException;
	
	/**
	 * Updates a list of category facets into the database, this method should be used by the service for updating the display order
	 * @param categoryFacets The list of CategoryFacet objects to be updated in the DB
	 * @return The list of updated Category Facets
	 * @author Ramiro.Serrato
	 */
	public List<CategoryFacet> updateCategoryFacetsOrder(List<CategoryFacet> categoryFacets) throws DataAcessException;
	
	/**
	 * Gets the max display order for the facets displayed in the given category
	 * @param categoryId The id of the target category
	 * @return The max number in the display order field for the CategoryFacet table
	 * @throws DataAcessException If an error occurs while performing the DB query
	 * @author Ramiro.Serrato
	 */
	public Long getMaxDisplayOrder(String categoryId) throws DataAcessException;
	
	/**
	 * Returns the list of CategoryFacet which contains the list of dependant facets for the given category (page)
	 * @param categoryId The id of the category (page)
	 * @return A list of CategoryFacet that contains only facets that depend on a facet assigned to the given category
	 * @throws DataAcessException If an error occurs while performing the query to the DB
	 * @author Ramiro.Serrato
	 */
	public List<CategoryFacet> loadDependantFacetsForCategoryDisplayOrder(String categoryPath, List<String> parentPaths) throws DataAcessException;
	
	/**
	 * Returns the list of CategoryFacet which contains the list of hidden facets for the given category (page)
	 * @param categoryId The id of the category
	 * @return The list of hidden facets
	 * @throws DataAcessException If an error occurs while performing the query to the DB
	 * @author Ramiro.Serrato
	 */
	public List<CategoryFacet> loadHiddenFacetsForCategoryDisplayOrder(String categoryPath, List<String> parentPaths) throws DataAcessException;
	
	/**
	 * This method retrieves all the contexts (CategoryFacet) that point to the given facet, also will retrieve
	 * all the other contexts that belong to any category that the previously retrieved contexts point to. This is useful
	 * when deleting facets
	 * @param facet The facet that is being deleted
	 * @return The list of CategoryFacet
	 * @throws DataAcessException If any error occurs while performing the DB queries
	 * @author Ramiro.Serrato
	 */
	public List<CategoryFacet> loadContextsForFacetForInvalidation(Facet facet) throws DataAcessException;
}
