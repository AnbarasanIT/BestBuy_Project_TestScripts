package com.bestbuy.search.merchandising.service;

import static com.bestbuy.search.merchandising.common.MerchandisingConstants.DELETE_STATUS;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.APPROVE;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.DELETE;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.PUBLISH;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.REJECT;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.EDIT;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bestbuy.search.merchandising.common.FacetWrapperConverter;
import com.bestbuy.search.merchandising.common.MerchandisingConstants;
import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.FacetDAO;
import com.bestbuy.search.merchandising.dao.IFacetDAO;
import com.bestbuy.search.merchandising.domain.Attribute;
import com.bestbuy.search.merchandising.domain.AttributeValue;
import com.bestbuy.search.merchandising.domain.CategoryFacet;
import com.bestbuy.search.merchandising.domain.Facet;
import com.bestbuy.search.merchandising.domain.FacetAttributeValueOrder;
import com.bestbuy.search.merchandising.domain.FacetsDisplayOrder;
import com.bestbuy.search.merchandising.domain.Status;
import com.bestbuy.search.merchandising.domain.Users;
import com.bestbuy.search.merchandising.security.UserUtil;
import com.bestbuy.search.merchandising.workflow.GeneralWorkflow;
import com.bestbuy.search.merchandising.workflow.exception.InvalidStatusException;
import com.bestbuy.search.merchandising.workflow.exception.WorkflowException;
import com.bestbuy.search.merchandising.wrapper.AttributeValueWrapper;
import com.bestbuy.search.merchandising.wrapper.FacetWrapper;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.PaginationWrapper;

/**Service layer for Facet
 * @author Chanchal.kumari
 * Date - 24th Oct 2012
 * @Modified By Kalaiselvi Jaganathan - Modified the approve/delete/reject/update status method logic to update the status in facet table
 * and added create facet method
 */

public class FacetService extends BaseService<Long,Facet> implements IFacetService{

	@Autowired
	public void setDao(FacetDAO dao) {
		this.baseDAO = dao;
	}

	@Autowired
	private UserUtil userUtil;

	@Autowired
	private IFacetDAO facetDAO;

	@Autowired
	private IStatusService statusService;

	@Autowired
	private GeneralWorkflow generalWorkflow;

	@Autowired
	private FacetWrapperConverter facetWrapperConverter;

	@Autowired
	private IAttributeService attributeService;

	@Autowired
	private ICategoryFacetService categoryFacetService;
	
	@Autowired
	private IAttributeValueService attributeValueService;

	/**
	 * @param to set the attributeValueService 
	 */
	public void setAttributeValueService(
			IAttributeValueService attributeValueService) {
		this.attributeValueService = attributeValueService;
	}

	/**
	 * @param categoryFacetService the categoryFacetService to set
	 */
	public void setCategoryFacetService(ICategoryFacetService categoryFacetService) {
		this.categoryFacetService = categoryFacetService;
	}

	public void setGeneralWorkflow(GeneralWorkflow generalWorkflow) {
		this.generalWorkflow = generalWorkflow;
	}

	public void setFacetDAO(IFacetDAO facetDAO) {
		this.facetDAO = facetDAO;
	}

	/**
	 * @param attributeService the attributeService to set
	 */
	public void setAttributeService(IAttributeService attributeService) {
		this.attributeService = attributeService;
	}

	/**
	 * @param facetWrapperConverter the facetWrapperConverter to set
	 */
	public void setFacetWrapperConverter(FacetWrapperConverter facetWrapperConverter) {
		this.facetWrapperConverter = facetWrapperConverter;
	}

	/**
	 * @param statusService the statusService to set
	 */
	public void setStatusService(IStatusService statusService) {
		this.statusService = statusService;
	}

	/**
	 * @param to set the userUtil 
	 */
	public void setUserUtil(UserUtil userUtil) {
		this.userUtil = userUtil;
	}

	/**
	 * Method to load the facets from the facet entity
	 * @throws ServiceException
	 */
	public  List<FacetWrapper> loadFacets(PaginationWrapper paginationWrapper) throws ServiceException{

		List<FacetWrapper> facetWrappers = null;
		SearchCriteria criteria = new SearchCriteria();
		Map<String,Object>	notEqCriteria = criteria.getNotEqCriteria();
		//Get the Facets which are not in deleted status
		notEqCriteria.put("status.statusId", DELETE_STATUS);
		//Condition to get the facets based on modfied date desc order
		setColumnValuesForOrderCriteria(paginationWrapper, criteria);
		//Search based on specific columns
		setColumnValuesForSearch(paginationWrapper, criteria);
		criteria.setPaginationWrapper(paginationWrapper);

		try{ 
			List<Facet> facets = this.loadEntitiesWithCriteria(criteria);
			facetWrappers = FacetWrapper.getAllFacets(facets);
			try{
				for(IWrapper facetWrapper : facetWrappers){
					((FacetWrapper)facetWrapper).setActions(FacetWrapper.getActions(generalWorkflow.getActionsForStatus(((FacetWrapper)facetWrapper).getStatus())));
				}
			}
			catch(InvalidStatusException e){
				throw new ServiceException("Error while retriving the actions "+ e);
			}
		}
		catch(DataAcessException dae){
			throw new ServiceException("Error while retriving the facets from DB", dae);
		}
		return facetWrappers;
	}

	/**
	 * Method to update the status of facet to deleted
	 * @param id A Long with the facet id of the facet that we want to approve
	 * @throws ServiceException
	 * @author chanchal.kumari
	 * Date 25th Oct 2012
	 * @throws DataAcessException 
	 */
	@Transactional(propagation = Propagation.REQUIRED, 
			isolation = Isolation.DEFAULT,
			readOnly = false,
			rollbackFor = ServiceException.class,
			timeout = -1)
	public void deleteFacet(Long id) throws ServiceException {
		try {
			Facet facet = retrieveById(id);
			removeFacetDisplayOrders(facet);
			updateStatusForFacet(facet, generalWorkflow.stepForward(facet.getStatus().getStatus(), DELETE));
			categoryFacetService.invalidateContextsForFacet(facet);
		} 
		catch (WorkflowException e) {
			throw new ServiceException("Error while performing the workflow step", e);
		}
	}

	/**
	 * Approves a facet with the given id
	 * @param id A Long with the facet id of the facet that we want to approve
	 * @throws ServiceException
	 * @author chanchal.kumari
	 * Date 25th Oct 2012
	 * @throws DataAcessException 
	 */
	@Transactional(propagation = Propagation.REQUIRED, 
			isolation = Isolation.DEFAULT, 
			readOnly = false, 
			rollbackFor = ServiceException.class, 
			timeout = -1)
	public void approveFacet(Long id) throws ServiceException {
		try {
			Facet facet = retrieveById(id);
			updateStatusForFacet(facet, generalWorkflow.stepForward(facet.getStatus().getStatus(), APPROVE));
		} 
		catch (WorkflowException e) {
			throw new ServiceException("Error while performing the workflow step", e);
		}
	}

	/**
	 * Rejects a facet with the given id
	 * @param id A Long with the facet id of the facet that we want to reject
	 * @throws ServiceException
	 * @author chanchal.kumari
	 * Date 25th Oct 2012
	 * @throws DataAcessException 
	 */
	@Transactional(propagation = Propagation.REQUIRED, 
			isolation = Isolation.DEFAULT, 
			readOnly = false, 
			rollbackFor = ServiceException.class, 
			timeout = -1)
	public void rejectFacet(Long id) throws ServiceException {
		try {
			Facet facet = retrieveById(id);
			updateStatusForFacet(facet, generalWorkflow.stepForward(facet.getStatus().getStatus(), REJECT));
		} 
		catch (WorkflowException e) {
			throw new ServiceException("Error while performing the workflow step", e);
		}

	}

	/**
	 * Method to update status of the Facet
	 * @param asset
	 * @param status
	 * @throws ServiceException
	 * @author chanchal.kumari
	 * Date 25th Oct 2012
	 * @throws DataAcessExcepton 
	 */
	@Override
	public void updateStatusForFacet(Facet facet, String statusName) throws ServiceException {
		facet = setUpdateStatusForFacet(facet, statusName);
		update(facet);
	}

	/**
	 * Method to create a new Facet 
	 * @param facet
	 * @throws ServiceException,ParseException
	 * @throws ParseException 
	 */

	@Transactional(propagation = Propagation.REQUIRED,
			isolation = Isolation.DEFAULT,
			readOnly = false, 
			rollbackFor = ServiceException.class,
			timeout = -1)
	public void createFacet(FacetWrapper facetWrapper) throws ServiceException, ParseException {

		//Convert Facet data from wrapper to entity
		Facet facet = facetWrapperConverter.wrapperConverter(facetWrapper, new Facet());
		
		//Set the status of the facet
		if(facetWrapper.getStatusId() == null){
			Status status = statusService.retrieveById(MerchandisingConstants.DRAFT_STATUS);
			facet.setStatus(status);
		}
		//setting the attribute Id information
		if(facetWrapper.getAttributeId() != null){
			//Set the attribute
			Attribute attribute = attributeService.retrieveById(facetWrapper.getAttributeId());
			if(attribute != null){ 
				facet.setAttribute(attribute);
			}
		}
		//Combine both the promote and exclude data list to one list
		List<AttributeValueWrapper> attributeValueWrappers = new ArrayList<AttributeValueWrapper>();
		if(facetWrapper.getPromoteList() != null && facetWrapper.getPromoteList().size() > 0){
			attributeValueWrappers.addAll(facetWrapper.getPromoteList());
		}

		if(facetWrapper.getExcludeList() != null && facetWrapper.getExcludeList().size() > 0){
			attributeValueWrappers.addAll(facetWrapper.getExcludeList());
		}

		//setting the attribute Value info
		List<FacetAttributeValueOrder> facetAttributeValueOrders = new ArrayList<FacetAttributeValueOrder>();
		if(attributeValueWrappers.size() > 0 ){
			facetAttributeValueOrders =  facetWrapperConverter.getAttributeValues(attributeValueWrappers, facet);
			facet.setFacetAttributeOrder(facetAttributeValueOrders);
		}

		//setting the Context Path info
		if(facetWrapper.getCategoryWrapper() != null && facetWrapper.getCategoryWrapper().size() > 0){
			List<CategoryFacet> categoryFacets = facetWrapperConverter.getCategoryFacets(facetWrapper.getCategoryWrapper(),facet);
			facet.setCategoryFacet(categoryFacets);
		}
		//Save the facets
		facet=save(facet);
	}

	/**
	 * Method to update a Facet 
	 * @param facet
	 * @throws ServiceException,ParseException
	 * @throws ParseException 
	 * @throws DataAcessException 
	 */

	@Transactional(propagation = Propagation.REQUIRED,
			isolation = Isolation.DEFAULT,
			readOnly = false, 
			rollbackFor = ServiceException.class,
			timeout = -1)
	public void updateFacet(FacetWrapper facetWrapper) throws ServiceException, ParseException, DataAcessException {

		//Retrieve the data from the database before updating the new data
		Facet facet = facetDAO.retrieveById(facetWrapper.getFacetId());
		////Update the facet data with the data from the facet Wrapper
		facet = facetWrapperConverter.wrapperConverter(facetWrapper, facet);

		List<FacetAttributeValueOrder> facetAttributeValueOrders = facet.getFacetAttributeOrder();

		//Check if all the Promoted and excluded list are removed from UI and delete it from entity
		if(( facetWrapper.getPromoteList() == null) && (facetWrapper.getExcludeList() == null)){
			facetAttributeValueOrders.clear();
			facet.setFacetAttributeOrder(facetAttributeValueOrders);
		}
		else{
			//Combine both the promote and exclude data list to one list
			List<AttributeValueWrapper> attributeValueWrappers = new ArrayList<AttributeValueWrapper>();
			if(facetWrapper.getPromoteList() != null && facetWrapper.getPromoteList().size() > 0){
				attributeValueWrappers.addAll(facetWrapper.getPromoteList());
			}

			if(facetWrapper.getExcludeList() != null && facetWrapper.getExcludeList().size() > 0){
				attributeValueWrappers.addAll(facetWrapper.getExcludeList());
			}

			
			 
		  facetWrapperConverter.updateFacetValueOrders(facet,attributeValueWrappers);
				//facet.getFacetAttributeOrder().clear();
				//facet.addFacetAttributeOrders(facetAttributeValueOrders);

		}

		if(facetWrapper.getCategoryWrapper() != null){
			//Update the facet value order data
			 facetWrapperConverter.updateCategoryFacets(facetWrapper.getCategoryWrapper(), facet);
			//facet.addCategoryFacets(categoryFacets);
		}
		
		try {
			facet = setUpdateStatusForFacet(facet, generalWorkflow.stepForward(facet.getStatus().getStatus(), EDIT));
		} catch (WorkflowException e) {
			throw new ServiceException("Error while performing the workflow step", e);
		}

		//Update the facets
		facet=update(facet);
	}



	/**
	 * Method to load the data for edit pop-up facet based on facet id sent from UI
	 */
	public FacetWrapper loadEditFacetData(Long facetId) throws ServiceException { 
		//Retrieve the data from db for the specific facetId
		Facet facet = retrieveById(facetId);
		//Call the wrapper class to load the data from entity to wrapper
		FacetWrapper facetWrapper=FacetWrapper.getFacets(facet);
		return facetWrapper;
	}


	/**
	 * Method that publishes the Facet
	 */
	@Transactional(propagation = Propagation.REQUIRED, 
			isolation = Isolation.DEFAULT, 
			readOnly = false, 
			rollbackFor = ServiceException.class, 
			timeout = -1)
	public void publishFacet(Long id, String currentStatus) throws ServiceException {
		try {
			Facet facet = this.retrieveById(id);
			String facetStatus = generalWorkflow.stepForward(currentStatus, PUBLISH);
			Long statusId = statusService.getStatusId(facetStatus);
			Status status = statusService.retrieveById(statusId);
			facet.setUpdatedDate(new Date());
			facet.setStatus(status);
			facet = this.update(facet);
		}  
		catch (WorkflowException e) {
			throw new ServiceException("Error while performing the workflow step Publish", e);
		}
	}


	/**
	 * Updates the List of Facets to DataBase
	 * @param facets
	 * @throws ServiceException
	 */
	@Transactional(propagation = Propagation.REQUIRED, 
			isolation = Isolation.DEFAULT, 
			readOnly = false, 
			rollbackFor = ServiceException.class, 
			timeout = -1)
	public void updateFacets(List<Facet> facets) throws ServiceException{
		try {

			this.update(facets);
		}  
		catch (DataAccessException e) {
			throw new ServiceException("Error while Updating the Facets", e);
		}
	}
	
	/**
	 * To get the Attribute values for the facet
	 * @throws DataAcessException 
	 */
	@Transactional(propagation = Propagation.REQUIRED, 
			isolation = Isolation.DEFAULT, 
			readOnly = false, 
			rollbackFor = ServiceException.class, 
			timeout = -1)
	public List<IWrapper> loadFacetAttributeValues(Long facetId) throws ServiceException, DataAcessException {
		try {

			//Retrieve the data from the database
			Facet facet = facetDAO.retrieveById(facetId);
			//Set the search criteria
			SearchCriteria criteria = new SearchCriteria();
			Map<String,Object>	searchTerms = criteria.getSearchTerms();
			searchTerms.put("obj.attribute.attributeId", facet.getAttribute().getAttributeId());
			List<AttributeValue> attributeValues = attributeValueService.loadAttributeValues(criteria);
			List<IWrapper> attributeValueWrappers = AttributeValueWrapper.getAttributeValueWrapper(attributeValues);
			return attributeValueWrappers;
		}  
		catch (DataAccessException e) {
			throw new ServiceException("Error while retrieving attribute values for the Facet", e);
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
	
	/**
	 * Remove facet display orders for dependent facet
	 * 
	 * @param categoryWrappers
	 * @param facet
	 */
	private void removeFacetDisplayOrders(Facet facet) throws ServiceException {
		Status deleteStatus = null;
		if (facet.getFacetDisplayOrder() != null && facet.getFacetDisplayOrder().size() > 0) {
			for (FacetsDisplayOrder facetDisplayOrder : facet.getFacetDisplayOrder()) {
				if (deleteStatus == null) {
					try {
						deleteStatus = statusService.retrieveById(DELETE_STATUS);
					} catch (ServiceException e) {
						throw new ServiceException(e);
					}
				}
				if (deleteStatus != null) {
					facetDisplayOrder.setStatus(deleteStatus);
				}
			}
		}
	}

	/**
	 * Method to get display order ids for child paths
	 * 
	 * @param categoryPath
	 * @return List<Long>
	 * @throws ServiceException
	 */
	public List<Long> getDisplayOrderIdsForChildPaths(String categoryPath) throws ServiceException {
		List<Long> ids = null;
		try {
			ids = facetDAO.getDisplayOrderIdsForChildPaths(categoryPath);
		} catch (ServiceException e) {
			throw new ServiceException(e);
		} catch (DataAccessException e) {
			throw new ServiceException(e);
		}
		return ids;
	}
}
