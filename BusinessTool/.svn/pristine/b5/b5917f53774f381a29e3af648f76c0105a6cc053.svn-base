package com.bestbuy.search.merchandising.service;

import static com.bestbuy.search.merchandising.common.MerchandisingConstants.DELETE_STATUS;
import static com.bestbuy.search.merchandising.common.MerchandisingConstants.DRAFT_STATUS;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.APPROVE;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.DELETE;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.EDIT;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.PUBLISH;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.REJECT;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bestbuy.search.merchandising.common.BoostAndBlockWrapperConverter;
import com.bestbuy.search.merchandising.common.MerchandisingConstants;
import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.BoostAndBlockDao;
import com.bestbuy.search.merchandising.dao.IBoostAndBlockDao;
import com.bestbuy.search.merchandising.domain.BoostAndBlock;
import com.bestbuy.search.merchandising.domain.BoostAndBlockProduct;
import com.bestbuy.search.merchandising.domain.Status;
import com.bestbuy.search.merchandising.security.UserUtil;
import com.bestbuy.search.merchandising.workflow.GeneralWorkflow;
import com.bestbuy.search.merchandising.workflow.exception.InvalidStatusException;
import com.bestbuy.search.merchandising.workflow.exception.WorkflowException;
import com.bestbuy.search.merchandising.wrapper.BoostAndBlockWrapper;
import com.bestbuy.search.merchandising.wrapper.BoostBlockProductWrapper;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.PaginationWrapper;
/**
 * @author Kalaiselvi Jaganathan
 * Service Layer for BoostAndBlock
 */
public class BoostAndBlockService extends BaseService<Long,BoostAndBlock> implements IBoostAndBlockService{

	@Autowired
	public void setDao(BoostAndBlockDao dao) {
		this.baseDAO = dao;
	}

	@Autowired
	private BoostAndBlockWrapperConverter boostAndBlockWrapperConverter;

	@Autowired
	private IBoostAndBlockDao boostAndBlockDAO;
	
	@Autowired
	private UserUtil userUtil;

	@Autowired
	private GeneralWorkflow generalWorkflow;

	@Autowired
	private IStatusService statusService;
	
	@Autowired
	private IBoostAndBlockProductService boostAndBlockProductService;

	/**
	 * @param to set the boostAndBlockProductService 
	 */
	public void setBoostAndBlockProductService(
			IBoostAndBlockProductService boostAndBlockProductService) {
		this.boostAndBlockProductService = boostAndBlockProductService;
	}

	/**
	 * @param to set the boostAndBlockWrapperConverter 
	 */
	public void setBoostAndBlockWrapperConverter(
			BoostAndBlockWrapperConverter boostAndBlockWrapperConverter) {
		this.boostAndBlockWrapperConverter = boostAndBlockWrapperConverter;
	}

	/**
	 * @param to set the boostAndBlockDAO 
	 */
	public void setBoostAndBlockDAO(IBoostAndBlockDao boostAndBlockDAO) {
		this.boostAndBlockDAO = boostAndBlockDAO;
	}

	/**
	 * @param to set the generalWorkflow 
	 */
	public void setGeneralWorkflow(GeneralWorkflow generalWorkflow) {
		this.generalWorkflow = generalWorkflow;
	}

	/**
	 * @param to set the statusService 
	 */
	public void setStatusService(IStatusService statusService) {
		this.statusService = statusService;
	}
	
	public void setUserUtil(UserUtil userUtil) {
		this.userUtil = userUtil;
	}

	/**
	 * Method to retrieve the list of Boost&Block which are not in the deleted status from DB
	 * @throws ServiceException
	 * @author Chanchal.Kumari
	 */
	public  List<IWrapper> load(PaginationWrapper paginationWrapper) throws ServiceException{
    List<IWrapper> wrappers = null;
    SearchCriteria criteria = new SearchCriteria();
    Map<String, Object> notEqCriteria = criteria.getNotEqCriteria();
    notEqCriteria.put("status.statusId", DELETE_STATUS);
    setColumnValuesForOrderCriteria(paginationWrapper, criteria);
    setColumnValuesForSearch(paginationWrapper, criteria);

    criteria.setPaginationWrapper(paginationWrapper);
    try {
      List<BoostAndBlock> boostAndBlocks = boostAndBlockDAO.retrieveBoostsAndBlocks(criteria);
      wrappers = BoostAndBlockWrapper.getBoostAndBlocks(boostAndBlocks);
    }

    catch (DataAcessException da) {
      throw new ServiceException("Error while retriving the boostAndBlocks from DB", da);
    }

    return wrappers;
  }

	/**
	 * Method to create a new BoostAndBlock 
	 * @param BoostAndBlock
	 * @throws ServiceException,ParseException
	 * @throws ParseException 
	 */

	@Transactional(propagation = Propagation.REQUIRED,
			isolation = Isolation.DEFAULT,
			readOnly = false, 
			rollbackFor = ServiceException.class,
			timeout = -1)
	public IWrapper createBoostAndBlock(BoostAndBlockWrapper boostAndBlockWrapper) throws ServiceException, ParseException, InvalidStatusException {

		//Convert BoostAndBlock data from wrapper to entity
		BoostAndBlock boostAndBlock = boostAndBlockWrapperConverter.wrapperConverter(boostAndBlockWrapper, new BoostAndBlock());

		//Set the status of the BoostAndBlock
		Status status = statusService.retrieveById(MerchandisingConstants.DRAFT_STATUS);
		boostAndBlock.setStatus(status);

		//Combine both the boost and block product data list to one list
		List<BoostBlockProductWrapper> boostBlockProductWrappers = new ArrayList<BoostBlockProductWrapper>();
		if(boostAndBlockWrapper.getBoostProduct() != null && boostAndBlockWrapper.getBoostProduct().size() > 0){
			boostBlockProductWrappers.addAll(boostAndBlockWrapper.getBoostProduct());
		}

		if(boostAndBlockWrapper.getBlockProduct() != null && boostAndBlockWrapper.getBlockProduct().size() > 0){
			boostBlockProductWrappers.addAll(boostAndBlockWrapper.getBlockProduct());
		}

		//Set the boost and block product data
		List<BoostAndBlockProduct> boostBlockProducts = new ArrayList<BoostAndBlockProduct>();
		if(boostBlockProductWrappers.size() > 0 ){
			boostBlockProducts = boostAndBlockWrapperConverter.getProducts(boostBlockProductWrappers, boostAndBlock);
		}
		boostAndBlock.setBoostAndBlockProducts(boostBlockProducts);

		//Save the boostAndBlock
		boostAndBlock=save(boostAndBlock);

		//Return the saved entity to the wrapper to refresh the grid
		BoostAndBlockWrapper boostBlockWrapper=BoostAndBlockWrapper.getBoostBlock(boostAndBlock);
		boostBlockWrapper.setActions(BoostAndBlockWrapper.getValidActions(generalWorkflow.getActionsForStatus(boostAndBlock.getStatus().getStatus())));
		return boostBlockWrapper;
	}

	/**
	 * Method to update a new BoostAndBlock 
	 * @param BoostAndBlock
	 * @throws ServiceException,ParseException
	 * @throws ParseException 
	 * @throws DataAcessException
	 * @throws InvalidStatusException 
	 */

	@Transactional(propagation = Propagation.REQUIRED,
			isolation = Isolation.DEFAULT,
			readOnly = false, 
			rollbackFor = ServiceException.class,
			timeout = -1)
	public IWrapper updateBoostAndBlock(BoostAndBlockWrapper boostAndBlockWrapper) throws ServiceException, ParseException, DataAcessException, InvalidStatusException,WorkflowException {

		//Retrieve the data from the database before updating the new data
		BoostAndBlock boostAndBlock = boostAndBlockDAO.retrieveById(boostAndBlockWrapper.getBoostBlockId());

		//Convert BoostAndBlock data from wrapper to entity
		boostAndBlock = boostAndBlockWrapperConverter.wrapperConverter(boostAndBlockWrapper, boostAndBlock);
		List<BoostAndBlockProduct> boostBlockProducts = boostAndBlock.getBoostAndBlockProducts();

		//Check if all the Boosted and blocked SKUs are removed from UI and delete it from entity
		if((boostAndBlockWrapper.getBlockProduct() == null) && (boostAndBlockWrapper.getBoostProduct() == null)){
			boostBlockProducts.clear();
			boostAndBlock.setBoostAndBlockProducts(boostBlockProducts);
		}
		else{
			//Combine both the boost and block product data list to one list
			List<BoostBlockProductWrapper> boostBlockProductWrappers = new ArrayList<BoostBlockProductWrapper>();
			if(boostAndBlockWrapper.getBoostProduct() != null){
				boostBlockProductWrappers.addAll(boostAndBlockWrapper.getBoostProduct());
			}
			if(boostAndBlockWrapper.getBlockProduct() != null){
				boostBlockProductWrappers.addAll(boostAndBlockWrapper.getBlockProduct());
			}
			boostBlockProducts=boostAndBlockWrapperConverter.updateProducts(boostAndBlock,boostBlockProductWrappers);
			//boostAndBlock.setBoostAndBlockProducts(boostBlockProducts);
			boostAndBlockProductService.save(boostBlockProducts);
			
		}
		String status = generalWorkflow.stepForward(boostAndBlock.getStatus().getStatus(), EDIT);
		updateBloostBlockStatus(boostAndBlock, status);
		
		//Update the boostAndBlock
		boostAndBlock=update(boostAndBlock);

		//Return the saved entity to the wrapper to refresh the grid
		BoostAndBlockWrapper boostBlockWrapper=BoostAndBlockWrapper.getBoostBlock(boostAndBlock);
		boostBlockWrapper.setActions(BoostAndBlockWrapper.getValidActions(generalWorkflow.getActionsForStatus(boostAndBlock.getStatus().getStatus())));
		return boostBlockWrapper;
	}

	/**
	 * Method to load the data for edit pop-up BoostBlock data based on boostAndBlock id sent from UI
	 */
	public BoostAndBlockWrapper loadEditBoostBlockData(Long boostAndBlockId) throws ServiceException { 
		//Retrieve the data from db for the specific  boostAndBlock Id
		BoostAndBlock boostAndBlock = retrieveById(boostAndBlockId);
		//Call the wrapper class to load the data from entity to wrapper
		BoostAndBlockWrapper boostAndBlockWrapper=BoostAndBlockWrapper.getBoostBlock(boostAndBlock);
		return boostAndBlockWrapper;
	}
	
	/**
	 * performs the delete action on boostAndBlock
	 * @param id The boostAndBlockId for this BoostAndBlock
	 * @return IWrapper 
	 * @throws ServiceException If any error occurs
	 * @author Chanchal.Kumari
	 */

	@Transactional(propagation = Propagation.REQUIRED, 
			isolation = Isolation.DEFAULT,
			readOnly = false,
			rollbackFor = ServiceException.class,
			timeout = -1)
	public IWrapper deleteBoostAndBlock(Long id) throws ServiceException {
		try {
			BoostAndBlock boostAndBlock = this.retrieveById(id);
			updateBloostBlockStatus(boostAndBlock,generalWorkflow.stepForward(boostAndBlock.getStatus().getStatus(), DELETE));
			setAuditInfo(boostAndBlock);
			boostAndBlock = this.update(boostAndBlock);
			return getIwrapper(boostAndBlock);
		} 
		
		catch (WorkflowException e) {
			throw new ServiceException("Error while performing the workflow step", e);
		}
	}
	
	/**
	 * performs the approve action on boostAndBlock
	 * @param id The boostAndBlockId for this BoostAndBlock
	 * @return IWrapper
	 * @throws ServiceException If any error occurs
	 * @author Chanchal.Kumari
	 */

	@Transactional(propagation = Propagation.REQUIRED, 
			isolation = Isolation.DEFAULT,
			readOnly = false,
			rollbackFor = ServiceException.class,
			timeout = -1)
	public IWrapper approveBoostAndBlock(Long id) throws ServiceException {
		try {
			BoostAndBlock boostAndBlock = this.retrieveById(id);
			updateBloostBlockStatus(boostAndBlock,generalWorkflow.stepForward(boostAndBlock.getStatus().getStatus(), APPROVE));
			setAuditInfo(boostAndBlock);
			boostAndBlock = this.update(boostAndBlock);
			return getIwrapper(boostAndBlock);
		} 
		
		catch (WorkflowException e) {
			throw new ServiceException("Error while performing the workflow step", e);
		}
	}
	
	/**
	 * performs the reject action on boostAndBlock
	 * @param id The boostAndBlockId for this BoostAndBlock
	 * @return IWrapper
	 * @throws ServiceException If any error occurs
	 * @author Chanchal.Kumari
	 */

	@Transactional(propagation = Propagation.REQUIRED, 
			isolation = Isolation.DEFAULT,
			readOnly = false,
			rollbackFor = ServiceException.class,
			timeout = -1)
	public IWrapper rejectBoostAndBlock(Long id) throws ServiceException {
		try {
			BoostAndBlock boostAndBlock = this.retrieveById(id);
			updateBloostBlockStatus(boostAndBlock,generalWorkflow.stepForward(boostAndBlock.getStatus().getStatus(), REJECT));
			setAuditInfo(boostAndBlock);
			boostAndBlock = this.update(boostAndBlock);
			return getIwrapper(boostAndBlock);
		} 
		
		catch (WorkflowException e) {
			throw new ServiceException("Error while performing the workflow step", e);
		}
	}
	
	/**
	 * performs the publish action on boostBlock
	 * @param id The boostBlockId for this boostBlock
	 * @return IWrapper
	 * @throws ServiceException If any error occurs
	 * @author Chanchal.Kumari
	 */
	
	@Transactional(propagation = Propagation.REQUIRED, 
			isolation = Isolation.DEFAULT, 
			readOnly = false, 
			rollbackFor = ServiceException.class, 
			timeout = -1)
	public void publishBoostBlock(Long id) throws ServiceException{
		try {
			 BoostAndBlock boostAndBlock = this.retrieveById(id);
			 String statusString = generalWorkflow.stepForward(boostAndBlock.getStatus().getStatus(), PUBLISH);
			 Long statusId = statusService.getStatusId(statusString);// status must be changed
			 Status status = statusService.retrieveById(statusId);
			 boostAndBlock.setStatus(status);
			 boostAndBlock.setUpdatedDate(new Date());
			 boostAndBlock = this.update(boostAndBlock);
		 } 
		
		catch (WorkflowException e) {
			throw new ServiceException("Error while performing the workflow step", e);
		}
	}	

	
	/**
	 * This method updates the status to the boostandblock entity
	 * @param boostAndBlock
	 * @param statusString
	 * @throws ServiceException
	 * @author Chanchal.Kumari
	 */
	 void updateBloostBlockStatus(BoostAndBlock boostAndBlock,
			String statusString) throws ServiceException {
		try {
			Long statusId;
			if (null != statusString) {
				statusId = statusService.getStatusId(statusString);
			} else {
				statusId = DRAFT_STATUS;
			}
			Status status = statusService.retrieveById(statusId);
			boostAndBlock.setStatus(status);
		} catch (ServiceException e) {
			throw new ServiceException(
					"Error while changing the status for the synonym", e);
		}
	}
	
	/**
	 * This method returnd  a Iwrapper from the boostAndBlock entity
	 * @param boostAndBlock
	 * @return IWrapper
	 * @throws ServiceException
	 * @author Chanchal.Kumari
	 */
	IWrapper getIwrapper(BoostAndBlock boostAndBlock) throws ServiceException{
		
		BoostAndBlockWrapper boostAndBlockWrapper = (BoostAndBlockWrapper)BoostAndBlockWrapper.getBoostBlock(boostAndBlock);

		try{
			boostAndBlockWrapper.setActions(BoostAndBlockWrapper.getValidActions(generalWorkflow.getActionsForStatus(boostAndBlockWrapper.getStatus())));
		}
		catch (InvalidStatusException e) {
			throw new ServiceException(e);
		}
		return (IWrapper) boostAndBlockWrapper;
	}
	
	/**
	 * This method sets the updatedDate and updatedBy to the boostAndBlock entity.
	 * @param boostAndBlock
	 * @author Chanchal.Kumari
	 */
	private  void setAuditInfo(BoostAndBlock boostAndBlock){
		boostAndBlock.setUpdatedDate(new Date());
		boostAndBlock.setUpdatedBy(userUtil.getUser());
	}
	
	/* (non-Javadoc)
	 * @see com.bestbuy.search.merchandising.service.IBoostAndBlockService#validateNewBoostAndBlock(java.lang.String, java.lang.Long, java.lang.String)
	 */
	@Override
	public Integer validateNewBoostAndBlock(String searchTerm, Long searchProfileId, String categoryId) throws ServiceException {
		SearchCriteria criteria = new SearchCriteria();
		
		Map<String,Object> eqCriteria = criteria.getSearchTerms();
		Map<String,Object> notEqCriteria = criteria.getNotEqCriteria(); 
		Map<String,Object> ignoreCaseCriteria = criteria.getSearchTermsIgnoreCase();
		ignoreCaseCriteria.put("obj.term", searchTerm.toUpperCase());
		eqCriteria.put("obj.searchProfile.searchProfileId", searchProfileId);
		eqCriteria.put("obj.category.categoryNodeId", categoryId);
		notEqCriteria.put("obj.status.statusId", DELETE_STATUS);
		
		Integer result = 0;
		
		try {
			result = ((BoostAndBlockDao) baseDAO).getCount(criteria) > 0 ? 1 : result;
		} 
		
		catch (DataAcessException e) {
			throw new ServiceException("Error while trying to count the number of Boosts and Blocks for the combination", e);
		}
		
		return result;
	}	
}