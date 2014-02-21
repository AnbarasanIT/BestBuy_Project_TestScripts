package com.bestbuy.search.merchandising.service;

import static com.bestbuy.search.merchandising.common.MerchandisingConstants.DELETE_STATUS;
import static com.bestbuy.search.merchandising.common.MerchandisingConstants.DRAFT_STATUS;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.APPROVE;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.DELETE;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.EDIT;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.PUBLISH;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.REJECT;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.KeywordRedirectDAO;
import com.bestbuy.search.merchandising.domain.KeywordRedirect;
import com.bestbuy.search.merchandising.domain.SearchProfile;
import com.bestbuy.search.merchandising.domain.Status;
import com.bestbuy.search.merchandising.domain.Users;
import com.bestbuy.search.merchandising.security.UserUtil;
import com.bestbuy.search.merchandising.workflow.GeneralWorkflow;
import com.bestbuy.search.merchandising.workflow.exception.InvalidStatusException;
import com.bestbuy.search.merchandising.workflow.exception.WorkflowException;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.KeywordRedirectWrapper;
import com.bestbuy.search.merchandising.wrapper.PaginationWrapper;

/**
 * Service layer for Keyword Redirects
 * @author chanchal kumari
 * @Modified Kalaiselvi Jaganathan - Added update functionality
 */

public class KeywordRedirectService extends BaseService<Long,KeywordRedirect> implements IKeywordRedirectService {

	@Autowired
	public void setDao(KeywordRedirectDAO dao) {
		this.baseDAO = dao;
	}
	
	@Autowired
	private KeywordRedirectDAO keywordRedirectDAO;
	
	@Autowired
	private IStatusService statusService;
	
	@Autowired
	private UserUtil userUtil;
	
	@Autowired
	private GeneralWorkflow generalWorkflow;
	
	@Autowired
	private ISearchProfileService searchProfileService;
	
	public void setGeneralWorkflow(GeneralWorkflow generalWorkflow) {
		this.generalWorkflow = generalWorkflow;
	}	

	public void setStatusService(IStatusService statusService) {
		this.statusService = statusService;
	}	
	
	/**
	 * Method to load the redirects from the Keyword_Redorect entity
	 * @return List<KeywordRedirectWrapper>
	 * @throws ServiceException
	 * @author Chanchal.Kumari
	 */
	public  List<KeywordRedirectWrapper> loadRedirects(PaginationWrapper paginationWrapper) throws ServiceException{
		
		List<KeywordRedirectWrapper> keywordRedirectWrappers = new ArrayList<KeywordRedirectWrapper>();
		List<KeywordRedirect> keywordRedirects = new ArrayList<KeywordRedirect>();
		SearchCriteria criteria = new SearchCriteria();
		Map<String,Object>	notEqCriteria = criteria.getNotEqCriteria();
		notEqCriteria.put("status.statusId", DELETE_STATUS);
		setColumnValuesForOrderCriteria(paginationWrapper, criteria);
	    setColumnValuesForSearch(paginationWrapper, criteria);
	    criteria.setPaginationWrapper(paginationWrapper);
	    
		try {
			keywordRedirects = keywordRedirectDAO.loadRedirects(criteria);
		} 
		catch(DataAcessException da){
			throw new ServiceException("Error while retriving the keywordRedirects from DB", da);
		}
		
		List<IWrapper> wrappers = KeywordRedirectWrapper.getRedirects(keywordRedirects);
		String currentStatus = "";
			
		for(IWrapper wrapper : wrappers){
				
			try {
					KeywordRedirectWrapper keywordRedirectWrapper = (KeywordRedirectWrapper) wrapper;
					currentStatus = keywordRedirectWrapper.getStatus();
					keywordRedirectWrapper.setActions(KeywordRedirectWrapper.getValidActions(generalWorkflow.getActionsForStatus(currentStatus)));
					keywordRedirectWrappers.add(keywordRedirectWrapper);
				 } 
				
			catch (InvalidStatusException e) {
				throw new ServiceException("Could not retrieve valid actions for the status[" + currentStatus + "]");
			}
		}		 
		return keywordRedirectWrappers;
	}
	
	/**
	 * Loads a redirect into the wrapper to be returned by the REST service
	 * @param redirectId The id of the keyword redirect to be retrieved
	 * @return A List KeywordRedirectWrapper object with the requested information
	 * @throws ServiceException
	 * @author Ramiro.Serrato
	 */
	
	@Override
	public List<KeywordRedirectWrapper> loadRedirect(Long redirectId) throws ServiceException {
		
        KeywordRedirect keywordRedirect = retrieveById(redirectId);
        List<KeywordRedirect> redirects = new ArrayList<KeywordRedirect>();
        redirects.add(keywordRedirect);
        List<KeywordRedirectWrapper> wrappers = new ArrayList<KeywordRedirectWrapper>();
        wrappers.add((KeywordRedirectWrapper) KeywordRedirectWrapper.getRedirects(redirects).get(0));
        
        return wrappers;
	}

	/**
	 * Method to create a new redirect
	 * @param keywordRedirectWrapper
	 * @return IWrapper
	 * @throws ServiceException
	 * @author Chanchal.Kumari
	 */
	
	@Transactional(propagation = Propagation.REQUIRED,
			isolation = Isolation.DEFAULT,
			readOnly = false, 
			rollbackFor = ServiceException.class,
			timeout = -1)
	public IWrapper createKeywordRedirect(KeywordRedirectWrapper keywordRedirectWrapper) throws ServiceException {
		
				KeywordRedirect keywordRedirect = new KeywordRedirect();
				Users user = userUtil.getUser();
				Date currentDate = new Date();
				keywordRedirect.setKeyword(keywordRedirectWrapper.getRedirectTerm());
				keywordRedirect.setRedirectString(keywordRedirectWrapper.getRedirectUrl());
				keywordRedirect.setRedirectType(keywordRedirectWrapper.getRedirectType());
					
				try{
					 SearchProfile searchProfile = searchProfileService.retrieveById(keywordRedirectWrapper.getSearchProfileId());
					 keywordRedirect.setSearchProfile(searchProfile);
				}
					
				catch(ServiceException se){
					throw new ServiceException("Error while getting the keywordRedirect entity from redirectWrapper", se);
				}
					
				keywordRedirect.setStartDate(keywordRedirectWrapper.getStartDate());
				keywordRedirect.setEndDate(keywordRedirectWrapper.getEndDate());
				keywordRedirect.setCreatedDate(currentDate);
				keywordRedirect.setCreatedBy(user);
				keywordRedirect.setUpdatedDate(currentDate);
				keywordRedirect.setUpdatedBy(user);
				Status status = statusService.retrieveById(DRAFT_STATUS);
				keywordRedirect.setStatus(status);
				keywordRedirect = save(keywordRedirect);
				List<KeywordRedirect> KeywordRedirectList = new ArrayList<KeywordRedirect>();
				KeywordRedirectList.add(keywordRedirect);
				keywordRedirectWrapper = (KeywordRedirectWrapper)KeywordRedirectWrapper.getRedirects(KeywordRedirectList).get(0);

				try{
					keywordRedirectWrapper.setActions(KeywordRedirectWrapper.getValidActions(generalWorkflow.getActionsForStatus(keywordRedirect.getStatus().getStatus())));
				}
				
				catch (InvalidStatusException e) {
					throw new ServiceException(e);
				}
				return (IWrapper) keywordRedirectWrapper ;
			}
	
	/**
	 * Update existing keyword redirect 
	 * @param KeywordRedirectWrapper
	 * @return  IWrapper
	 * @throws ServiceException
	 * @author chanchal.Kumari
	 */
	
	@Transactional(propagation = Propagation.REQUIRED,
			isolation = Isolation.DEFAULT,
			readOnly = false, 
			rollbackFor = ServiceException.class,
			timeout = -1)
	public IWrapper updateRedirect(KeywordRedirectWrapper keywordRedirectWrapper) throws ServiceException {
	
				KeywordRedirect keywordRedirect = this.retrieveById(keywordRedirectWrapper.getRedirectId());
				Users user = userUtil.getUser();
				Date currentDate = new Date();
				keywordRedirect.setKeyword(keywordRedirectWrapper.getRedirectTerm());
				keywordRedirect.setRedirectString(keywordRedirectWrapper.getRedirectUrl());
				keywordRedirect.setRedirectType(keywordRedirectWrapper.getRedirectType());
				keywordRedirect.setKeywordId(keywordRedirectWrapper.getRedirectId());
					
				try{
					 SearchProfile searchProfile = searchProfileService.retrieveById(keywordRedirectWrapper.getSearchProfileId());
					 keywordRedirect.setSearchProfile(searchProfile);
				}
					
				catch(ServiceException se){
					throw new ServiceException("Error while getting the keywordRedirect entity from redirectWrapper", se);
				}
					
				keywordRedirect.setStartDate(keywordRedirectWrapper.getStartDate());
				keywordRedirect.setEndDate(keywordRedirectWrapper.getEndDate());
				keywordRedirect.setUpdatedDate(currentDate);
				keywordRedirect.setUpdatedBy(user);
				
				try {
					return updateKeywordRedirectStatus(keywordRedirect, generalWorkflow.stepForward(keywordRedirect.getStatus().getStatus(), EDIT));
				} 
				
				catch (WorkflowException e) {
					throw new ServiceException("Error while performing the workflow step", e);
				}
		}

	/**
	 * Method to perform the delete action.
	 * @param id of the input KeywordRedirect
	 * @return KeywordRedirect the deleted KeywordRedirect entity
	 * @throws ServiceException
	 * @author Chanchal.Kumari
	 */	
	@Transactional(propagation = Propagation.REQUIRED,
			isolation = Isolation.DEFAULT,
			readOnly = false, 
			rollbackFor = ServiceException.class,
			timeout = -1)
	public IWrapper deleteRedirect(Long id) throws ServiceException{
		
		try {
			KeywordRedirect keywordRedirect = this.retrieveById(id);
			return updateKeywordRedirectStatus(keywordRedirect,generalWorkflow.stepForward(keywordRedirect.getStatus().getStatus(), DELETE));
		}
		catch (WorkflowException e) {
			throw new ServiceException("Error while performing the workflow step", e);
		}
	}

	/**
	 * Method to perform the approve action.
	 * @param id of the input KeywordRedirect
	 * @return IWrapper the Iwrapper for the approved KeywordRedirect entity
	 * @throws ServiceException
	 * @author Chanchal.Kumari
	 */	
	@Transactional(propagation = Propagation.REQUIRED,
			isolation = Isolation.DEFAULT,
			readOnly = false, 
			rollbackFor = ServiceException.class,
			timeout = -1)
	public IWrapper approveKeywordRedirect(Long id) throws ServiceException {
		
		try {
				KeywordRedirect keywordRedirect = this.retrieveById(id);
				return updateKeywordRedirectStatus(keywordRedirect,generalWorkflow.stepForward(keywordRedirect.getStatus().getStatus(), APPROVE));
		}
		catch (WorkflowException e) {
				throw new ServiceException("Error while performing the workflow step", e);
		}
	}

	/**
	 * Method to perform the reject action.
	 * @param id of the input KeywordRedirect
	 * @return KeywordRedirect the rejected KeywordRedirect entity
	 * @throws ServiceException
	 * @author Chanchal.Kumari
	 */
	
	@Transactional(propagation = Propagation.REQUIRED,
			isolation = Isolation.DEFAULT,
			readOnly = false, 
			rollbackFor = ServiceException.class,
			timeout = -1)
	public IWrapper rejectKeywordRedirect(Long id) throws ServiceException {
		
		try {
				KeywordRedirect keywordRedirect = this.retrieveById(id);
				return updateKeywordRedirectStatus(keywordRedirect,generalWorkflow.stepForward(keywordRedirect.getStatus().getStatus(), REJECT));
		}
		catch (WorkflowException e) {
				throw new ServiceException("Error while performing the workflow step", e);
		}
	}

	/**
	 * Method to perform the publish action.
	 * @param id of the input KeywordRedirect
	 * @return KeywordRedirect the published KeywordRedirect entity
	 * @throws ServiceException
	 * @author Chanchal.Kumari
	 */
	
	@Transactional(propagation = Propagation.REQUIRED,
			isolation = Isolation.DEFAULT, 
			readOnly = false, 
			rollbackFor = ServiceException.class, 
			timeout = -1)
	public void publishKeywordRedirect(Long id) throws ServiceException {
		
		try {
				KeywordRedirect keywordRedirect = this.retrieveById(id);
				String statusString = generalWorkflow.stepForward(keywordRedirect.getStatus().getStatus(), PUBLISH);
				Long statusId = statusService.getStatusId(statusString);
				Status status = statusService.retrieveById(statusId);keywordRedirect.setStatus(status);
				keywordRedirect.setUpdatedDate(new Date());
				keywordRedirect = this.update(keywordRedirect);
		}
		catch (WorkflowException e) {
				throw new ServiceException("Error while performing the workflow step", e);
		}
	}
	
	/**
	 * Method to update status of the input redirect with the input status name.
	 * @param redirect the input KeywordRedirect entity and statusString the status name.
	 * @return IWrapper the Iwrapper for the updated KeywordRedirect entity
	 * @throws ServiceException
	 * @author Chanchal.Kumari
	 */
	
	public IWrapper updateKeywordRedirectStatus(KeywordRedirect keywordRedirect , String statusString) throws ServiceException {
		
		Long statusId = statusService.getStatusId(statusString);
		Status status = statusService.retrieveById(statusId);
		keywordRedirect.setStatus(status);
		keywordRedirect.setUpdatedDate(new Date());
		keywordRedirect.setUpdatedBy(userUtil.getUser());
		keywordRedirect = this.update(keywordRedirect);
		List<KeywordRedirect> KeywordRedirectList = new ArrayList<KeywordRedirect>();
		KeywordRedirectList.add(keywordRedirect);
		KeywordRedirectWrapper keywordRedirectWrapper = (KeywordRedirectWrapper)KeywordRedirectWrapper.getRedirects(KeywordRedirectList).get(0);

		try{
			keywordRedirectWrapper.setActions(KeywordRedirectWrapper.getValidActions(generalWorkflow.getActionsForStatus(keywordRedirectWrapper.getStatus())));
		}
		
		catch (InvalidStatusException e) {
			throw new ServiceException(e);
		}
		
		return (IWrapper) keywordRedirectWrapper ;
	}
	
	
	/**
	 * 
	 */
	@Transactional(propagation = Propagation.REQUIRED,
			isolation = Isolation.DEFAULT, 
			readOnly = false, 
			rollbackFor = ServiceException.class, 
			timeout = -1)
	public void updateRedirects(List<KeywordRedirect> redirects) throws ServiceException{
		try {
			this.update(redirects);
		 } 
		catch (DataAccessException e) {
			throw new ServiceException("Error while performing the workflow step", e);
		}
	}
 }
