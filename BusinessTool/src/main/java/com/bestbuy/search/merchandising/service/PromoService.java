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
import com.bestbuy.search.merchandising.dao.PromoDAO;
import com.bestbuy.search.merchandising.domain.Promo;
import com.bestbuy.search.merchandising.domain.PromoSku;
import com.bestbuy.search.merchandising.domain.Status;
import com.bestbuy.search.merchandising.domain.Users;
import com.bestbuy.search.merchandising.security.UserUtil;
import com.bestbuy.search.merchandising.workflow.GeneralWorkflow;
import com.bestbuy.search.merchandising.workflow.exception.InvalidStatusException;
import com.bestbuy.search.merchandising.workflow.exception.WorkflowException;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.PaginationWrapper;
import com.bestbuy.search.merchandising.wrapper.PromoWrapper;

/**
 * Service layer for Promos
 * @author Chanchal.kumari
 * Date - 4th Oct 2012
 */

public class PromoService extends BaseService<Long,Promo> implements IPromoService{

	@Autowired
	public void setDao(PromoDAO dao) {
		this.baseDAO = dao;
	}

	@Autowired
	private PromoDAO promoDAO;

	@Autowired
	private GeneralWorkflow generalWorkflow;

	@Autowired
	private IStatusService statusService;
	
	@Autowired
	private IUsersService usersService;
	
	@Autowired
	private UserUtil userUtil;
	
	@Autowired
	private IPromoSkuService promoSkuService;

	public void setPromoDAO(PromoDAO promoDAO) {
		this.promoDAO = promoDAO;
	}
	
	public IPromoSkuService getPromoSkuService() {
		return promoSkuService;
	}
	
	public void setPromoSkuService(IPromoSkuService promoSkuService) {
		this.promoSkuService = promoSkuService;
	}
	
	public void setStatusService(IStatusService statusService) {
		this.statusService = statusService;
	}
	
	public void setGeneralWorkflow(GeneralWorkflow generalWorkflow) {
		this.generalWorkflow = generalWorkflow;
	}

	public void setUsersService(IUsersService usersService) {
		this.usersService = usersService;
	}

	public void setUserUtil(UserUtil userUtil) {
		this.userUtil = userUtil;
	}

	/**
	 * Method to retrieve the list of Promo which are not in the deleted status from DB
	 * @throws ServiceException
	 */
	public  List<IWrapper> loadPromos(PaginationWrapper paginationWrapper) throws ServiceException{
		List<IWrapper> wrappers = null;
		SearchCriteria criteria = new SearchCriteria();
		Map<String,Object>	notEqCriteria = criteria.getNotEqCriteria();
		notEqCriteria.put("status.statusId", DELETE_STATUS);
		setColumnValuesForOrderCriteria(paginationWrapper, criteria);
    setColumnValuesForSearch(paginationWrapper, criteria);

    criteria.setPaginationWrapper(paginationWrapper);
		
		try{
			List<Promo> promos = promoDAO.loadPromos(criteria);
			wrappers = PromoWrapper.getPromos(promos);
		}

		catch(DataAcessException da){
			throw new ServiceException("Error while retriving the promos from DB", da);
		}

		return wrappers;
	}

	/**
	 * Method to create a new promo
	 * @param promoWrapper
	 * @return IWrapper
	 * @throws ServiceException
	 * @author Chanchal.Kumari
	 */
	
	@Transactional(propagation = Propagation.REQUIRED,
			isolation = Isolation.DEFAULT,
			readOnly = false, 
			rollbackFor = ServiceException.class,
			timeout = -1)
	public IWrapper createPromo(PromoWrapper promoWrapper) throws ServiceException {
			Promo promo = new Promo();
			PromoSku promoSku = null;
			Users user = userUtil.getUser();
			promo.setPromoName(promoWrapper.getPromoName());
			Status status = statusService.retrieveById(DRAFT_STATUS);
			promo.setStatus(status);
			promo.setStartDate(promoWrapper.getStartDate());
			
			if(promoWrapper.getEndDate() != null){
				promo.setEndDate(promoWrapper.getEndDate());
			}
			promo.setCreatedDate(new Date());
			promo.setCreatedBy(user);
			promo.setUpdatedDate(new Date());
			promo.setUpdatedBy(user);
			ArrayList<PromoSku> promoSkus = new ArrayList<PromoSku>();
			
			for(String skuId : promoWrapper.getSkuIds()){
				promoSku = new PromoSku();
				promoSku.setPromo(promo);
				promoSku.setSkuId(skuId);
				promoSkus.add(promoSku);
			}
			
			promo.setPromoSku(promoSkus);
			promo = this.save(promo);
			List<Promo> promoList = new ArrayList<Promo>();
			promoList.add(promo);
			promoWrapper = (PromoWrapper)PromoWrapper.getPromos(promoList).get(0);

			try{
				promoWrapper.setActions(PromoWrapper.getValidActions(generalWorkflow.getActionsForStatus(promo.getStatus().getStatus())));
			}
			
			catch (InvalidStatusException e) {
				throw new ServiceException(e);
			}

			return (IWrapper) promoWrapper ;
		}

	/**
	 * performs the approve action on promo
	 * @param id The promoId for this Promo
	 * @return IWrapper
	 * @throws ServiceException If any error occurs
	 * @author Chanchal.Kumari
	 */

	@Transactional(propagation = Propagation.REQUIRED, 
			isolation = Isolation.DEFAULT,
			readOnly = false,
			rollbackFor = ServiceException.class,
			timeout = -1)
	public IWrapper deletePromo(Long id) throws ServiceException {
		try {
			Promo promo = this.retrieveById(id);
			return updatePromoStatus(promo,generalWorkflow.stepForward(promo.getStatus().getStatus(), DELETE));
		} 
		
		catch (WorkflowException e) {
			throw new ServiceException("Error while performing the workflow step", e);
		}
	}

	/**
	 * performs the approve action on promo
	 * @param id The promoId for this Promo
	 * @return IWrapper
	 * @throws ServiceException If any error occurs
	 * @author Chanchal.Kumari
	 */
	
	@Transactional(propagation = Propagation.REQUIRED,
			isolation = Isolation.DEFAULT, 
			readOnly = false, 
			rollbackFor = ServiceException.class, 
			timeout = -1)
	public IWrapper approvePromo(Long id) throws ServiceException {
		try {
			Promo promo = this.retrieveById(id);
			return updatePromoStatus(promo,generalWorkflow.stepForward(promo.getStatus().getStatus(), APPROVE));
		} 
		
		catch (WorkflowException e) {
			throw new ServiceException("Error while performing the workflow step", e);
		}
	}

	/**
	 * performs the reject action on promo
	 * @param id The promoId for this Promo
	 * @return IWrapper
	 * @throws ServiceException If any error occurs
	 * @author Chanchal.Kumari
	 */
	
	@Transactional(propagation = Propagation.REQUIRED, 
			isolation = Isolation.DEFAULT, 
			readOnly = false, 
			rollbackFor = ServiceException.class, 
			timeout = -1)
	public IWrapper rejectPromo(Long id) throws ServiceException {
		try {
			Promo promo = this.retrieveById(id);
			return updatePromoStatus(promo,generalWorkflow.stepForward(promo.getStatus().getStatus(), REJECT));
		} 
		
		catch (WorkflowException e) {
			throw new ServiceException("Error while performing the workflow step", e);
		}
	}

	public IWrapper updatePromoStatus(Promo promo , String statusString) throws ServiceException {
		
		Long statusId = statusService.getStatusId(statusString);// status must be changed
		Status status = statusService.retrieveById(statusId);
		promo.setStatus(status);
		promo.setUpdatedDate(new Date());
		promo.setUpdatedBy(userUtil.getUser());
		promo = this.update(promo);
		List<Promo> promoList = new ArrayList<Promo>();
		promoList.add(promo);
		PromoWrapper promoWrapper = (PromoWrapper)PromoWrapper.getPromos(promoList).get(0);

		try{
			promoWrapper.setActions(PromoWrapper.getValidActions(generalWorkflow.getActionsForStatus(promoWrapper.getStatus())));
		}
		
		catch (InvalidStatusException e) {
			throw new ServiceException(e);
		}
		
		return (IWrapper) promoWrapper ;
	}
	
	/**
	 * Method to update an existing promo
	 * @param promoWrapper
	 * @return IWrapper
	 * @throws ServiceException
	 * @author Chanchal.Kumari
	 * Date - 11th Oct 2012
	 */
	@Transactional(propagation = Propagation.REQUIRED,
			isolation = Isolation.DEFAULT,
			readOnly = false, 
			rollbackFor = ServiceException.class,
			timeout = -1)
	public IWrapper updatePromo(PromoWrapper promoWrapper) throws ServiceException {
		
		try{
				Promo promo = this.retrieveById(promoWrapper.getPromoId());
				PromoSku promoSku = null;
		
				promo.setPromoId(promoWrapper.getPromoId());
				promo.setPromoName(promoWrapper.getPromoName());
				String statusString = generalWorkflow.stepForward(promo.getStatus().getStatus(), EDIT);
				Long statusId = statusService.getStatusId(statusString);
				Status status = statusService.retrieveById(statusId);
				promo.setStatus(status);
				promo.setStartDate(promoWrapper.getStartDate());
				
				if(promoWrapper.getEndDate() != null){
					promo.setEndDate(promoWrapper.getEndDate());
				}
				
				promo.setUpdatedDate(new Date());
				promo.setUpdatedBy(userUtil.getUser());
				List<PromoSku> promoSkus =  promo.getPromoSku();
				promoSkus.clear();
				
				for(String skuId : promoWrapper.getSkuIds()){
					promoSku = new PromoSku();
					promoSku.setPromo(promo);
					promoSku.setSkuId(skuId);
					promoSkus.add(promoSku);
				}
				
				promo.setPromoSku(promoSkus);
				promo = this.update(promo);
				List<Promo> promoList = new ArrayList<Promo>();
				promoList.add(promo);
				promoWrapper = (PromoWrapper)PromoWrapper.getPromos(promoList).get(0);
				promoWrapper.setActions(PromoWrapper.getValidActions(generalWorkflow.getActionsForStatus(promoWrapper.getStatus())));
			}
			catch (InvalidStatusException e) {
				throw new ServiceException(e);
			}catch(WorkflowException wfe){
				throw new ServiceException(wfe);
			}
			return (IWrapper) promoWrapper;
		}

	/**
	 * Method to return promoWrapper for the given promo id
	 * @param id
	 * @return PromoWrapper
	 * @throws ServiceException
	 * @author Chanchal.Kumari
	 * Date - 11th Oct 2012
	 */
	public PromoWrapper getPromo(Long id) throws ServiceException{
		PromoWrapper promoWrapper= new PromoWrapper();
		Promo promo = retrieveById(id);
		Users user= null;
		Status status = null;
		
		if(promo != null){
			
			promoWrapper.setPromoId(id);
			promoWrapper.setPromoName(promo.getPromoName());
			ArrayList<String> skuIds = new ArrayList<String>();
			
			for(PromoSku promoSku : promo.getPromoSku()){
				skuIds.add(promoSku.getSkuId());
		    }
			
			promoWrapper.setSkuIds(skuIds);
			promoWrapper.setModifiedDate(promo.getUpdatedDate());
			user = promo.getUpdatedBy();
			
			if(user != null){
				promoWrapper.setModifiedBy(user.getUserId().toString());
			}
			
			promoWrapper.setCreatedDate(promo.getCreatedDate());
			user = promo.getCreatedBy();
			
			if(user != null){
				promoWrapper.setCreatedBy(user.getFirstName() + "." + user.getLastName());
			}
			
			promoWrapper.setStartDate(promo.getStartDate());
			promoWrapper.setEndDate(promo.getEndDate());
			status = promo.getStatus();
			
			if(status != null){
				promoWrapper.setStatus(status.getStatus());
				promoWrapper.setStatusId(status.getStatusId());
		    }

		}

		return promoWrapper;
	}
	
	/**
	 * performs the publish action on promo
	 * @param id The promoId for this Promo
	 * @return IWrapper
	 * @throws ServiceException If any error occurs
	 * @author Chanchal.Kumari
	 */
	
	@Transactional(propagation = Propagation.REQUIRED, 
			isolation = Isolation.DEFAULT, 
			readOnly = false, 
			rollbackFor = ServiceException.class, 
			timeout = -1)
	public void publishPromo(Long id,String promoStatus) throws ServiceException{
		try {
			 Promo promo = this.retrieveById(id);
			 String statusString = generalWorkflow.stepForward(promo.getStatus().getStatus(), PUBLISH);
			 Long statusId = statusService.getStatusId(statusString);// status must be changed
			 Status status = statusService.retrieveById(statusId);
			 promo.setStatus(status);
			 promo.setUpdatedDate(new Date());
			 promo = this.update(promo);
		 } 
		
		catch (WorkflowException e) {
			throw new ServiceException("Error while performing the workflow step", e);
		}
	}	
	
	
	/**
	 * 
	 */
	@Transactional(propagation = Propagation.REQUIRED, 
			isolation = Isolation.DEFAULT, 
			readOnly = false, 
			rollbackFor = ServiceException.class, 
			timeout = -1)
	public void updatePromos(List<Promo> promos) throws ServiceException{
		try {
			this.update(promos);
		 } 
		catch (DataAccessException e) {
			throw new ServiceException("Error while performing the workflow step", e);
		}
	}
}
