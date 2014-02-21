package com.bestbuy.search.merchandising.service;

import static com.bestbuy.search.merchandising.common.MerchandisingConstants.BANNER_TEMPLATE_3HEADER;
import static com.bestbuy.search.merchandising.common.MerchandisingConstants.BANNER_TEMPLATE_HTML;
import static com.bestbuy.search.merchandising.common.MerchandisingConstants.DELETE_STATUS;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.APPROVE;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.DELETE;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.EDIT;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.INVALID;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.PUBLISH;
import static com.bestbuy.search.merchandising.workflow.enumeration.GeneralAction.REJECT;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bestbuy.search.merchandising.common.BannerWrapperConverter;
import com.bestbuy.search.merchandising.common.MerchandisingConstants;
import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.dao.BannerDAO;
import com.bestbuy.search.merchandising.domain.Banner;
import com.bestbuy.search.merchandising.domain.BannerTemplate;
import com.bestbuy.search.merchandising.domain.Context;
import com.bestbuy.search.merchandising.domain.Status;
import com.bestbuy.search.merchandising.domain.Users;
import com.bestbuy.search.merchandising.domain.common.BannerTemplateEnum;
import com.bestbuy.search.merchandising.security.UserUtil;
import com.bestbuy.search.merchandising.workflow.GeneralWorkflow;
import com.bestbuy.search.merchandising.workflow.exception.InvalidStatusException;
import com.bestbuy.search.merchandising.workflow.exception.WorkflowException;
import com.bestbuy.search.merchandising.wrapper.BannerTemplateWrapper;
import com.bestbuy.search.merchandising.wrapper.BannerWrapper;
import com.bestbuy.search.merchandising.wrapper.ContextWrapper;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.PaginationWrapper;

/**Service layer for Banners
 * 
 * @author Chanchal.kumari
 * Modified By Kalaiselvi Jaganathan - Added Create and update functionality for Banners
 */
public class BannerService extends BaseService<Long,Banner> implements IBannerService{
	@Autowired
	private IStatusService statusService;

	@Autowired
	private GeneralWorkflow generalWorkflow;

	@Autowired
	private BannerDAO bannerDAO;

	@Autowired
	private BannerWrapperConverter bannerWrapperConverter;

	@Autowired
	private IContextService contextService;

	@Autowired
	private UserUtil userUtil;
	
	/**
	 * Method to set dao
	 * 
	 * @param dao
	 */
	@Autowired
	public void setDao(BannerDAO dao) {
		this.baseDAO = dao;
	}

	/**
	 * Method to set status service
	 * 
	 * @param IStatusService statusService
	 */
	public void setStatusService(IStatusService statusService) {
		this.statusService = statusService;
	}

	/**
	 * Method to set banner dao
	 * 
	 * @param BannerDAO bannerDAO
	 */
	public void setBannerDAO(BannerDAO bannerDAO) {
		this.bannerDAO = bannerDAO;
	}

	/**
	 * Method to set banner wrapper converter
	 * 
	 * @param BannerWrapperConverter bannerWrapperConverter
	 */
	public void setBannerWrapperConverter(
			BannerWrapperConverter bannerWrapperConverter) {
		this.bannerWrapperConverter = bannerWrapperConverter;
	}

	/**
	 * Method to set context service
	 * 
	 * @param IContextService contextService
	 */
	public void setContextService(IContextService contextService) {
		this.contextService = contextService;
	}

	/**
	 * Method to load the banners from the Banner entity
	 * 
	 * @param PaginationWrapper paginationWrapper
	 * @return List<Banner>
	 * @throws ServiceException
	 */
	public List<Banner> loadBanners(PaginationWrapper paginationWrapper) throws ServiceException {
		List<Banner> banners = new ArrayList<Banner>();
		SearchCriteria criteria = new SearchCriteria();
		Map<String,Object>	notEqCriteria = criteria.getNotEqCriteria();
		notEqCriteria.put("status.statusId", DELETE_STATUS);
		setColumnValuesForOrderCriteria(paginationWrapper, criteria);
		setColumnValuesForSearch(paginationWrapper, criteria);
		criteria.setPaginationWrapper(paginationWrapper);

		try {
			banners = this.loadEntitiesWithCriteria(criteria);
		} catch(DataAcessException da) {
			throw new ServiceException("Error while retriving the banners from DB", da);
		}

		return banners;
	}

	/**
	 * Delete the banner from DataBase
	 * 
	 * @param Long bannerId
	 * @return IWrapper
	 * @throws ServiceException, ParseException
	 */
	@Transactional(propagation = Propagation.REQUIRED,
			isolation = Isolation.DEFAULT,
			readOnly = false, 
			rollbackFor = ServiceException.class,
			timeout = -1)
	public IWrapper deleteBanner(Long bannerId) throws ServiceException, ParseException {
		Banner banner = retrieveById(bannerId);
		try {
			return updateStatusForBanner(banner, generalWorkflow.stepForward(banner.getStatus().getStatus(), DELETE));
		} catch (WorkflowException e) {
			throw new ServiceException("Error while performing the workflow step", e);
		}
	}

	/**
	 * Approves a banner with the given id
	 * 
	 * @param Long id
	 * @return IWrapper
	 * @throws ServiceException
	 * @author chanchal.kumari,ramiro.serrato
	 */
	@Transactional(propagation = Propagation.REQUIRED, 
			isolation = Isolation.DEFAULT, 
			readOnly = false, 
			rollbackFor = ServiceException.class, 
			timeout = -1)
	public IWrapper approveBanner(Long id) throws ServiceException {
		Banner banner = retrieveById(id);
		try {
			return updateStatusForBanner(banner, generalWorkflow.stepForward(banner.getStatus().getStatus(), APPROVE));
		} catch (WorkflowException e) {
			throw new ServiceException("Error while performing the workflow step", e);
		}

	}

	/**
	 * Reject a banner with the given id
	 * 
	 * @param Long id
	 * @return IWrapper
	 * @throws ServiceException
	 * @author chanchal.kumari,ramiro.serrato
	 */
	@Transactional(propagation = Propagation.REQUIRED, 
			isolation = Isolation.DEFAULT, 
			readOnly = false, 
			rollbackFor = ServiceException.class, 
			timeout = -1)
	public IWrapper rejectBanner(Long id) throws ServiceException {
		Banner banner = retrieveById(id);
		try {
			return updateStatusForBanner(banner, generalWorkflow.stepForward(banner.getStatus().getStatus(), REJECT));
		} catch (WorkflowException e) {
			throw new ServiceException("Error while performing the workflow step", e);
		}

	}

	/**
	 * Method to publish banner
	 * 
	 * @param Long id
	 * @throws ServiceException
	 */
	@Transactional(propagation = Propagation.REQUIRED, 
			isolation = Isolation.DEFAULT, 
			readOnly = false, 
			rollbackFor = ServiceException.class, 
			timeout = -1)
	public void publishBanner(Long id) throws ServiceException {
		Banner banner = retrieveById(id);
		try {
			String statusString = generalWorkflow.stepForward(banner.getStatus().getStatus(), PUBLISH);
			SearchCriteria criteria = new SearchCriteria();
			Map<String,Object>	searchTerm = criteria.getSearchTerms();
			searchTerm.put("obj.status.status", statusString);
			List<Status> statusList = (List<Status>)statusService.executeQuery(criteria);
			if(statusList != null && statusList.size() > 0){
				banner.setStatus(statusList.get(0));
				banner.setUpdatedDate(new Date());
			}
			update(banner);
		} catch (WorkflowException e) {
			throw new ServiceException("Error while performing the workflow step", e);
		}
	}	

	/**
	 * Method to invalidate a banner
	 * 
	 * @param Long id
	 * @return IWrapper
	 * @throws ServiceException
	 */
	@Transactional(propagation = Propagation.REQUIRED, 
			isolation = Isolation.DEFAULT, 
			readOnly = false, 
			rollbackFor = ServiceException.class, 
			timeout = -1)
	public IWrapper invalidBanner(Long id) throws ServiceException {
		Banner banner = retrieveById(id);
		try {
			return updateStatusForBanner(banner, generalWorkflow.stepForward(banner.getStatus().getStatus(), INVALID));
		} catch (WorkflowException e) {
			throw new ServiceException("Error while performing the workflow step", e);
		}
	}

	/**
	 * Method to update status for banner
	 * 
	 * @param Banner banner
	 * @param String status
	 * @return IWrapper
	 * @throws ServiceException
	 */
	@Override
	public IWrapper updateStatusForBanner(Banner banner, String status) throws ServiceException {
		Banner updatedBanner = null;
		SearchCriteria criteria = new SearchCriteria();
		Map<String,Object>	searchTerm = criteria.getSearchTerms();
		searchTerm.put("obj.status.status", status);
		List<Status> statusList = (List<Status>) statusService.executeQuery(criteria);
		if (statusList != null && statusList.size() > 0) {
			banner.setStatus(statusList.get(0));
			Users user = userUtil.getUser();
			banner.setUpdatedBy(user);
			banner.setUpdatedDate(new Date());
		}
		updatedBanner = update(banner);
		List<Banner> bannerList = new ArrayList<Banner>();
		bannerList.add(updatedBanner);
		BannerWrapper bannerWrapper = (BannerWrapper)BannerWrapper.fetchBanners(bannerList).get(0);
		
		try {
			bannerWrapper.setActions(BannerWrapper.fetchActions(generalWorkflow.getActionsForStatus(bannerWrapper.getStatus())));
		} catch (InvalidStatusException e) {
			throw new ServiceException(e);
		}
		return (IWrapper) bannerWrapper;
	}

	/**
	 * Method to create a new banner
	 *  
	 * @param BannerWrapper bannerWrapper
	 * @return IWrapper
	 * @throws ServiceException
	 */
	@Transactional(propagation = Propagation.REQUIRED,
			isolation = Isolation.DEFAULT,
			readOnly = false, 
			rollbackFor = ServiceException.class,
			timeout = -1)
	public IWrapper createBanner(BannerWrapper bannerWrapper) throws ServiceException {
		Banner banner = bannerWrapperConverter.wrapperConverter(bannerWrapper,new Banner());
		
		// Set the status of the banner
		if (bannerWrapper.getStatusId() == null) {
			Status status = statusService.retrieveById(MerchandisingConstants.DRAFT_STATUS);
			banner.setStatus(status);
		}
		List<Context> contexts = bannerWrapperConverter.contextWrapperConverter(bannerWrapper, null);	
		banner.setContexts(contexts);
		banner = prepareTemplate(bannerWrapper, banner);
		banner = this.save(banner);
		contextService.save(contexts);	
		List<Banner> bannerList = new ArrayList<Banner>();
		bannerList.add(banner);
		BannerWrapper wrapper = (BannerWrapper)BannerWrapper.fetchBanners(bannerList).get(0);

		try {
			wrapper.setActions(BannerWrapper.fetchActions(generalWorkflow.getActionsForStatus(banner.getStatus().getStatus())));
		} catch (InvalidStatusException e) {
			throw new ServiceException(e);
		}

		return (IWrapper) wrapper;
	}

	@Transactional(propagation = Propagation.REQUIRED,
			isolation = Isolation.DEFAULT,
			readOnly = false, 
			rollbackFor = ServiceException.class,
			timeout = -1)
	/**
	 * Method to update a existing Banner
	 *  
	 * @param BannerWrapper bannerWrapper
	 * @return IWrapper
	 * @throws ServiceException, DataAccessException, ParseException
	 */
	public IWrapper updateBanner(BannerWrapper bannerWrapper) throws ServiceException, DataAcessException, ParseException {
		Banner banner = bannerDAO.retrieveById(bannerWrapper.getBannerId());
		banner = bannerWrapperConverter.wrapperConverter(bannerWrapper,banner);
		List<Context> contexts = null;
		if (bannerWrapper.getContexts() != null && bannerWrapper.getContexts().size() > 0) {
			contexts  = updateContext(banner,bannerWrapper);
			banner.setContexts(contexts);
		} else {
			banner.setContexts(null);
		}

		if (bannerWrapper.getBannerTemplate() != null) {	
			banner = updateTemplate(bannerWrapper, banner);
		}

		try {
			banner = setUpdateStatusForBanner(banner, generalWorkflow.stepForward(banner.getStatus().getStatus(), EDIT));
		} catch (WorkflowException e) {
			throw new ServiceException("Error while performing the workflow step", e);
		}
		
		banner = update(banner);
		List<Banner> bannerList = new ArrayList<Banner>();
		bannerList.add(banner);
		BannerWrapper wrapper = (BannerWrapper)BannerWrapper.fetchBanners(bannerList).get(0);

		try {
			wrapper.setActions(BannerWrapper.fetchActions(generalWorkflow.getActionsForStatus(banner.getStatus().getStatus())));
		} catch (InvalidStatusException e) {
			throw new ServiceException(e);
		}
		
		return (IWrapper) wrapper;
	}

	/**
	 * Prepares the Template Data for Saving
	 * 
	 * @param bannerWrapper BannerWrapper
	 * @param banner Banner
	 * @return Banner
	 * @throws ServiceException
	 */
	private Banner prepareTemplate(BannerWrapper bannerWrapper, Banner banner) throws ServiceException {	
		List<BannerTemplate> bannerTemplates = new ArrayList<BannerTemplate>();
		//Check whether the banner template is HTML_ONLY
		if (bannerWrapper.getBannerTemplate().equalsIgnoreCase(BANNER_TEMPLATE_HTML)) {
			BannerTemplate bannerTemplate = new BannerTemplate();
			bannerTemplate.setSku(bannerWrapper.getDocumentId().toString());
			bannerTemplate.setTemplateType(BannerTemplateEnum.HTML_ONLY);
			bannerTemplate.setBanner(banner);
			bannerTemplates.add(bannerTemplate);
		} else {
			if (bannerWrapper.getTemplates() != null && bannerWrapper.getTemplates().size() > 0) {
				for (BannerTemplateWrapper bannerTemplateWrapper:bannerWrapper.getTemplates()) {
					for (int i=0;i<(bannerTemplateWrapper.getBannerSlotSkuLists()).size();i++) {
						BannerTemplate bannerTemplate = new BannerTemplate();
						bannerTemplate.setBanner(banner);
						bannerTemplate.setSku(bannerTemplateWrapper.getBannerSlotSkuLists().get(i).getSkuIds());
						bannerTemplate.setSkuPosition(bannerTemplateWrapper.getBannerSlotSkuLists().get(i).getSkuPosition());
						bannerTemplate.setTemplateHeader(bannerTemplateWrapper.getBannerHeader());
						if (bannerWrapper.getBannerTemplate().equalsIgnoreCase(BANNER_TEMPLATE_3HEADER)) {
							bannerTemplate.setTemplateType(BannerTemplateEnum.HEADER3_3SKU);
						} else {
							bannerTemplate.setTemplateType(BannerTemplateEnum.HEADER1_3SKU);
						}
						bannerTemplates.add(bannerTemplate);
					}
				}
			}
		}
		banner.setBannerTemplates(bannerTemplates);
		return banner;
	}


	/**
	 * Method to update template
	 * 
	 * @param BannerWrapper bannerWrapper
	 * @param Banner banner
	 * @return Banner
	 * @throws ServiceException
	 */
	private Banner updateTemplate(BannerWrapper bannerWrapper, Banner banner) throws ServiceException {	
		List<BannerTemplate> bannerTemplates = banner.getBannerTemplates();
		//Check whether the banner template is HTML_ONLY
		if (bannerWrapper.getBannerTemplate().equalsIgnoreCase(BANNER_TEMPLATE_HTML)) {
			boolean isChange = false;
			for (Iterator<BannerTemplate> iterator = bannerTemplates.iterator(); iterator.hasNext();) {
				BannerTemplate bannerTemplate = (BannerTemplate) iterator.next();
				if (isChange) {
					iterator.remove();
				} else {
					bannerTemplate.setSku(bannerWrapper.getDocumentId().toString());
					bannerTemplate.setTemplateType(BannerTemplateEnum.HTML_ONLY);
					bannerTemplate.setTemplateHeader(null);
					bannerTemplate.setSkuPosition(null);
					isChange = true;
				}
			}
		} else {	
			boolean isChange = false;
			BannerTemplate bannerTemplate = null;
			if (bannerTemplates.size() != 3) {
				isChange = true;
			}
			if (bannerWrapper.getBannerTemplate().equalsIgnoreCase(BANNER_TEMPLATE_3HEADER)) {
				for (int j=0 ; j< bannerWrapper.getTemplates().size() ; j++) {
					BannerTemplateWrapper bannerTemplateWrapper = bannerWrapper.getTemplates().get(j);
					for (int i=0;i<(bannerTemplateWrapper.getBannerSlotSkuLists()).size();i++) {	
						if (!isChange || j == 0) {
							bannerTemplate = bannerTemplates.get(j);
						} else {
							bannerTemplate = new BannerTemplate();
						}
						bannerTemplate.setTemplateType(BannerTemplateEnum.HEADER3_3SKU);
						bannerTemplate.setTemplateHeader(bannerTemplateWrapper.getBannerHeader());
						bannerTemplate.setTemplateHeader(bannerTemplateWrapper.getBannerHeader());
						bannerTemplate.setBanner(banner);
						bannerTemplate.setSku(bannerTemplateWrapper.getBannerSlotSkuLists().get(i).getSkuIds());
						bannerTemplate.setSkuPosition(bannerTemplateWrapper.getBannerSlotSkuLists().get(i).getSkuPosition());
						if (isChange && j!=0) {
							bannerTemplates.add(bannerTemplate);
						}
					}
				}
			} else {
				for (int j=0 ; j< bannerWrapper.getTemplates().size() ; j++) {
					BannerTemplateWrapper bannerTemplateWrapper = bannerWrapper.getTemplates().get(j);
					for (int i=0 ; i<(bannerTemplateWrapper.getBannerSlotSkuLists()).size() ; i++) {	
						if (!isChange || i == 0) {
							bannerTemplate = bannerTemplates.get(i);
						} else {
							bannerTemplate = new BannerTemplate();
						}
						bannerTemplate.setTemplateType(BannerTemplateEnum.HEADER1_3SKU);
						bannerTemplate.setBanner(banner);
						bannerTemplate.setSku(bannerTemplateWrapper.getBannerSlotSkuLists().get(i).getSkuIds());
						bannerTemplate.setSkuPosition(bannerTemplateWrapper.getBannerSlotSkuLists().get(i).getSkuPosition());
						bannerTemplate.setTemplateHeader(bannerTemplateWrapper.getBannerHeader());
						if (isChange && i != 0) {
							bannerTemplates.add(bannerTemplate);
						}
					}
				}
			}
			banner.setBannerTemplates(bannerTemplates);
		}
		return banner;
	}



	/**
	 * Update the contexts, contextFacet, ContextKeyword entity //done refactoring
	 * 
	 * @param Banner banner
	 * @param BannerWrapper bannerWrapper
	 * @return List<Context>
	 * @throws DataAcessException, ServiceException, ParseException
	 */
	private List<Context> updateContext(Banner banner, BannerWrapper bannerWrapper) 
			throws DataAcessException, ServiceException, ParseException {
		List<Context> contextsUpdated = new ArrayList<Context>();

		List<ContextWrapper> wrapperContexts = bannerWrapper.getContexts();
		//Compare the wrapper context info with the on in DB
		for (Context contextDB:banner.getContexts()) {
			//Context contextDB = iterator.next();
			ContextWrapper foundContext = null;
			for (ContextWrapper wrapperContext : wrapperContexts) {
				if (contextDB.getContextId().equals(wrapperContext.getContextId())) {
					foundContext = wrapperContext;
					break;
				}
			}
			//found the context update it with the Information from UI
			if (foundContext != null) {
				Context ctx = bannerWrapperConverter.bannerContextwrapper(foundContext, foundContext.getContextId(), contextDB);
				contextsUpdated.add(ctx);
				wrapperContexts.remove(foundContext);
			}
		}
		//Get the list of contexts from banner wrapper
		if (wrapperContexts != null && wrapperContexts.size() > 0) {
			for (ContextWrapper contextwrapper:wrapperContexts) {
				Context context = bannerWrapperConverter.bannerContextwrapper(contextwrapper,null,null);
				Context ctx = contextService.update(context);
				contextsUpdated.add(ctx);
			}
		}
		return contextsUpdated;
	}

	/**
	 * Method to load the data for edit pop-up banner based on banner id sent from UI
	 * 
	 * @param Long bannerId
	 * @return BannerWrapper
	 * @throws ServiceException
	 */
	public BannerWrapper loadeditbanner(Long bannerId) throws ServiceException { 
		//Retrieve the data from db for the specific bannerId
		Banner banner = retrieveById(bannerId);
		//Call the wrapper class to load the data from entity to wrapper
		BannerWrapper bannerWrapper=BannerWrapper.loadBannerWrapper(banner);
		return bannerWrapper;
	}

	/**
	 * Delete the Context from DataBase
	 * 
	 * @param Long contextId
	 * @throws ServiceException, ParseException
	 */
	@Transactional(propagation = Propagation.REQUIRED,
			isolation = Isolation.DEFAULT,
			readOnly = false, 
			rollbackFor = ServiceException.class,
			timeout = -1)
	public void deleteBannerContext(Long contextId) throws ServiceException, ParseException {
		//Retrieve the data for the contextID
		Context context = contextService.retrieveById(contextId);
		if (context != null) {
			//Delete the reference of banners to this context
			context.setBanners(null);
			//Delete the context
			contextService.delete(context);
		}
	}

	/**
	 * Method to update the Banners List to DB
	 * 
	 * @param List<Banner> banners
	 * @throws ServiceException
	 */
	@Transactional(propagation = Propagation.REQUIRED,
			isolation = Isolation.DEFAULT,
			readOnly = false, 
			rollbackFor = ServiceException.class,
			timeout = -1)
	public void updateBanners(List<Banner> banners) throws ServiceException {
		try {
			this.update(banners);
		} catch (DataAccessException e) {
			throw new ServiceException("Error while Updating the banners", e);
		}
	}
	
	/**
	 * Method to set update status for banner
	 * 
	 * @param Banner banner
	 * @param String statusName
	 * @return Banner
	 * @throws ServiceException
	 */
	private Banner setUpdateStatusForBanner(Banner banner, String statusName) throws ServiceException {
		Long statusId = statusService.getStatusId(statusName);
		Status status = statusService.retrieveById(statusId);
		Users userId = userUtil.getUser();
		banner.setUpdatedDate(new Date());
		banner.setUpdatedBy(userId);
		banner.setStatus(status);
		return banner;
	}
}
