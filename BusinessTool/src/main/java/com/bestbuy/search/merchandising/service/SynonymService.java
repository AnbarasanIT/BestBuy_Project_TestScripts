package com.bestbuy.search.merchandising.service;

import static com.bestbuy.search.merchandising.common.MerchandisingConstants.DELETE_STATUS;
import static com.bestbuy.search.merchandising.common.MerchandisingConstants.DRAFT_STATUS;
import static com.bestbuy.search.merchandising.common.MerchandisingConstants.SYNONYM_TYPE_ONEWAY;
import static com.bestbuy.search.merchandising.common.MerchandisingConstants.SYNONYM_TYPE_TWOWAY;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.APPROVE;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.DELETE;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.EDIT;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.PUBLISH;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.REJECT;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.SynonymGroupDAO;
import com.bestbuy.search.merchandising.domain.Status;
import com.bestbuy.search.merchandising.domain.Synonym;
import com.bestbuy.search.merchandising.domain.SynonymTerm;
import com.bestbuy.search.merchandising.domain.SynonymTermPK;
import com.bestbuy.search.merchandising.domain.SynonymType;
import com.bestbuy.search.merchandising.domain.Users;
import com.bestbuy.search.merchandising.security.UserUtil;
import com.bestbuy.search.merchandising.workflow.GeneralWorkflow;
import com.bestbuy.search.merchandising.workflow.exception.InvalidStatusException;
import com.bestbuy.search.merchandising.workflow.exception.WorkflowException;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.PaginationWrapper;
import com.bestbuy.search.merchandising.wrapper.PromoWrapper;
import com.bestbuy.search.merchandising.wrapper.SynonymWrapper;

/**
 * @author Lakshmi Valluripalli
 * Modified By : Kalaiselvi.Jaganathan 29-Aug-2012 Added createSynonym and prepareSynonymGroup methods
 * Modified By : Kalaiselvi.Jaganathan 30-Aug-2012 Added prepareSynonymTerms and saveAssets methods
 * Modified By : Chanchal KUmari Date:30-Aug-2012 Added update and updateAssets methods for update
 * Modified By : Chanchal Kumari Date:30-Aug-2012 updated prepareSynonymTerms and prepareSynonymGroup methods for update
 * Modified By : Kalaiselvi.Jaganathan 31-Aug-2012 Modified saveAssets to use WebConstants for assetType and status
 * Modified By : Kalaiselvi.Jaganathan 17-Aug-2012 Added loadSynonymsGroups
 */
public class SynonymService extends BaseService<Long,Synonym> implements ISynonymService{

	private final static Logger logger = Logger.getLogger(SynonymService.class);
	
	@Autowired
	private ISynonymTypeService synonymTypeService;

	@Autowired
	private IStatusService statusService;

	@Autowired
	private UserUtil userUtil;
	
	@Autowired
	private GeneralWorkflow generalWorkflow;	
	
	public void setGeneralWorkflow(GeneralWorkflow generalWorkflow) {
		this.generalWorkflow = generalWorkflow;
	}

	@Autowired
	public void setDao(SynonymGroupDAO dao) {
		this.baseDAO = dao;
	}
	
	public void setSynonymTypeService(SynonymTypeService synonymTypeService) {
		this.synonymTypeService = synonymTypeService;
	}

	public void setStatusService(StatusService statusService) {
		this.statusService = statusService;
	}
	
	public void setUserUtil(UserUtil userUtil) {
		this.userUtil = userUtil;
	}

	/**
	 * Create new Synonym
	 * @param SynonymWrapper, boolean
	 * @return JsonResponse
	 */
	@Transactional(propagation = Propagation.REQUIRED, 
			isolation = Isolation.DEFAULT, 
			readOnly = false, 
			rollbackFor = ServiceException.class, 
			timeout = -1)
	public IWrapper createSynonym(SynonymWrapper synonymWrapper) throws ServiceException{
				Users user = userUtil.getUser();
				Date currentDate = new Date();
				Synonym synonym = new Synonym();
				synonym.setCreatedDate(currentDate);
				synonym.setCreatedBy(user);
				setSynonym(synonymWrapper, synonym,null);
				synonym = this.save(synonym);
				List<Synonym> synonymList = new ArrayList<Synonym>();
				synonymList.add(synonym);
				synonymWrapper = (SynonymWrapper)SynonymWrapper.getSynonyms(synonymList).get(0);

				try{
					synonymWrapper.setActions(PromoWrapper.getValidActions(generalWorkflow.getActionsForStatus(synonymWrapper.getStatus())));
				}
				
				catch (InvalidStatusException e) {
					throw new ServiceException(e);
				}
				
				return (IWrapper) synonymWrapper;
			}

	/**
	 * update Synonym group entity
	 * @param SynonymWrapper, boolean
	 */
	private void setSynonym(SynonymWrapper synonymWrapper,Synonym synonym, String status) throws ServiceException{

		if(synonymWrapper.getPrimaryTerm() != null)	{
			synonym.setPrimaryTerm(synonymWrapper.getPrimaryTerm());
		}
		
		if(synonymWrapper.getDirectionality() != null) {
			synonym.setDirectionality(synonymWrapper.getDirectionality());
		}
		
		if(synonymWrapper.getExactMatch() != null) {
			synonym.setExactMatch(synonymWrapper.getExactMatch());
		}
		
		SynonymType type =synonymTypeService.retrieveById(synonymWrapper.getListId());
		if(type != null){
			synonym.setSynListId(type);
		}
		
		this.setSynonymTerms(synonymWrapper, synonym);
		if(synonym.getStatus() !=null){
			this.updateStatusForSynonym(synonym,status);
		}else{
			this.updateStatusForSynonym(synonym, null);
		}
		
		setAuditInfo(synonym);

	}

	/**
	 * @param synonymWrapper
	 * @param persistedSynonym
	 * @author Chanchal.Kumari
	 */
	private void setSynonymTerms(SynonymWrapper synonymWrapper,Synonym synonym)  throws ServiceException{
		
		List<SynonymTerm> synonymGroupTerms = new ArrayList<SynonymTerm>();
		
		SynonymTerm groupTerm = null;
		SynonymTermPK groupTermPK = null;
		
		   for (Iterator iterator = synonym.getSynonymGroupTerms().iterator(); iterator
					.hasNext();) {
				SynonymTerm synonymTerm = (SynonymTerm) iterator.next();
				if(!synonymWrapper.getTerm().contains(synonymTerm.getSynonymTerms().getSynTerm())){
					iterator.remove();
				}else{
					synonymWrapper.getTerm().remove(synonymTerm.getSynonymTerms().getSynTerm());
				}
			}  
			synonymGroupTerms = synonym.getSynonymGroupTerms();
			for(String term : synonymWrapper.getTerm()){
				groupTerm=new SynonymTerm();
				groupTermPK = new SynonymTermPK();
				groupTermPK.setSynonym(synonym);
				groupTermPK.setSynTerm(term);
				groupTerm.setSynonymTerms(groupTermPK);
				synonymGroupTerms.add(groupTerm);
			}
			synonym.setSynonymGroupTerms(synonymGroupTerms);

	}

	/**
	 * Load Synonym List and Type for Create Synonym
	 * @param SynonymWrapper, boolean
	 * @return JsonResponse
	 */
	public String loadSynonym() throws ServiceException,JSONException{
		List<JSONObject> data = new ArrayList<JSONObject>();
		JSONObject jsonTypeObject = new JSONObject();

		List<SynonymType> synonymGroupsType= (List<SynonymType>)synonymTypeService.retrieveAll();
		if(synonymGroupsType!= null && synonymGroupsType.size() > 0)
		{
			for(SynonymType synonymGroupsTypes:synonymGroupsType)
			{
				JSONObject jsonListObject = new JSONObject();
				jsonListObject.put("syn_list_id", synonymGroupsTypes.getSynonymListId());
				jsonListObject.put("syn_list_type",synonymGroupsTypes.getSynonymListType());
				data.add(jsonListObject);
			}
			jsonTypeObject.put("Syn_type_one", SYNONYM_TYPE_ONEWAY);
			jsonTypeObject.put("Syn_type_two", SYNONYM_TYPE_TWOWAY);
			data.add(jsonTypeObject);
		}
		else
		{
			logger.info("Synonym list table is empty");
		}
		
		return data.toString();
	}

	/**
	 * Updates the SynonymGroup
	 * @param synonymGroup
	 * @return String
	 */
	@Transactional(propagation = Propagation.REQUIRED, 
			isolation = Isolation.DEFAULT, 
			readOnly = false, 
			rollbackFor = ServiceException.class, 
			timeout = -1)
	public IWrapper update(SynonymWrapper synonymWrapper) throws ServiceException{
			try{
				Synonym synonym = this.retrieveById(synonymWrapper.getSynonymId());
				this.setSynonym(synonymWrapper,synonym,generalWorkflow.stepForward(synonymWrapper.getStatus(), EDIT));
				synonym = this.update(synonym);
				return getIwrapper(synonym);
			}
			catch (WorkflowException e) {
				throw new ServiceException("Error while performing the workflow step", e);
			}
		}

	@Override
	public SynonymWrapper getSynonym(Long id) throws ServiceException{
		SynonymWrapper synonymWrapper= new SynonymWrapper();
		Synonym synonym = retrieveById(id);
		
		if(synonym != null){
			
			synonymWrapper.setSynonymId(synonym.getId());
			synonymWrapper.setPrimaryTerm(synonym.getPrimaryTerm());
            List<String> terms = new ArrayList<String>();
			
			for(SynonymTerm term : synonym.getSynonymGroupTerms()){
				terms.add(term.getSynonymTerms().getSynTerm());
		    }
			
			synonymWrapper.setTerm(terms);
			Users user = synonym.getUpdatedBy();
			
			if(user != null){
				String fullName = user.getFirstName()+"."+user.getLastName();
				synonymWrapper.setModifiedBy(fullName);
			}
			
			synonymWrapper.setStatusId(synonym.getStatus().getStatusId());
			synonymWrapper.setStatus(synonym.getStatus().getStatus());
			synonymWrapper.setSynonymListType(synonym.getSynListId().getSynonymListType());
			synonymWrapper.setDirectionality(synonym.getDirectionality());
			synonymWrapper.setExactMatch(synonym.getExactMatch());
			synonymWrapper.setModifiedDate(synonym.getUpdatedDate());
			synonymWrapper.setStartDate(synonym.getStartDate());
			synonymWrapper.setDirectionality(synonym.getDirectionality());
			synonymWrapper.setExactMatch(synonym.getExactMatch());
			synonymWrapper.setListId(synonym.getSynListId().getSynonymListId());
			synonymWrapper.setSynonymListType(synonym.getSynListId().getSynonymListType());
			
		}
		
		return synonymWrapper;
	}

    /** 
     * Method to load the status from the table 
     * @throws ServiceException 
     */ 
    public List<Status> loadStatuses() throws ServiceException{ 
         List<Status> statusList = (List<Status>)statusService.retrieveAll(); 
         return statusList; 
    }
        
	@Transactional(propagation = Propagation.REQUIRED, 
			isolation = Isolation.DEFAULT, 
			readOnly = false, 
			rollbackFor = ServiceException.class, 
			timeout = -1)
    public IWrapper deleteSynonym(Long id) throws ServiceException {
		try {
			Synonym synonym = this.retrieveById(id);
			updateStatusForSynonym(synonym, generalWorkflow.stepForward(synonym.getStatus().getStatus(), DELETE));
			setAuditInfo(synonym);
			synonym = this.update(synonym);
			return getIwrapper(synonym);
		} 
		
		catch (WorkflowException e) {
			throw new ServiceException("Error while performing the workflow step", e);
		}
    }
	
	/**
	 * Method to load the Synonyms
	 * @throws ServiceException
	 */
	public  List<Synonym> getAllSynonyms(PaginationWrapper paginationWrapper) throws ServiceException{
		List<Synonym> synonyms = new ArrayList<Synonym>();
		SearchCriteria criteria = new SearchCriteria();
		Map<String,Object>	notEqCriteria = criteria.getNotEqCriteria();
		notEqCriteria.put("status.statusId", DELETE_STATUS);
		setColumnValuesForOrderCriteria(paginationWrapper, criteria);
        setColumnValuesForSearch(paginationWrapper, criteria);
        criteria.setPaginationWrapper(paginationWrapper);
        
		try {
			synonyms = this.loadEntitiesWithCriteria(criteria);
		} 
		
		catch (DataAcessException dae) {
			throw new ServiceException("Error while retriving the synonyms from DB", dae);
		} 

		return synonyms;
	}
	
	/**
	 * Approves a synonym with the given id
	 * @param id A Long with the SynonymGroup id of the synonym that we want to approve
	 * @throws ServiceException
	 * @author Ramiro.Serrato
	 */
	@Transactional(propagation = Propagation.REQUIRED, 
			isolation = Isolation.DEFAULT, 
			readOnly = false, 
			rollbackFor = ServiceException.class, 
			timeout = -1)
	public IWrapper approveSynonym(Long id) throws ServiceException {
		try {
			Synonym synonym = this.retrieveById(id);
			updateStatusForSynonym(synonym, generalWorkflow.stepForward(synonym.getStatus().getStatus(), APPROVE));
			setAuditInfo(synonym);
			synonym = this.update(synonym);
			return getIwrapper(synonym);
		} 
		
		catch (WorkflowException e) {
			throw new ServiceException("Error while performing the workflow step", e);
		}
	}
	
	/**
	 * Rejects a synonym with the given id
	 * @param id A Long with the SynonymGroup id of the synonym that we want to reject
	 * @throws ServiceException
	 * @author Ramiro.Serrato
	 */
	@Transactional(propagation = Propagation.REQUIRED, 
			isolation = Isolation.DEFAULT, 
			readOnly = false, 
			rollbackFor = ServiceException.class, 
			timeout = -1)
	public IWrapper rejectSynonym(Long id) throws ServiceException {
		try {
			Synonym synonym = this.retrieveById(id);
			updateStatusForSynonym(synonym, generalWorkflow.stepForward(synonym.getStatus().getStatus(), REJECT));
			setAuditInfo(synonym);
			synonym = this.update(synonym);
			return getIwrapper(synonym);
		} 
		
		catch (WorkflowException e) {
			throw new ServiceException("Error while performing the workflow step", e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.bestbuy.search.merchandising.service.ISynonymGroupService#listSynonyms()
	 */
	@Override
	public List<SynonymWrapper> listSynonyms(PaginationWrapper paginationWrapper) throws ServiceException {
		List<Synonym> synonymGroups = getAllSynonyms(paginationWrapper);
		@SuppressWarnings("unchecked")
		List<SynonymWrapper> wrappers = (List<SynonymWrapper>) (List<?>) SynonymWrapper.getSynonyms(synonymGroups);
		String currentStatus = "";
		
		// adding the actions to the wrapper for displaying in the actions dropdown
		for(SynonymWrapper synonymWrapper : wrappers){
			try {
				currentStatus = synonymWrapper.getStatus();
				synonymWrapper.setActions(SynonymWrapper.getValidActions(generalWorkflow.getActionsForStatus(currentStatus)));
			} 
			
			catch (InvalidStatusException e) {
				throw new ServiceException("Could not retrieve valid actions for the status[" + currentStatus + "]");
			}
		}
		
		return wrappers;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, 
			isolation = Isolation.DEFAULT, 
			readOnly = false, 
			rollbackFor = ServiceException.class, 
			timeout = -1)
	public void publishSynonym(Long id, String status) throws ServiceException {
		
		try {
			Synonym synonym = this.retrieveById(id);
			updateStatusForSynonym(synonym, generalWorkflow.stepForward(status, PUBLISH));
			synonym.setUpdatedDate(new Date());
			synonym = this.update(synonym);
		}  
		catch (WorkflowException e) {
			throw new ServiceException("Error while performing the workflow step", e);
		}
	}

	private void updateStatusForSynonym(Synonym synonym, String synonymStatus) throws ServiceException {
		try {
			Long statusId;
			if (null != synonymStatus) {
			 statusId= statusService.getStatusId(synonymStatus);
		   }else{
				 statusId = DRAFT_STATUS;
		   }
		   Status status = statusService.retrieveById(statusId);
		   synonym.setStatus(status);
		 } 
		catch (ServiceException e) {
			throw new ServiceException("Error while changing the status for the synonym", e);
		} 
	}
	
	private  void setAuditInfo(Synonym synonym){
		   synonym.setUpdatedDate(new Date());
		   synonym.setUpdatedBy(userUtil.getUser());
	}
	
	private IWrapper getIwrapper(Synonym synonym) throws ServiceException{
		
		List<Synonym> synonymList = new ArrayList<Synonym>();
		synonymList.add(synonym);
		SynonymWrapper synonymWrapper = (SynonymWrapper)SynonymWrapper.getSynonyms(synonymList).get(0);

		try{
			synonymWrapper.setActions(PromoWrapper.getValidActions(generalWorkflow.getActionsForStatus(synonymWrapper.getStatus())));
		}
		catch (InvalidStatusException e) {
			throw new ServiceException(e);
		}
		return (IWrapper) synonymWrapper;
		
	}
	
}