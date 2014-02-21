package com.bestbuy.search.merchandising.service;

import static com.bestbuy.search.merchandising.common.MerchandisingConstants.APPROVE_STATUS;
import static com.bestbuy.search.merchandising.common.MerchandisingConstants.CF_PUBLISH_STATUS;
import static com.bestbuy.search.merchandising.common.MerchandisingConstants.AF_PUBLISH_STATUS;
import static com.bestbuy.search.merchandising.common.MerchandisingConstants.STR_PIPE;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.EDIT;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralStatus.APPROVED;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralStatus.DRAFT;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralStatus.EXPIRED;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralStatus.NOT_VALID;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralStatus.CF_PUBLISHED;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralStatus.AF_PUBLISHED;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralStatus.REJECTED;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.CategoryFacetDAO;
import com.bestbuy.search.merchandising.domain.Category;
import com.bestbuy.search.merchandising.domain.CategoryFacet;
import com.bestbuy.search.merchandising.domain.Facet;
import com.bestbuy.search.merchandising.domain.FacetsDisplayOrder;
import com.bestbuy.search.merchandising.domain.Status;
import com.bestbuy.search.merchandising.domain.Users;
import com.bestbuy.search.merchandising.domain.common.DisplayEnum;
import com.bestbuy.search.merchandising.security.UserUtil;
import com.bestbuy.search.merchandising.workflow.GeneralWorkflow;
import com.bestbuy.search.merchandising.workflow.exception.WorkflowException;
import com.bestbuy.search.merchandising.wrapper.CategoryFacetOrderWrapper;
import com.bestbuy.search.merchandising.wrapper.CategoryFacetWrapper;
import com.bestbuy.search.merchandising.wrapper.IWrapper;

/**
 * @author Kalaiselvi Jaganathan
 *         Service for Category Facet (for Facet UI)
 */
public class CategoryFacetService extends BaseService<Long, CategoryFacet> implements ICategoryFacetService {

  @Autowired
  public void setDao(CategoryFacetDAO dao) {
    this.baseDAO = dao;
  }
  
  @Autowired
  private ICategoryNodeService categoryNodeService;
  
  @Autowired
  private IStatusService statusService;
  
  @Autowired
  private IFacetService facetService;
  
  @Autowired
  private GeneralWorkflow generalWorkflow;
  
  @Autowired
  private UserUtil userUtil;

  /**
   * Method to load dependent facets for a given category id and facet id
   * 
   * @param categoryId
   * @param facetId
   * @return List<CategoryFacet>
   * @throws ServiceException
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = ServiceException.class, timeout = -1)
  public List<IWrapper> loadDepFacetsForCategory(String categoryId, String facetId) throws ServiceException {
	  List<IWrapper> categoryFacetWrappers = null;
	  List<CategoryFacet> categoryFacets = null;
	  String currentPath = null;
	  List<String> parentPaths = null;
	  String categoryPath = null;
	  
	  // Check if the category id is not null and not empty
	  if (categoryId == null || categoryId.trim().length() == 0) {
		  throw new ServiceException("Null/Empty Category ID");
	  }
	  
	  // Fetch the category path from the category id
	  categoryPath = categoryNodeService.getCategoryPath(categoryId);
	  
	  if (categoryPath != null && categoryPath.trim().length() > 0) {
		  // Set current path as the category path
		  currentPath = categoryPath;
		  
		  // Check if the current path has a pipe. If yes, get parent paths
		  if (categoryPath.lastIndexOf(STR_PIPE) != -1) {
			  parentPaths = getParentCategoryPaths(categoryPath); 
		  }
		  categoryFacets = ((CategoryFacetDAO) baseDAO).loadDepFacetsForCategory(currentPath, parentPaths, facetId);		  
		  categoryFacetWrappers = CategoryFacetWrapper.getAllFacetsforCatg(categoryFacets);
	  } else {
		  throw new ServiceException("Invalid Category ID");
	  }
	  return categoryFacetWrappers;
  }

  /**
   * Retrieves all facets of a category
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = ServiceException.class, timeout = -1)
  public List<IWrapper> loadFacetCategory(String catgId) throws ServiceException {
    List<IWrapper> categoryFacetWrappers = null;
    try {
      SearchCriteria criteria = new SearchCriteria();
      List<Long> status = new ArrayList<Long>();
      Map<String, Object> whereCriteria = criteria.getSearchTerms();
      whereCriteria.put("obj.categoryNode.categoryNodeId", catgId);
      Map<String, Object> inCriteria = criteria.getInCriteria();
      status.add(APPROVE_STATUS);
      status.add(AF_PUBLISH_STATUS);
      status.add(CF_PUBLISH_STATUS);
      inCriteria.put("obj.facet.status.statusId", status);
      criteria.setOrderByCriteria("obj.facet.displayName asc");
      // Retrieve all facets for a category
      List<CategoryFacet> categoryFacets = ((CategoryFacetDAO) baseDAO).loadFacetForCatg(criteria);
      // Map the entity to wrapper
      categoryFacetWrappers = CategoryFacetWrapper.getAllFacetsforCatg(categoryFacets);
    } catch (DataAcessException dae) {
      throw new ServiceException("Error while retriving the facets for a category from DB", dae);
    }
    return categoryFacetWrappers;
  }

  /*
   * (non-Javadoc)
   * @see com.bestbuy.search.merchandising.service.ICategoryFacetService#loadFacetsForCategory(java.lang.String)
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = ServiceException.class, timeout = -1)
  public List<IWrapper> loadCategoryFacetsForDisplayOrder(String categoryId) throws ServiceException {
	  List<String> parentPaths = null;
	  String categoryPath = null;
	  List<CategoryFacet> categoryFacets = null;
		  
	  // Check if the category id is not null and not empty
	  if (categoryId == null || categoryId.trim().length() == 0) {
		  throw new ServiceException("Null/Empty Category ID");
	  }
		  
	  // Fetch the category path from the category id
	  categoryPath = categoryNodeService.getCategoryPath(categoryId);
		  
	  if (categoryPath != null && categoryPath.trim().length() > 0) {
		  // Check if the current path has a pipe. If yes, get parent paths
		  if (categoryPath.lastIndexOf(STR_PIPE) != -1) {
			  parentPaths = getParentCategoryPaths(categoryPath);
		  }
		  try {
			categoryFacets = ((CategoryFacetDAO) baseDAO).loadCategoryFacetsForDisplayOrder(categoryPath,parentPaths);
		} catch (DataAcessException dae) {
			throw new ServiceException("Error while retriving CategoryFacetsForDisplayOrder" +dae);
		}
	  }
    	return CategoryFacetOrderWrapper.getWrappers(categoryFacets);
  }

  /*
   * (non-Javadoc)
   * @see com.bestbuy.search.merchandising.service.ICategoryFacetService#updateCategoryFacetsOrder(java.util.List)
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = ServiceException.class, timeout = -1)
  public List<IWrapper> updateCategoryFacetsOrder(List<CategoryFacetOrderWrapper> categoryFacetWrappers) throws ServiceException {
    if (categoryFacetWrappers == null || categoryFacetWrappers.isEmpty()) {
      throw new ServiceException("Error while receiving a null or empty list of Category Facet Wrappers");
    }

    noNullOrder(categoryFacetWrappers);
    categoryFacetWrappers = fixOrder(categoryFacetWrappers);

    try {
      List<CategoryFacet> categoryFacets = retrieveAndApplyOrderToCategoryFacets(categoryFacetWrappers);
      noBadStatus(categoryFacets);
      categoryFacets = ((CategoryFacetDAO) baseDAO).updateCategoryFacetsOrder(categoryFacets);
      
      // Update facet status
      List<Facet> facets = null;
      if (categoryFacets != null && categoryFacets.size() > 0) {
    	  for (CategoryFacet categoryFacet : categoryFacets) {
    		  Facet facet = categoryFacet.getFacet();
    		  if (facet.getStatus().getStatusId().longValue() == CF_PUBLISH_STATUS ||
    				  facet.getStatus().getStatusId().longValue() == AF_PUBLISH_STATUS) {
    			  try {
	    			facet = setUpdateStatusForFacet(facet, generalWorkflow.stepForward(facet.getStatus().getStatus(), EDIT));
	    			if (facets == null) { 
	      			  facets = new ArrayList<Facet>();
	      		  	}
	    			facets.add(facet);
	    		  } catch (WorkflowException e) {
	    			throw new ServiceException("Error while performing the workflow step", e);
	    		  }
    		  }
    	  }
    	  if (facets != null && facets.size() > 0) {
    		  facetService.updateFacets(facets);
    	  }
      }
      return CategoryFacetOrderWrapper.getWrappers(categoryFacets);
    } catch (DataAcessException e) {
      throw new ServiceException("Error while updating the order of the facets", e);
    }
  }

  /**
   * Fixes the display order in a list of CategoryFacetWrapper, it removes any gap in the order series e.g. 0234 to 0123
   * or equal values 0122 to 0123
   * 
   * @param categoryFacetWrappers
   *          The list of wrappers to fix
   * @return The list of wrappers with the proper order
   * @author Ramiro.Serrato
   */
  public List<CategoryFacetOrderWrapper> fixOrder(List<CategoryFacetOrderWrapper> categoryFacetWrappers) {
    Collections.sort(categoryFacetWrappers);

    for (int i = 0; i < categoryFacetWrappers.size(); i++) {
      categoryFacetWrappers.get(i).setDisplayOrder(Long.valueOf(i));
    }

    return categoryFacetWrappers;
  }

  /**
   * Checks that the wrappers in the given list have no null values in the display order field
   * 
   * @param categoryFacetWrappers
   *          The list of wrappers to check
   * @throws ServiceException
   *           If a null value was found
   * @author Ramiro.Serrato
   */
  private void noNullOrder(List<CategoryFacetOrderWrapper> categoryFacetWrappers) throws ServiceException {
    for (CategoryFacetOrderWrapper facetWrapper : categoryFacetWrappers) {
      if (facetWrapper.getDisplayOrder() == null) {
        throw new ServiceException("One or more of the facets have a null order");
      }
    }
  }

  /**
   * Checks that each category facets corresponding facet in the given list has the correct status
   * 
   * @param categoryFacets
   *          The list of CategoryFacet to check
   * @throws ServiceException
   *           If the status in any of the category facet is not correct, based on the list of correct statuses
   * @author Ramiro.Serrato
   */
  private void noBadStatus(List<CategoryFacet> categoryFacets) throws ServiceException {
    for (CategoryFacet categoryFacet : categoryFacets) {
      ArrayList<String> goodStatuses = new ArrayList<String>(Arrays.asList(new String[] {APPROVED.toString(), DRAFT.toString(), AF_PUBLISHED.toString(), CF_PUBLISHED.toString(), REJECTED.toString(), NOT_VALID.toString(), EXPIRED.toString()}));

      if (!goodStatuses.contains(categoryFacet.getFacet().getStatus().getStatus())) {
        throw new ServiceException("One or more of the facets have a bad status");
      }
    }
  }

	/**
	 * Retrieves the list of CategoryFacet corresponding to the list of given
	 * wrappers, then it will copy the display order in the wrapper to the
	 * entity and return the list of CategoryFavet
	 * 
	 * @param categoryFacetWrappers
	 *            The list of wrappers that contain the new display order
	 *            information
	 * @return The list of CategoryFacet with the changes in display order
	 * @throws DataAcessException
	 *             If an error occurs while querying the DB
	 * @author Ramiro.Serrato
	 */
	public List<CategoryFacet> retrieveAndApplyOrderToCategoryFacets(
			List<CategoryFacetOrderWrapper> categoryFacetWrappers)
			throws DataAcessException, ServiceException {
		List<CategoryFacet> categoryFacets = new ArrayList<CategoryFacet>();
		Category category = null;
		Status approveStatus = null;
		for (CategoryFacetOrderWrapper categoryFacetWrapper : categoryFacetWrappers) {
			SearchCriteria criteria = new SearchCriteria();
			Map<String, Object> eqCriteria = criteria.getSearchTerms();
			eqCriteria.put("obj.catgFacetId", categoryFacetWrapper.getCategoryFacetId());
			ArrayList<CategoryFacet> catFacets = new ArrayList<CategoryFacet>(
					(List<CategoryFacet>) ((CategoryFacetDAO) baseDAO).executeQuery(criteria));
			CategoryFacet categoryFacet = catFacets.get(0);
			categoryFacet.setDisplayOrder(categoryFacetWrapper.getDisplayOrder());
			categoryFacets.add(categoryFacet);
			if (category == null) {
				category = categoryNodeService.retrieveById(categoryFacetWrapper.getCategoryNodeId());
			}
			saveFacetDisplayOrder(category, categoryFacet, categoryFacetWrapper, approveStatus);
		}
		if (category != null) {
			categoryNodeService.update(category);
		}
		return categoryFacets;
	}

  /*
   * (non-Javadoc)
   * @see com.bestbuy.search.merchandising.service.ICategoryFacetService#setDisplayOrder(java.util.List)
   */
  public List<CategoryFacet> setDisplayOrder(List<CategoryFacet> categoryFacets) throws ServiceException {
    if (categoryFacets != null && !categoryFacets.isEmpty()) {
      for (int i = 0; i < categoryFacets.size(); i++) {
        CategoryFacet categoryFacet = categoryFacets.get(i);
        String categoryId = categoryFacet.getCategoryNode().getCategoryNodeId();

        if (categoryFacet.getFacet().getDisplay().equals(DisplayEnum.Y) && categoryFacet.getDepFacet() == null) {
          try {
            Long maxOrder = ((CategoryFacetDAO) baseDAO).getMaxDisplayOrder(categoryId);
            maxOrder = (maxOrder == null) ? 0 : ++maxOrder;
            categoryFacet.setDisplayOrder(maxOrder);
          }

          catch (DataAcessException e) {
            throw new ServiceException("Error while retrieving the current max display order", e);
          }
        }

        else {
          categoryFacet.setDisplayOrder(null);
        }
      }
    }

    return categoryFacets;
  }

  /*
   * (non-Javadoc)
   * @see
   * com.bestbuy.search.merchandising.service.ICategoryFacetService#loadDependantFacetsForCategoryDisplayOrder(java.
   * lang.String)
   */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = ServiceException.class, timeout = -1)
	public List<IWrapper> loadDependantFacetsForCategoryDisplayOrder(String categoryId) throws ServiceException {
		List<String> parentPaths = null;
		String categoryPath = null;
		List<CategoryFacet> categoryFacets = null;
		  
		// Check if the category id is not null and not empty
		if (categoryId == null || categoryId.trim().length() == 0) {
			throw new ServiceException("Null/Empty Category ID");
		}
		  
		// Fetch the category path from the category id
		categoryPath = categoryNodeService.getCategoryPath(categoryId);
		  
		if (categoryPath != null && categoryPath.trim().length() > 0) {
			// Check if the current path has a pipe. If yes, get parent paths
			if (categoryPath.lastIndexOf(STR_PIPE) != -1) {
				parentPaths = getParentCategoryPaths(categoryPath);
			}
			try {
				categoryFacets = ((CategoryFacetDAO) baseDAO).loadDependantFacetsForCategoryDisplayOrder(categoryPath,parentPaths);
			} catch (DataAcessException dae) {
				throw new ServiceException("Error while retriving DependantFacetsForCategoryDisplayOrder" +dae);
			}
		}
		return CategoryFacetOrderWrapper.getWrappers(categoryFacets);
	}
	
	/*
	* (non-Javadoc)
	* @see
	* com.bestbuy.search.merchandising.service.ICategoryFacetService#loadHiddenFacetsForCategoryDisplayOrder(java.lang
	* .String)
	*/
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = ServiceException.class, timeout = -1)
	public List<IWrapper> loadHiddenFacetsForCategoryDisplayOrder(String categoryId) throws ServiceException {
		List<String> parentPaths = null;
		String categoryPath = null;
		List<CategoryFacet> categoryFacets = null;
		  
		// Check if the category id is not null and not empty
		if (categoryId == null || categoryId.trim().length() == 0) {
			throw new ServiceException("Null/Empty Category ID");
		}
		  
		// Fetch the category path from the category id
		categoryPath = categoryNodeService.getCategoryPath(categoryId);
		  
		if (categoryPath != null && categoryPath.trim().length() > 0) {
			// Check if the current path has a pipe. If yes, get parent paths
			if (categoryPath.lastIndexOf(STR_PIPE) != -1) {
				parentPaths = getParentCategoryPaths(categoryPath);
			}
			try {
				categoryFacets = ((CategoryFacetDAO) baseDAO).loadHiddenFacetsForCategoryDisplayOrder(categoryPath,parentPaths);
			} catch (DataAcessException dae) {
				throw new ServiceException("Error while retriving hiddenFacetsForCategoryDisplayOrder" +dae);
			}
		}
		return CategoryFacetOrderWrapper.getWrappers(categoryFacets);
	}

  /*
   * (non-Javadoc)
   * @see com.bestbuy.search.merchandising.service.ICategoryFacetService#invalidateContextsForFacet(com.bestbuy.search.
   * merchandising.domain.Facet)
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = ServiceException.class, timeout = -1)
  public void invalidateContextsForFacet(Facet facet) throws ServiceException {
    try {
      List<CategoryFacet> categoryFacets = ((CategoryFacetDAO) baseDAO).loadContextsForFacetForInvalidation(facet);

      for (CategoryFacet categoryFacet : categoryFacets) {
        categoryFacet.setIsActive("N");
        ((CategoryFacetDAO) baseDAO).update(categoryFacet);
      }
    }

    catch (DataAcessException e) {
      throw new ServiceException("Error while trying to invalidate the contexts", e);
    }
  }

  /**
   * Invalidate the category facets
   * 
   * @param categoryFacetIds
   * @throws ServiceException
   */
  public void updateCategoryFacet(String categoryFacetIds) throws ServiceException {
    try {
      baseDAO.updateContext(categoryFacetIds);
    } catch (DataAcessException dae) {
      throw new ServiceException(dae);
    }
  }

  /**
   * Retrieves all facets of a category
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = ServiceException.class, timeout = -1)
  @Override
  public List<IWrapper> loadFacetCategory(String catgId, boolean includeFacetsFromItsParentCategories) throws ServiceException {
    List<IWrapper> categoryFacetWrappers = null;
    if (includeFacetsFromItsParentCategories) {
      try {
        String categoryPath = categoryNodeService.getCategoryPath(catgId);
        List<CategoryFacet> categoryFacets = ((CategoryFacetDAO) baseDAO).loadFacetForCatg(categoryPath, getParentCategoryPaths(categoryPath));
        categoryFacetWrappers = CategoryFacetWrapper.getAllFacetsforCatg(categoryFacets);
      } catch (DataAcessException dae) {
        throw new ServiceException("Error while retriving the facets for a category from DB", dae);
      }

    } else {
      categoryFacetWrappers = loadFacetCategory(catgId);
    }

    return categoryFacetWrappers;
  }
  
  private List<String> getParentCategoryPaths(String categoryPath) {
    String[] categories = StringUtils.split(categoryPath, STR_PIPE);
    List<String> returnCategories = new ArrayList<String>();
    String newCategoryPath = "";
    for (int i = 0; i < categories.length-1; i++) {
      newCategoryPath = newCategoryPath + categories[i];
      returnCategories.add(newCategoryPath);
      newCategoryPath = newCategoryPath + STR_PIPE;

    }
    return returnCategories;
  }
  	
  	/**
  	 * Method to save facet display order
  	 * 
  	 * @param category
  	 * @param categoryFacet
  	 * @param categoryFacetWrapper
  	 * @param approveStatus
  	 * @throws ServiceException
  	 */
	private void saveFacetDisplayOrder(Category category, CategoryFacet categoryFacet, 
		  CategoryFacetOrderWrapper categoryFacetWrapper, Status approveStatus) throws ServiceException {
		boolean exists = false;
		if (category.getFacetDisplayOrder() != null && category.getFacetDisplayOrder().size() > 0) {
			for (FacetsDisplayOrder fdo : category.getFacetDisplayOrder()) {
				if (fdo.getFacet().getFacetId().longValue() == 
						categoryFacet.getFacet().getFacetId().longValue()) {
					if (approveStatus == null) {
						try {
							approveStatus = statusService.retrieveById(APPROVE_STATUS);
						} catch (ServiceException e) {
							throw new ServiceException(e);
						}
					}
					fdo.setStatus(approveStatus);
					fdo.setDisplayOrder(categoryFacetWrapper.getDisplayOrder() + 1);
					exists = true;
					break;
				}
			}
		}
		if (!exists) {
			FacetsDisplayOrder fdo = new FacetsDisplayOrder();
			fdo.setCategory(category);
			fdo.setFacet(categoryFacet.getFacet());
			fdo.setDisplayOrder(categoryFacetWrapper.getDisplayOrder() + 1);
			if (approveStatus == null) {
				try {
					approveStatus = statusService.retrieveById(APPROVE_STATUS);
				} catch (ServiceException e) {
					throw new ServiceException(e);
				}
			}
			fdo.setStatus(approveStatus);
			category.getFacetDisplayOrder().add(fdo);
		}
	}
	
	/**
	 * Method to set update status for facet
	 * 
	 * @param facet
	 * @param statusName
	 * @return Facet
	 * @throws ServiceException
	 */
	private Facet setUpdateStatusForFacet(Facet facet, String statusName) throws ServiceException {
		Long statusId = statusService.getStatusId(statusName);
		Status status = statusService.retrieveById(statusId);
		Users userId = userUtil.getUser();
		facet.setUpdatedDate(new Date());
		facet.setUpdatedBy(userId);
		facet.setStatus(status);
		return facet;
	}
}