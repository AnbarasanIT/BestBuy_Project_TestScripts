package com.bestbuy.search.merchandising.dao;

import static com.bestbuy.search.merchandising.common.MerchandisingConstants.DELETE_STATUS;
import static com.bestbuy.search.merchandising.domain.common.DisplayEnum.Y;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.Query;

import org.springframework.dao.DataAccessException;

import com.bestbuy.search.merchandising.common.BTLogger;
import com.bestbuy.search.merchandising.common.ErrorType;
import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.CategoryFacet;
import com.bestbuy.search.merchandising.domain.Facet;
import com.bestbuy.search.merchandising.domain.FacetsDisplayOrder;

/**
 * @author Kalaiselvi Jaganathan
 * DAO layer for CategoryFacet
 */
public class CategoryFacetDAO extends BaseDAO<Long,CategoryFacet> implements ICategoryFacetDAO{

  private final static BTLogger log = BTLogger.getBTLogger(CategoryFacetDAO.class.getName());

	/**
	 * Method to retrieve the facets for a category
	 * @throws DataAcessException
	 * @throws ServiceException 
	 */
	public List<CategoryFacet> loadFacetForCatg(SearchCriteria criteria) throws DataAcessException, ServiceException{
		List<CategoryFacet> categoryFacets = null;

		try{
			categoryFacets = (List<CategoryFacet>)this.executeQuery(criteria);
		}
		catch(DataAcessException e){
			log.error("CategoryFacetDAO", e, ErrorType.APPLICATION,"DAO error while retrieving the facet values from DB");
			throw new ServiceException(e);
		}
		return categoryFacets;
	}
	
	/**
	 * Method to return a list of category facets for given current path, parent paths and facet id
	 * 
	 * @param currentPath
	 * @param parentPaths
	 * @param facetId
	 * @return List<CategoryFacet>
	 * @throws DataAccessException
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public List<CategoryFacet> loadDepFacetsForCategory(String currentPath, List<String> parentPaths, String facetId) 
			throws DataAccessException, ServiceException {
		List<CategoryFacet> categoryFacets = null;
		Query query = null;
		String jpqlParentPath = "select obj from CategoryFacet obj where ((obj.categoryNode.categoryNodeId " +
				"in (select cat.categoryNodeId from Category cat where cat.categoryPath in (?1)) " +
				"and obj.applySubCategory = 'Y') or (obj.categoryNode.categoryNodeId " +
				"in (select cat.categoryNodeId from Category cat where cat.categoryPath = '"+currentPath+"'))) " +
						"and (obj.depFacet is null or obj.depFacet.facetId <> '"+facetId+"') " +
						"and obj.facet.facetId <> '"+facetId+"' and obj.facet.status.statusId in (3, 10, 8) order by obj.facet.displayName asc";
		String jpqlNoParentPath = "select obj from CategoryFacet obj where obj.categoryNode.categoryNodeId " +
				"in (select cat.categoryNodeId from Category cat where cat.categoryPath = '"+currentPath+"') " +
				"and obj.applySubCategory = 'Y' and (obj.depFacet is null or obj.depFacet.facetId <> '"+facetId+"') " +
						"and obj.facet.facetId <> '"+facetId+"' and obj.facet.status.statusId in (3, 10, 8) order by obj.facet.displayName asc";
		String jpqlParentPathNoFacetId = "select obj from CategoryFacet obj where ((obj.categoryNode.categoryNodeId " +
				"in (select cat.categoryNodeId from Category cat where cat.categoryPath in (?1)) " +
				"and obj.applySubCategory = 'Y') or (obj.categoryNode.categoryNodeId " +
				"in (select cat.categoryNodeId from Category cat where cat.categoryPath = '"+currentPath+"'))) " +
						"and obj.facet.status.statusId in (3, 10, 8) order by obj.facet.displayName asc";
		String jpqlNoParentPathNoFacetId = "select obj from CategoryFacet obj where obj.categoryNode.categoryNodeId " +
				"in (select cat.categoryNodeId from Category cat where cat.categoryPath = '"+currentPath+"') " +
				"and obj.applySubCategory = 'Y' and obj.facet.status.statusId in (3, 10, 8) order by obj.facet.displayName asc";
		if (parentPaths != null && parentPaths.size() > 0) {
			if (facetId != null && facetId.trim().length() > 0) {
				query = entityManager.createQuery(jpqlParentPath);
			} else {
				query = entityManager.createQuery(jpqlParentPathNoFacetId);
			}
			query.setParameter(1, parentPaths);
		} else {
			if (facetId != null && facetId.trim().length() > 0) {
				query = entityManager.createQuery(jpqlNoParentPath);
			} else {
				query = entityManager.createQuery(jpqlNoParentPathNoFacetId);
			}
		}
		categoryFacets =  ((List<CategoryFacet>) query.getResultList());
		return categoryFacets;
	}
	
	
  /* (non-Javadoc)
   * @see com.bestbuy.search.merchandising.dao.ICategoryFacetDAO#loadFacetForCatg(java.lang.String, java.util.List)
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<CategoryFacet> loadFacetForCatg(String categoryPath, List<String> parentCategoryPaths) throws DataAcessException, ServiceException {
	  Query query = null;
    String parentPathSql = "SELECT cfc.* " + 
    "FROM category_facet cfc INNER JOIN facets fc ON cfc.facet_id = fc.facet_id " + 
        "WHERE category_node_id IN " + 
    "(SELECT category_node_id FROM categories WHERE categories.category_path = ?1 " + 
        " ) "+
    "AND fc.status_id in (3,10,8) " +
        "UNION " + 
    "SELECT cfc.* " + 
    "FROM category_facet cfc INNER JOIN facets fc ON cfc.facet_id = fc.facet_id " + "WHERE category_node_id IN " +
    "(SELECT category_node_id "+
    "FROM categories " +
        "WHERE categories.category_path IN (?2) " + 
        ") " + 
    "AND cfc.apply_sub_categories = 'Y' AND fc.status_id in (3,10,8)";
    String noParentPathSql = "SELECT cfc.* " + 
    		"FROM category_facet cfc INNER JOIN facets fc ON cfc.facet_id = fc.facet_id " + 
            "WHERE category_node_id IN " + 
            "(SELECT category_node_id FROM categories WHERE categories.category_path = ?1 " + 
                " ) "+
            "AND fc.status_id in (3,10,8) ";
    
    if (parentCategoryPaths!= null && parentCategoryPaths.size() > 0) {
    	query = super.entityManager.createNativeQuery(parentPathSql, CategoryFacet.class);
    	query.setParameter(1, categoryPath);
        query.setParameter(2, parentCategoryPaths);
    } else {
    	query = super.entityManager.createNativeQuery(noParentPathSql, CategoryFacet.class);
	    query.setParameter(1, categoryPath);
    }
    return ((List<CategoryFacet>) query.getResultList());

  }
	
	/* (non-Javadoc)
	 * @see com.bestbuy.search.merchandising.dao.ICategoryFacetDAO#loadCategoryFacetsForDisplayOrder(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CategoryFacet> loadCategoryFacetsForDisplayOrder(String categoryPath,List<String> parentPaths) throws DataAcessException {
		Query query = null;
		List<CategoryFacet> categoryFacets = null;
		List<FacetsDisplayOrder> facetDisplayOrder = null;
		TreeMap<Long, CategoryFacet> orderedMap = null;
		String parentPathJpql = "select obj from CategoryFacet obj where " +
				"((obj.categoryNode.categoryNodeId " +
					"in (select cat.categoryNodeId from Category cat where cat.categoryPath in (?1)) and obj.applySubCategory = 'Y') " +
				"or (obj.categoryNode.categoryNodeId " +
					"in (select cat.categoryNodeId from Category cat where cat.categoryPath = '"+categoryPath+"'))) " +
				"and obj.facet.display = 'Y' " +
				"and obj.depFacet is null " +
				"and obj.facet.status.statusId not in ("+DELETE_STATUS+") " +
						"order by obj.facet.createdDate asc, upper(obj.facet.systemName) asc";
		String noParentPathJpql = "select obj from CategoryFacet obj where " +
				"(obj.categoryNode.categoryNodeId " +
					"in (select cat.categoryNodeId from Category cat where cat.categoryPath = '"+categoryPath+"')) " +
				"and obj.facet.display ='Y' " +
				"and obj.depFacet is null " +
				"and obj.facet.status.statusId not in ("+DELETE_STATUS+") " +
						"order by obj.facet.createdDate asc, upper(obj.facet.systemName) asc";
		try {
			facetDisplayOrder = getFacetsDisplayOrderForPaths(categoryPath);
			if (parentPaths != null && parentPaths.size() > 0) {
				query = entityManager.createQuery(parentPathJpql);
				query.setParameter(1, parentPaths);
			} else {
				query = entityManager.createQuery(noParentPathJpql);
			}
			categoryFacets = ((List<CategoryFacet>) query.getResultList());
			int c = 1;
			if (categoryFacets != null && categoryFacets.size() > 0) {
				for (CategoryFacet categoryFacet : categoryFacets) {
					boolean exists = false;
					if (facetDisplayOrder != null && facetDisplayOrder.size() > 0) {
						for (FacetsDisplayOrder fdo : facetDisplayOrder) {
							if (categoryFacet.getFacet().getFacetId().longValue() == fdo.getFacet().getFacetId().longValue()) {
								categoryFacet.setDisplayOrder(fdo.getDisplayOrder());
								if (orderedMap == null) {
									orderedMap = new TreeMap<Long, CategoryFacet>();
								}
								orderedMap.put(fdo.getDisplayOrder(), categoryFacet);
								exists = true;
								break;
							}
						}
					}
					if (!exists) {
						if (orderedMap == null) {
							orderedMap = new TreeMap<Long, CategoryFacet>();
						}
						if (orderedMap.containsKey(categoryFacet.getFacet().getCreatedDate().getTime())) {
							orderedMap.put((categoryFacet.getFacet().getCreatedDate().getTime() + c), categoryFacet);
							c++;
						} else {
							orderedMap.put(categoryFacet.getFacet().getCreatedDate().getTime(), categoryFacet);
						}
					}
				}
			}
			if (orderedMap != null && orderedMap.size() > 0) {
				long count = 1;
				categoryFacets.clear();
				for (Map.Entry<Long, CategoryFacet> entry : orderedMap.entrySet()) {
					entry.getValue().setDisplayOrder(count);
					count++;
				}
				categoryFacets.addAll(orderedMap.values());
			}
		} catch(Exception e) {
			throw new DataAcessException("Exception when performing the data access for retrieving the category facets", e);
		}
		return categoryFacets;
	}
	
	/**
	 * Method to get a list of facet display order for given paths
	 * 
	 * @param paths
	 * @return List<FacetsDisplayOrder>
	 */
	@SuppressWarnings("unchecked")
	private List<FacetsDisplayOrder> getFacetsDisplayOrderForPaths(String path) {
		List<FacetsDisplayOrder> facetDisplayOrder = null;
		String jpqlQuery = "select obj from FacetsDisplayOrder obj where obj.category.categoryNodeId in " +
				"(select cat.categoryNodeId from Category cat where cat.categoryPath = ?1) and obj.status.statusId = 3";
		Query query = null;
		if (path != null && path.trim().length() > 0) {
			query = entityManager.createQuery(jpqlQuery);
			query.setParameter(1, path);
			facetDisplayOrder = query.getResultList();
		}
		return facetDisplayOrder;
	}

	/* (non-Javadoc)
	 * @see com.bestbuy.search.merchandising.dao.ICategoryFacetDAO#updateCategoryFacetsOrder(java.util.List)
	 */
	@Override
	public List<CategoryFacet> updateCategoryFacetsOrder(List<CategoryFacet> categoryFacets) throws DataAcessException {
		try {
			List<CategoryFacet> resultCategoryFacets = new ArrayList<CategoryFacet>();
			
			for(CategoryFacet categoryFacet : categoryFacets) {
				resultCategoryFacets.add(update(categoryFacet));
			}
			
			return resultCategoryFacets;
		}
		
		catch(Exception e) {
			throw new DataAcessException("Exception while performing the data access for updating the category facets", e);
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.bestbuy.search.merchandising.dao.ICategoryFacetDAO#getMaxDisplayOrder(java.lang.String)
	 */
	@Override
	public Long getMaxDisplayOrder(String categoryId) throws DataAcessException {
		try {
			String jpql = "SELECT max(obj.displayOrder) FROM CategoryFacet obj WHERE obj.categoryNode.categoryNodeId = '" + categoryId + "'";
			jpql += " AND obj.facet.display ='" + Y + "'";
			jpql += " AND obj.facet.status.statusId NOT IN ("+ DELETE_STATUS +")";
			jpql += " AND obj.displayOrder IS NOT NULL";
			jpql += " AND obj.depFacet IS NULL";   
			Long maxOrder = entityManager.createQuery(jpql, Long.class).getSingleResult();
			return maxOrder;
		}
		
		catch(Exception e) {
			throw new DataAcessException("Exception while trying to retrieve the max order from the DB", e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.bestbuy.search.merchandising.dao.ICategoryFacetDAO#loadDependantFacetsForCategoryDisplayOrder(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CategoryFacet> loadDependantFacetsForCategoryDisplayOrder(String categoryPath, List<String> parentPaths) throws DataAcessException {
		Query query = null;
		List<CategoryFacet> categoryFacets = null;
		try {
			String parentPathJpql = "select obj from CategoryFacet obj where " +
					"((obj.categoryNode.categoryNodeId " +
						"in (select cat.categoryNodeId from Category cat where cat.categoryPath in (?1)) and obj.applySubCategory = 'Y') " +
					"or (obj.categoryNode.categoryNodeId " +
						"in (select cat.categoryNodeId from Category cat where cat.categoryPath = '"+categoryPath+"'))) " +
					"and obj.depFacet is not null " +
					"and obj.facet.status.statusId not in ("+DELETE_STATUS+")";
			String noParentPathJpql = "select obj from CategoryFacet obj where " +
					"(obj.categoryNode.categoryNodeId " +
						"in (select cat.categoryNodeId from Category cat where cat.categoryPath = '"+categoryPath+"')) " +
					"and obj.depFacet is not null " +
					"and obj.facet.status.statusId not in ("+DELETE_STATUS+")";
			if (parentPaths != null && parentPaths.size() > 0) {
				query = entityManager.createQuery(parentPathJpql);
				query.setParameter(1, parentPaths);
			} else {
				query = entityManager.createQuery(noParentPathJpql);
			}
			categoryFacets = ((List<CategoryFacet>) query.getResultList());
		} catch(Exception e) {
			throw new DataAcessException("Exception while trying to retrieve the list of dependant facets from the DB", e);
		}
		return categoryFacets;
	}


	/* (non-Javadoc)
	 * @see com.bestbuy.search.merchandising.dao.ICategoryFacetDAO#loadHiddenFacetsForCategoryDisplayOrder(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CategoryFacet> loadHiddenFacetsForCategoryDisplayOrder(String categoryPath, List<String> parentPaths) throws DataAcessException {
		Query query = null;
		List<CategoryFacet> categoryFacets = null;
		try {
			String parentPathJpql = "select obj from CategoryFacet obj where " +
					"((obj.categoryNode.categoryNodeId " +
						"in (select cat.categoryNodeId from Category cat where cat.categoryPath in (?1)) and obj.applySubCategory = 'Y') " +
					"or (obj.categoryNode.categoryNodeId " +
						"in (select cat.categoryNodeId from Category cat where cat.categoryPath = '"+categoryPath+"'))) " +
					"and obj.facet.display ='N' " +
					"and obj.facet.status.statusId not in ("+DELETE_STATUS+")";
			String noParentPathJpql = "select obj from CategoryFacet obj where " +
					"(obj.categoryNode.categoryNodeId " +
						"in (select cat.categoryNodeId from Category cat where cat.categoryPath = '"+categoryPath+"')) " +
					"and obj.facet.display ='N' " +
					"and obj.facet.status.statusId not in ("+DELETE_STATUS+")";
			if (parentPaths != null && parentPaths.size() > 0) {
				query = entityManager.createQuery(parentPathJpql);
				query.setParameter(1, parentPaths);
			} else {
				query = entityManager.createQuery(noParentPathJpql);
			}
			categoryFacets = ((List<CategoryFacet>) query.getResultList());
		} catch(Exception e) {
			throw new DataAcessException("Exception while trying to retrieve the list of dependant facets from the DB", e);
		}
		return categoryFacets;
	}

	
	/* (non-Javadoc)
	 * @see com.bestbuy.search.merchandising.dao.ICategoryFacetDAO#loadContextsForFacetForInvalidation(com.bestbuy.search.merchandising.domain.Facet)
	 */
	@SuppressWarnings("unchecked")
	public List<CategoryFacet> loadContextsForFacetForInvalidation(Facet facet) throws DataAcessException{
		try {
			String jpql = "SELECT obj FROM CategoryFacet obj WHERE obj.facet.facetId = " + facet.getFacetId();
			jpql += " OR obj.depFacet.facetId = " + facet.getFacetId();
			
			Query query = entityManager.createQuery(jpql);
			return (List<CategoryFacet>) query.getResultList();
		}
		
		catch(Exception e) {
			throw new DataAcessException("Exception while trying to update the valid field for the contexts in the DB", e);
		}		
	}
}
